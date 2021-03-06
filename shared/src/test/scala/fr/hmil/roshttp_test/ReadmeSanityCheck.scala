/**
  * This file is automatically generated by test/update-readme-sanity-check.sh
  * Do not edit this file manually!
  */
package fr.hmil.roshttp_test

import utest._

object ReadmeSanityCheck extends TestSuite {
  // Shims libraryDependencies
  class Dep {
    def %%%(s: String): String = "%%%"
    def %%(s: String): String = "%%"
    def %(s: String): String = "%"
  }
  implicit def fromString(s: String): Dep = new Dep()
  var libraryDependencies = Set[Dep]()

  // Silence print output
  def println(s: String): Unit = ()

  // Test suite
  val tests = this {
    "Readme snippets compile and run successfully" - {
      
      libraryDependencies += "fr.hmil" %%% "roshttp" % "2.1.0"
      
      import fr.hmil.roshttp.HttpRequest
      import monix.execution.Scheduler.Implicits.global
      import scala.util.{Failure, Success}
      import fr.hmil.roshttp.response.SimpleHttpResponse
      
      // Runs consistently on the jvm, in node.js and in the browser!
      val request = HttpRequest("https://schema.org/WebPage")
      
      request.send().onComplete({
          case res:Success[SimpleHttpResponse] => println(res.get.body)
          case e: Failure[SimpleHttpResponse] => println("Houston, we got a problem!")
        })
      
      import fr.hmil.roshttp.Protocol.HTTP
      
      HttpRequest()
        .withProtocol(HTTP)
        .withHost("localhost")
        .withPort(3000)
        .withPath("/weather")
        .withQueryParameter("city", "London")
        .send() // GET http://localhost:3000/weather?city=London
      
      // Sets the query string such that the target url ends in "?hello%20world"
      request.withQueryString("hello world")
      
      request
        .withQueryParameter("foo", "bar")
        .withQuerySeqParameter("table", Seq("a", "b", "c"))
        .withQueryObjectParameter("map", Seq(
          "d" -> "dval",
          "e" -> "e value"
        ))
        .withQueryParameters(
          "license" -> "MIT",
          "copy" -> "© 2016"
        )
        /* Query is now:
         foo=bar&table=a&table=b&table=c&map[d]=dval&map[e]=e%20value&license=MIT&copy=%C2%A9%202016
        */
      
      import fr.hmil.roshttp.Method.PUT
      
      request.withMethod(PUT).send()
      
      request.withHeader("Accept", "text/html")
      
      request.withHeaders(
        "Accept" -> "text/html",
        "User-Agent" -> "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"
      )
      
      import fr.hmil.roshttp.BackendConfig
      
      HttpRequest("long.source.of/data")
        .withBackendConfig(BackendConfig(
          // Uses stream chunks of at most 1024 bytes
          maxChunkSize = 1024
        ))
        .stream()
      
      request.withCrossDomainCookies(true)
      
      request.send().map({res =>
        println(res.headers("Set-Cookie"))
      })
      
      import fr.hmil.roshttp.body.Implicits._
      import fr.hmil.roshttp.body.URLEncodedBody
      
      val urlEncodedData = URLEncodedBody(
        "answer" -> "42",
        "platform" -> "jvm"
      )
      request.post(urlEncodedData)
      // or
      request.put(urlEncodedData)
      
      import fr.hmil.roshttp.body.Implicits._
      import fr.hmil.roshttp.body.JSONBody._
      
      val jsonData = JSONObject(
        "answer" -> 42,
        "platform" -> "node"
      )
      request.post(jsonData)
      
      import java.nio.ByteBuffer
      import fr.hmil.roshttp.body.ByteBufferBody
      
      val buffer = ByteBuffer.wrap(
              List[Byte](0x68, 0x65, 0x6c, 0x6c, 0x6f, 0x20, 0x77, 0x6f, 0x72, 0x6c, 0x64, 0x0a)
              .toArray)
      request.post(ByteBufferBody(buffer))
      
      import fr.hmil.roshttp.body.Implicits._
      import fr.hmil.roshttp.body.JSONBody._
      import fr.hmil.roshttp.body._
      
      request.post(MultiPartBody(
        // The name part is sent as plain text
        "name" -> PlainTextBody("John"),
        // The skills part is a complex nested structure sent as JSON
        "skills" -> JSONObject(
          "programming" -> JSONObject(
            "C" -> 3,
            "PHP" -> 1,
            "Scala" -> 5
          ),
          "design" -> true
        ),
        "hobbies" -> JSONArray(
          "programming",
          "stargazing"
        ),
        // The picture is sent using a ByteBufferBody, assuming buffer is a ByteBuffer
        // containing the image data
        "picture" -> ByteBufferBody(buffer, "image/jpeg")
      ))
      
      import fr.hmil.roshttp.util.Utils._
      
      request
        .stream()
        .map({ r =>
          r.body.foreach(buffer => println(getStringFromBuffer(buffer, "UTF-8")))
        })
      
      import fr.hmil.roshttp.Method.POST
      
      request
        .withMethod(POST)
        .withBody(PlainTextBody("My upload data"))
        .stream()
        // The response will be streamed
      
      val inputStream = new java.io.ByteArrayInputStream(new Array[Byte](1))
      
      import fr.hmil.roshttp.body.Implicits._
      
      // On the JVM:
      // val inputStream = new java.io.FileInputStream("video.avi")
      request
        .post(inputStream)
        .onComplete({
          case _:Success[SimpleHttpResponse] => println("Data successfully uploaded")
          case _:Failure[SimpleHttpResponse] => println("Error: Could not upload stream")
        })
      
      import fr.hmil.roshttp.exceptions.HttpException
      import java.io.IOException
      request.send()
        .recover {
          case HttpException(e: SimpleHttpResponse) =>
            // Here we may have some detailed application-level insight about the error
            println("There was an issue with your request." +
              " Here is what the application server says: " + e.body)
          case e: IOException =>
            // By handling transport issues separately, you get a chance to apply
            // your own recovery strategy. Should you report to the user? Log the error?
            // Retry the request? Send an alert to your ops team?
            println("There was a network issue, please try again")
        }

      "Success"
    }
  }
}
