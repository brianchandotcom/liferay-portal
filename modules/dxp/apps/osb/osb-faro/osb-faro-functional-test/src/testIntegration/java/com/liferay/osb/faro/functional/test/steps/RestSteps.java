/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.functional.test.steps;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.osb.faro.functional.test.util.AppResponse;
import com.liferay.osb.faro.functional.test.util.FaroRestUtil;
import com.liferay.osb.faro.functional.test.util.FaroTestDataUtil;
import com.liferay.osb.faro.functional.test.util.FaroTransformer;
import com.liferay.portal.kernel.util.StringUtil;

import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.junit.Assert;

/**
 * @author Cheryl Tang
 */
public class RestSteps {

	/**
	 * Asserts a the status code from a REST request and the value of a
	 * specified key in the response body.
	 *
	 * @param  expectedResponseCode the expected status code in the response
	 * @param  expectedKey the key to assert
	 * @param  expectedValue the expected value of the key
	 * @throws Exception if an exception occurred
	 */
	@Then("^I should receive a \"(\\d*)\" response with the following (.*):$")
	public void assertRestResponse(
			int expectedResponseCode,
			@Transform(FaroTransformer.class) String expectedKey,
			@Transform(FaroTransformer.class) String expectedValue)
		throws Exception {

		AppResponse appResponse = FaroTestDataUtil.getAppResponse();

		Assert.assertEquals(
			expectedResponseCode, appResponse.getHttpStatusCode());

		JsonNode jsonNode = _objectMapper.readTree(
			appResponse.getResponseBody()
		).get(
			expectedKey
		);

		Assert.assertEquals(expectedValue, jsonNode.asText());
	}

	/**
	 * Wrapper for sendRestRequest used when posting a Data Source or Segment so
	 * that the returned ID can be stored in FaroTestDataUtil for deletion after
	 * test execution.
	 *
	 * @param  entityType the type of entity to post
	 * @param  url the URL to post to
	 * @param  requestBody the request body to send
	 * @throws Exception if an exception occurred
	 */
	@When("^I POST the following (.*) to (.*) with the following body:$")
	public void postEntity(
			@Transform(FaroTransformer.class) String entityType,
			@Transform(FaroTransformer.class) String url,
			@Transform(FaroTransformer.class) String requestBody)
		throws Exception {

		sendRestRequest("POST", url, requestBody);

		AppResponse appResponse = FaroTestDataUtil.getAppResponse();

		JsonNode rootJsonNode = _objectMapper.readTree(
			appResponse.getResponseBody());

		JsonNode nameJsonNode = rootJsonNode.path("name");

		String name = nameJsonNode.textValue();

		JsonNode idJsonNode = rootJsonNode.path("id");

		String id = idJsonNode.textValue();

		if ((name != null) && (id != null)) {
			if (StringUtil.equalsIgnoreCase(entityType, "data source")) {
				FaroTestDataUtil.addDataSourceId(name, id);
			}
			else if (StringUtil.equalsIgnoreCase(entityType, "segment")) {
				FaroTestDataUtil.addSegmentId(name, id);
			}
		}
	}

	/**
	 * Sends a REST request with an optional body.
	 *
	 * @param  requestMethod the REST method to use for the request
	 * @param  url the URL to send the request to
	 * @param  requestBody the optional request body to send
	 * @throws Exception if an exception occurred
	 */
	@Given("^I send a (.*) request to \"(.*)\"(?: with the following body:)?$")
	public void sendRestRequest(
			@Transform(FaroTransformer.class) String requestMethod,
			@Transform(FaroTransformer.class) String url,
			@Transform(FaroTransformer.class) String requestBody)
		throws Exception {

		AppResponse appResponse = null;

		if (requestMethod.equals("GET")) {
			appResponse = FaroRestUtil.get(url, true);
		}
		else if (requestMethod.equals("POST")) {
			if (requestBody != null) {
				appResponse = FaroRestUtil.post(url, requestBody, true);
			}
			else {
				appResponse = FaroRestUtil.post(url, true);
			}
		}
		else if (requestMethod.equals("DELETE")) {
			appResponse = FaroRestUtil.delete(url, true);
		}
		else {
			throw new IllegalArgumentException(
				"Invalid request method " + requestMethod +
					". Supported methods are: \"GET\", \"POST\", \"DELETE\"");
		}

		FaroTestDataUtil.storeAppResponse(appResponse);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}