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
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.url.URLBuilder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.deepl.internal.model.Translation;

import java.io.IOException;

import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(immediate = true, service = TranslationManager.class)
public class TranslationManager {

	public List<Translation> getTranslateResponse(
			String authKey, String text, String sourceLanguageId,
			String targetLanguageId, String url)
		throws IOException, PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_getTranslation(
				authKey, text, sourceLanguageId, targetLanguageId, url));

		return JSONUtil.toList(
			jsonObject.getJSONArray("translations"),
			customFieldJSONObject -> new Translation(
				customFieldJSONObject.getString("detected_source_language"),
				customFieldJSONObject.getString("text")),
			_log);
	}

	private String _getTranslation(
			String authKey, String text, String sourceLanguageId,
			String targetLanguageId, String url)
		throws IOException, PortalException {

		Http.Options options = new Http.Options();

		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
		options.addPart("auth_key", authKey);
		options.addPart("source_lang", sourceLanguageId);
		options.addPart("target_lang", targetLanguageId);
		options.addPart("text", text);

		options.setLocation(
			URLBuilder.create(
				url
			).addParameter(
				"auth_key", authKey
			).build());

		options.setMethod(Http.Method.POST);

		String translation = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		Response.Status status = Response.Status.fromStatusCode(
			response.getResponseCode());

		if (status == Response.Status.OK) {
			return translation;
		}

		throw new TranslatorException(
			"The status is " + status + ". Please retry after a while.");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TranslationManager.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}