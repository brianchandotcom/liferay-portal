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
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.net.HttpURLConnection;

import java.util.Locale;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AICreatorOpenAIClient.class)
public class MockAICreatorOpenAIClient implements AICreatorOpenAIClient {

	@Override
	public String getCompletion(
		String apiKey, String content, Locale locale, String tone, int words) {

		if (Objects.equals(apiKey, "VALID_API_KEY") &&
			Objects.equals(content, "USER_CONTENT")) {

			return "OPENAI_API_COMPLETION_RESPONSE_CONTENT";
		}

		throw _getAICreatorOpenAIClientException(content);
	}

	@Override
	public void validateAPIKey(String apiKey) {
		if (Objects.equals(apiKey, "VALID_API_KEY")) {
			return;
		}

		throw _getAICreatorOpenAIClientException(apiKey);
	}

	private AICreatorOpenAIClientException _getAICreatorOpenAIClientException(
		String key) {

		if (Objects.equals(key, "OPENAI_API_INVALID_API_KEY")) {
			return new AICreatorOpenAIClientException(
				"invalid_api_key", "invalid_api_key_message",
				HttpURLConnection.HTTP_OK);
		}

		if (Objects.equals(key, "OPENAI_API_IOEXCEPTION")) {
			return new AICreatorOpenAIClientException(new IOException());
		}

		int responseCode = GetterUtil.getInteger(
			StringUtils.substringBetween(key, "OPENAI_API_", "_RESPONSE_CODE"));

		if (responseCode > 0) {
			return new AICreatorOpenAIClientException(responseCode);
		}

		return new AICreatorOpenAIClientException(
			new UnsupportedOperationException(
				"Invalid Key to use MockAICreatorOpenAIClient, Key: " + key));
	}

}