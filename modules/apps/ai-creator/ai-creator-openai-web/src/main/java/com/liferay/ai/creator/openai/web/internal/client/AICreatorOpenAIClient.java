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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.HttpURLConnection;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AICreatorOpenAIClient.class)
public class AICreatorOpenAIClient {

	public String getCompletion(
			String apiKey, String content, Locale locale, String tone,
			int words)
		throws Exception {

		Http.Options options = new Http.Options();

		options.addHeader("Authorization", "Bearer " + apiKey);
		options.addHeader("Content-Type", ContentTypes.APPLICATION_JSON);
		options.setLocation(CHAT_COMPLETION_ENDPOINT);
		options.setBody(
			JSONUtil.put(
				"messages",
				JSONUtil.putAll(
					JSONUtil.put(
						"content",
						_language.format(
							locale,
							"i-want-you-to-create-a-text-using-x-as-the-" +
								"language,-of-approximately-x-words,-and-" +
									"using-a-x-tone",
							new String[] {
								LocaleUtil.getLocaleDisplayName(locale, locale),
								String.valueOf(words), tone
							})
					).put(
						"role", "system"
					),
					JSONUtil.put(
						"content", content
					).put(
						"role", "user"
					))
			).put(
				"model", "gpt-3.5-turbo"
			).toString(),
			ContentTypes.APPLICATION_JSON, StringPool.UTF8);
		options.setPost(true);

		try (InputStream inputStream = _http.URLtoInputStream(options)) {
			Http.Response response = options.getResponse();

			JSONObject responseJSONObject = _jsonFactory.createJSONObject(
				StringUtil.read(inputStream));

			if (responseJSONObject.has("error")) {
				JSONObject errorJSONObject = responseJSONObject.getJSONObject(
					"error");

				throw new AICreatorOpenAIClientException(
					errorJSONObject.getString("code"),
					errorJSONObject.getString("message"),
					response.getResponseCode());
			}
			else if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new AICreatorOpenAIClientException(
					response.getResponseCode());
			}

			JSONArray jsonArray = responseJSONObject.getJSONArray("choices");

			if (JSONUtil.isEmpty(jsonArray)) {
				return StringPool.BLANK;
			}

			JSONObject choiceJSONObject = jsonArray.getJSONObject(0);

			JSONObject messageJSONObject = choiceJSONObject.getJSONObject(
				"message");

			return messageJSONObject.getString("content");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (exception instanceof AICreatorOpenAIClientException) {
				throw exception;
			}

			throw new AICreatorOpenAIClientException(exception);
		}
	}

	public void validateAPIKey(String apiKey) throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader("Authorization", "Bearer " + apiKey);
		options.setLocation(VALIDATE_API_KEY_ENDPOINT);

		try (InputStream inputStream = _http.URLtoInputStream(options)) {
			Http.Response response = options.getResponse();

			JSONObject responseJSONObject = _jsonFactory.createJSONObject(
				StringUtil.read(inputStream));

			if (responseJSONObject.has("error")) {
				JSONObject errorJSONObject = responseJSONObject.getJSONObject(
					"error");

				throw new AICreatorOpenAIClientException(
					errorJSONObject.getString("code"),
					errorJSONObject.getString("message"),
					response.getResponseCode());
			}
			else if (response.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new AICreatorOpenAIClientException(
					response.getResponseCode());
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			if (exception instanceof AICreatorOpenAIClientException) {
				throw exception;
			}

			throw new AICreatorOpenAIClientException(exception);
		}
	}

	protected static final String CHAT_COMPLETION_ENDPOINT =
		"https://api.openai.com/v1/chat/completions";

	protected static final String VALIDATE_API_KEY_ENDPOINT =
		"https://api.openai.com/v1/models/text-davinci-003";

	private static final Log _log = LogFactoryUtil.getLog(
		AICreatorOpenAIClient.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}