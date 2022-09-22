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

package com.liferay.translation.translator.deepl.internal.manager;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.url.URLBuilder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.deepl.internal.configuration.DeepLTranslatorConfiguration;
import com.liferay.translation.translator.deepl.internal.model.SupportedLanguage;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(immediate = true, service = SupportedLanguageManager.class)
public class SupportedLanguageManager {

	public List<String> getSupportedLanguages(
			DeepLTranslatorConfiguration deepLTranslatorConfiguration)
		throws PortalException {

		try {
			List<String> languages = new ArrayList<>();

			List<SupportedLanguage> supportedLanguages = _toSupportedLanguages(
				deepLTranslatorConfiguration.authKey(), "target",
				deepLTranslatorConfiguration.validateLanguageUrl());

			supportedLanguages.forEach(
				supportedLanguage -> languages.add(
					supportedLanguage.getLanguage()));

			return languages;
		}
		catch (IOException ioException) {
			_log.error(
				"Failed to call supported language list." +
					System.lineSeparator() + ioException.getLocalizedMessage());

			return Collections.emptyList();
		}
	}

	private String _getSupportedLanguage(
			String authKey, String target, String url)
		throws IOException, PortalException {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
		options.addPart("auth_key", authKey);
		options.addPart("target", target);

		options.setLocation(
			URLBuilder.create(
				url
			).addParameter(
				"auth_key", authKey
			).build());

		options.setMethod(Http.Method.POST);

		String supportedLanguage = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		Response.Status status = Response.Status.fromStatusCode(
			response.getResponseCode());

		if (status == Response.Status.OK) {
			return supportedLanguage;
		}

		throw new TranslatorException(
			"The status is " + status + ". Please retry after a while.");
	}

	private List<SupportedLanguage> _toSupportedLanguages(
			String authKey, String target, String url)
		throws IOException, PortalException {

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			_getSupportedLanguage(authKey, target, url));

		return JSONUtil.toList(
			jsonArray,
			customFieldJSONObject -> new SupportedLanguage(
				customFieldJSONObject.getString("language"),
				customFieldJSONObject.getString("name"),
				customFieldJSONObject.getBoolean("supports_formality")),
			_log);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SupportedLanguageManager.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}