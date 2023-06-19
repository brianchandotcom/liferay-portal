/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.ai.creator.openai.web.internal.client;

import com.liferay.ai.creator.openai.web.internal.exception.AICreatorOpenAIClientException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.verification.VerificationMode;

/**
 * @author Lourdes Fernández Besada
 */
public class AICreatorOpenAIClientTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws IOException {
		_aiCreatorOpenAIClient = new AICreatorOpenAIClient();

		_http = Mockito.mock(Http.class);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_http", _http);

		_jsonFactory = Mockito.mock(JSONFactory.class);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_jsonFactory", _jsonFactory);
	}

	@Test
	public void testValidateAPIKey() throws Exception {
		JSONObject responseJSONObject = Mockito.mock(JSONObject.class);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_OK, responseJSONObject);

		String apiKey = RandomTestUtil.randomString();

		_aiCreatorOpenAIClient.validateAPIKey(apiKey);

		_assertOptions(
			apiKey, "https://api.openai.com/v1/models/text-davinci-003");

		_assertResponse(response);

		_assertResponseJSONObject(responseJSONObject);
	}

	@Test
	public void testValidateAPIKeyIOException() throws Exception {
		IOException ioException = new IOException();

		Mockito.when(
			_http.URLtoInputStream(Mockito.any(Http.Options.class))
		).thenThrow(
			ioException
		);

		String apiKey = RandomTestUtil.randomString();

		try {
			_aiCreatorOpenAIClient.validateAPIKey(apiKey);

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				ioException, aiCreatorOpenAIClientException.getCause());
		}

		_assertOptions(
			apiKey, "https://api.openai.com/v1/models/text-davinci-003");
	}

	@Test
	public void testValidateAPIKeyResponseWithErrorKey() throws Exception {
		JSONObject errorJSONObject = JSONUtil.put(
			"code", RandomTestUtil.randomString()
		).put(
			"message", RandomTestUtil.randomString()
		);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_OK, JSONUtil.put("error", errorJSONObject));

		String apiKey = RandomTestUtil.randomString();

		try {
			_aiCreatorOpenAIClient.validateAPIKey(apiKey);

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				errorJSONObject.getString("code"),
				aiCreatorOpenAIClientException.getCode());
			Assert.assertEquals(
				errorJSONObject.getString("message"),
				aiCreatorOpenAIClientException.getMessage());

			Assert.assertEquals(
				HttpURLConnection.HTTP_OK,
				aiCreatorOpenAIClientException.getResponseCode());
		}

		_assertOptions(
			apiKey, "https://api.openai.com/v1/models/text-davinci-003");

		_assertResponse(response);
	}

	@Test
	public void testValidateAPIKeyUnauthorizedResponseCode() throws Exception {
		JSONObject responseJSONObject = Mockito.mock(JSONObject.class);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_UNAUTHORIZED, responseJSONObject);

		String apiKey = RandomTestUtil.randomString();

		try {
			_aiCreatorOpenAIClient.validateAPIKey(apiKey);

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				HttpURLConnection.HTTP_UNAUTHORIZED,
				aiCreatorOpenAIClientException.getResponseCode());
		}

		_assertOptions(
			apiKey, "https://api.openai.com/v1/models/text-davinci-003");

		_assertResponseJSONObject(responseJSONObject);

		_assertResponse(response, Mockito.times(2));
	}

	private static void _assertResponse(Http.Response response) {
		_assertResponse(response, Mockito.times(1));
	}

	private static void _assertResponse(
		Http.Response response, VerificationMode verificationMode) {

		Mockito.verify(
			response, verificationMode
		).getResponseCode();
	}

	private void _assertOptions(String apiKey, String location)
		throws Exception {

		ArgumentCaptor<Http.Options> argumentCaptor = ArgumentCaptor.forClass(
			Http.Options.class);

		Mockito.verify(
			_http
		).URLtoInputStream(
			argumentCaptor.capture()
		);

		Http.Options options = argumentCaptor.getValue();

		Assert.assertEquals(location, options.getLocation());
		Assert.assertEquals(
			"Bearer " + apiKey, options.getHeader("Authorization"));
	}

	private void _assertResponseJSONObject(JSONObject responseJSONObject) {
		Mockito.verify(
			responseJSONObject
		).has(
			"error"
		);
	}

	private Http.Response _getMockResponse(
			int responseCode, JSONObject responseJSONObject)
		throws Exception {

		Http.Response response = Mockito.mock(Http.Response.class);

		Mockito.when(
			response.getResponseCode()
		).thenReturn(
			responseCode
		);

		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyString())
		).thenReturn(
			responseJSONObject
		);

		Mockito.when(
			_jsonFactory.createJSONObject(Mockito.anyMap())
		).thenReturn(
			responseJSONObject
		);

		Mockito.when(
			_http.URLtoInputStream(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocationOnMock -> {
				Http.Options options = invocationOnMock.getArgument(
					0, Http.Options.class);

				options.setResponse(response);

				InputStream inputStream = Mockito.mock(InputStream.class);

				Mockito.when(
					inputStream.read(
						Mockito.any(), Mockito.anyInt(), Mockito.anyInt())
				).thenReturn(
					-1
				);

				return inputStream;
			}
		);

		return response;
	}

	private AICreatorOpenAIClient _aiCreatorOpenAIClient;
	private Http _http;
	private JSONFactory _jsonFactory;

}