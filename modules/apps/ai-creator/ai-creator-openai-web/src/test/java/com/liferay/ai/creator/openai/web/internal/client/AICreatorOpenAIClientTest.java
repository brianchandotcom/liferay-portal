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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;

import java.util.Locale;

import org.junit.After;
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
		_originalLanguage = LanguageUtil.getLanguage();

		_aiCreatorOpenAIClient = new AICreatorOpenAIClient();

		_http = Mockito.mock(Http.class);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_http", _http);

		_jsonFactory = Mockito.mock(JSONFactory.class);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_jsonFactory", _jsonFactory);
	}

	@After
	public void tearDown() {
		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_originalLanguage);
	}

	@Test
	public void testGetCompletion() throws Exception {
		String messageContent = RandomTestUtil.randomString();

		JSONObject responseJSONObject = JSONUtil.put(
			"choices",
			JSONUtil.put(
				JSONUtil.put(
					"message", JSONUtil.put("content", messageContent))));

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_OK, responseJSONObject);

		_mockLanguage();

		String apiKey = RandomTestUtil.randomString();
		String content = RandomTestUtil.randomString();
		String tone = RandomTestUtil.randomString();
		int words = RandomTestUtil.randomInt();

		Assert.assertEquals(
			messageContent,
			_aiCreatorOpenAIClient.getCompletion(
				apiKey, content, LocaleUtil.getDefault(), tone, words));

		_assertMessageRoleSystemContent(LocaleUtil.getDefault(), tone, words);

		_assertOptions(
			apiKey, content, ContentTypes.APPLICATION_JSON,
			"https://api.openai.com/v1/chat/completions");

		_assertResponse(response);
	}

	@Test
	public void testGetCompletionIOException() throws Exception {
		IOException ioException = new IOException();

		Mockito.when(
			_http.URLtoInputStream(Mockito.any(Http.Options.class))
		).thenThrow(
			ioException
		);

		_mockLanguage();

		String apiKey = RandomTestUtil.randomString();
		String content = RandomTestUtil.randomString();

		try {
			_aiCreatorOpenAIClient.getCompletion(
				apiKey, content, LocaleUtil.getDefault(),
				RandomTestUtil.randomString(), RandomTestUtil.randomInt());

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				ioException, aiCreatorOpenAIClientException.getCause());
		}

		_assertOptions(
			apiKey, content, ContentTypes.APPLICATION_JSON,
			"https://api.openai.com/v1/chat/completions");
	}

	@Test
	public void testGetCompletionResponseWithErrorKey() throws Exception {
		JSONObject errorJSONObject = JSONUtil.put(
			"code", RandomTestUtil.randomString()
		).put(
			"message", RandomTestUtil.randomString()
		);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_OK, JSONUtil.put("error", errorJSONObject));

		_mockLanguage();

		String apiKey = RandomTestUtil.randomString();
		String content = RandomTestUtil.randomString();

		try {
			_aiCreatorOpenAIClient.getCompletion(
				apiKey, content, LocaleUtil.getDefault(),
				RandomTestUtil.randomString(), RandomTestUtil.randomInt());

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
			apiKey, content, ContentTypes.APPLICATION_JSON,
			"https://api.openai.com/v1/chat/completions");

		_assertResponse(response);
	}

	@Test
	public void testGetCompletionUnauthorizedResponseCode() throws Exception {
		JSONObject responseJSONObject = Mockito.mock(JSONObject.class);

		Http.Response response = _getMockResponse(
			HttpURLConnection.HTTP_UNAUTHORIZED, responseJSONObject);

		_mockLanguage();

		String apiKey = RandomTestUtil.randomString();
		String content = RandomTestUtil.randomString();

		try {
			_aiCreatorOpenAIClient.getCompletion(
				apiKey, content, LocaleUtil.getDefault(),
				RandomTestUtil.randomString(), RandomTestUtil.randomInt());

			Assert.fail();
		}
		catch (AICreatorOpenAIClientException aiCreatorOpenAIClientException) {
			Assert.assertEquals(
				HttpURLConnection.HTTP_UNAUTHORIZED,
				aiCreatorOpenAIClientException.getResponseCode());
		}

		_assertOptions(
			apiKey, content, ContentTypes.APPLICATION_JSON,
			"https://api.openai.com/v1/chat/completions");

		_assertResponseJSONObject(responseJSONObject);

		_assertResponse(response, Mockito.times(2));
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

	private void _assertBody(String content, Http.Body body) throws Exception {
		Assert.assertNotNull(body);

		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, body.getContentType());
		Assert.assertEquals(StringPool.UTF8, body.getCharset());

		Assert.assertTrue(
			body.getContent(), JSONUtil.isJSONObject(body.getContent()));

		JSONObject contentJSONObject = new JSONObjectImpl(body.getContent());

		Assert.assertEquals(
			"gpt-3.5-turbo", contentJSONObject.getString("model"));

		JSONArray messagesJSONArray = contentJSONObject.getJSONArray(
			"messages");

		Assert.assertNotNull(contentJSONObject.toString(), messagesJSONArray);

		Assert.assertEquals(
			messagesJSONArray.toString(), 2, messagesJSONArray.length());

		JSONObject messageJSONObject1 = messagesJSONArray.getJSONObject(0);

		Assert.assertEquals("system", messageJSONObject1.getString("role"));

		JSONObject messageJSONObject2 = messagesJSONArray.getJSONObject(1);

		Assert.assertEquals(content, messageJSONObject2.getString("content"));

		Assert.assertEquals("user", messageJSONObject2.getString("role"));
	}

	private void _assertMessageRoleSystemContent(
		Locale locale, String tone, int words) {

		ArgumentCaptor<String[]> argumentCaptor = ArgumentCaptor.forClass(
			String[].class);

		Mockito.verify(
			_language
		).format(
			Mockito.eq(locale),
			Mockito.eq(
				"i-want-you-to-create-a-text-using-x-as-the-language,-of-" +
					"approximately-x-words,-and-using-a-x-tone"),
			argumentCaptor.capture()
		);

		String[] arguments = argumentCaptor.getValue();

		Assert.assertEquals(arguments.toString(), 3, arguments.length);

		Assert.assertEquals(locale.getDisplayName(locale), arguments[0]);

		Assert.assertEquals(String.valueOf(words), arguments[1]);

		Assert.assertEquals(tone, arguments[2]);
	}

	private void _assertOptions(String apiKey, String location)
		throws Exception {

		_assertOptions(apiKey, null, null, location);
	}

	private void _assertOptions(
			String apiKey, String content, String contentType, String location)
		throws Exception {

		ArgumentCaptor<Http.Options> argumentCaptor = ArgumentCaptor.forClass(
			Http.Options.class);

		Mockito.verify(
			_http
		).URLtoInputStream(
			argumentCaptor.capture()
		);

		Http.Options options = argumentCaptor.getValue();

		if (Validator.isNull(content)) {
			Assert.assertNull(options.getBody());
		}
		else {
			_assertBody(content, options.getBody());
		}

		Assert.assertEquals(
			"Bearer " + apiKey, options.getHeader("Authorization"));
		Assert.assertEquals(contentType, options.getHeader("Content-Type"));
		Assert.assertEquals(location, options.getLocation());
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

	private void _mockLanguage() {
		_language = Mockito.mock(Language.class);

		Mockito.when(
			_language.get(Mockito.any(Locale.class), Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> invocationOnMock.getArgument(1, String.class)
		);

		ReflectionTestUtil.setFieldValue(
			_aiCreatorOpenAIClient, "_language", _language);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);
	}

	private static Language _originalLanguage;

	private AICreatorOpenAIClient _aiCreatorOpenAIClient;
	private Http _http;
	private JSONFactory _jsonFactory;
	private Language _language;

}