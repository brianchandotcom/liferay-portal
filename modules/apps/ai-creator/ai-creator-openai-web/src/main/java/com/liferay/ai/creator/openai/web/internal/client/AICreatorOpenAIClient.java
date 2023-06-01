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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.InputStream;

import java.net.HttpURLConnection;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = AICreatorOpenAIClient.class)
public class AICreatorOpenAIClient {

	public void validateAPIKey(String apiKey) throws Exception {
		Http.Options options = new Http.Options();

		options.addHeader("Authorization", "Bearer " + apiKey);

		options.setLocation("https://api.openai.com/v1/models");

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

	private static final Log _log = LogFactoryUtil.getLog(
		AICreatorOpenAIClient.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}