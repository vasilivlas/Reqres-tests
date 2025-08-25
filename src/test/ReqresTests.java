import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ReqresTests {

    @Test
    public void getSingleResource() {
        ValidatableResponse response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .queryParam("id", "1")
                .when()
                .get("https://reqres.in/api/unknown/")
                .then()
                .statusCode(200);

        int statusCode = response.extract().statusCode();
        Assertions.assertEquals(200, statusCode, "STATUS CODE ERROR");

        System.out.println("Response: " + "\n" + response.extract().asPrettyString());
        System.out.println("Status code: " + response.extract().statusCode());
    }


    @Test
    public void createUserTest() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "neo");
        body.put("job", "savior");

        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(body)
                .when()
                .post("https://reqres.in/api/users")
                .andReturn();
        response.prettyPrint();
        String actualName = response.jsonPath().getString("name");
        String actualJob = response.jsonPath().getString("job");
        Assertions.assertEquals("neo", actualName, "CREATING ERROR");
        Assertions.assertEquals("savior", actualJob, "CREATING ERROR");


        int userId = response.jsonPath().getInt("id");

        System.out.println("User ID: " + userId);
    }

    @Test

    public void updateUserProfile() {

        Map<String, String> body = new HashMap<>();
        body.put("name", "neo");
        body.put("job", "ordinary man");

        Response targetResponse = RestAssured

                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(body)
                .when()
                .post("https://reqres.in/api/users/960")
                .andReturn();
        targetResponse.prettyPrint();

        String actualJob = targetResponse.jsonPath().getString("job");
        Assertions.assertEquals("ordinary man", actualJob, "UPDATING FAILED");


    }

    @Test
    public void deleteUserTest() {
        Map<String, String> body = new HashMap<>();
        body.put("name", "neo");
        body.put("job", "savior");

        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(body)
                .when()
                .delete("https://reqres.in/api/users/960")
                .andReturn();

        int statusCodeValue = response.statusCode();

        Assertions.assertEquals(204, statusCodeValue, "DELETING FAILED");
        System.out.println("Status code: " + response.statusCode());
    }


    @Test
    public void loginUnsuccessfulTest() {
        Map<String, String> body = new HashMap<>();
        body.put("e-mail", "neo@theone.com");

        Response response = RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1")
                .contentType("application/json")
                .body(body)
                .when()
                .post("https://reqres.in/api/login")
                .andReturn();
        response.prettyPrint();

        int statusCodeValue = response.statusCode();
        Assertions.assertEquals(400, statusCodeValue, "STATUS CODE ERROR");

        System.out.println();
        System.out.println("Status code: " + statusCodeValue);
    }
}
