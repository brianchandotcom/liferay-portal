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

package com.liferay.translation.translator.deepl.internal.translator;

import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.url.URLBuilder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Http;
import com.liferay.translation.exception.TranslatorException;
import com.liferay.translation.translator.deepl.internal.constants.DeepLConstants;
import com.liferay.translation.translator.deepl.internal.model.SupportedLanguage;
import com.liferay.translation.translator.deepl.internal.model.TranslateResponse;
import com.liferay.translation.translator.deepl.internal.util.JSONUtil;

import java.io.IOException;

import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yasuyuki Takeo
 */
@Component(immediate = true, service = DeepLClient.class)
public class DeepLClient {

	public TranslateResponse execute(
			String authKey, String text, String sourceLanguageId,
			String targetLanguageId, String url)
		throws IOException, PortalException {

		return JSONUtil.toObject(
			_fetch(authKey, text, sourceLanguageId, targetLanguageId, url),
			TranslateResponse.class);
	}

	public List<SupportedLanguage> getSupportedLanguages(
			String authKey, String target, String url)
		throws IOException, PortalException {

		return JSONUtil.toObject(
			_getSupportedLanguage(authKey, target, url),
			new TypeReference<List<SupportedLanguage>>() {
			});
	}

	private String _fetch(
			String authKey, String text, String sourceLanguageId,
			String targetLanguageId, String url)
		throws IOException, PortalException {

		Http.Options options = new Http.Options();

		options.setLocation(
			URLBuilder.create(
				url
			).addParameter(
				DeepLConstants.AUTH_KEY, authKey
			).build());

		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
		options.addPart(DeepLConstants.AUTH_KEY, authKey);
		options.addPart(DeepLConstants.SOURCE_LANG, sourceLanguageId);
		options.addPart(DeepLConstants.TARGET_LANG, targetLanguageId);
		options.addPart(DeepLConstants.TEXT, text);
		options.setMethod(Http.Method.POST);

		Http.Response response = options.getResponse();

		Response.Status status = Response.Status.fromStatusCode(
			response.getResponseCode());

		if (status == Response.Status.OK) {
			return _http.URLtoString(options);
		}
		else if (status == Response.Status.TOO_MANY_REQUESTS) {
			throw new TranslatorException(
				"The status is TOO_MANY_REQUESTS. Please retry after a while.");
		}
		else {
			throw new PortalException(
				"The status(" + status.toString() + ") is invalid.");
		}
	}

	private String _getSupportedLanguage(
			String authKey, String target, String url)
		throws IOException, PortalException {

		Http.Options options = new Http.Options();

		options.setLocation(
			URLBuilder.create(
				url
			).addParameter(
				DeepLConstants.AUTH_KEY, authKey
			).build());

		options.addHeader(
			HttpHeaders.CONTENT_TYPE,
			ContentTypes.APPLICATION_X_WWW_FORM_URLENCODED);
		options.addPart(DeepLConstants.AUTH_KEY, authKey);
		options.addPart(DeepLConstants.TARGET, target);
		options.setMethod(Http.Method.POST);

		String supportedLanguage = _http.URLtoString(options);

		Http.Response response = options.getResponse();

		Response.Status status = Response.Status.fromStatusCode(
			response.getResponseCode());

		if (status == Response.Status.OK) {
			return supportedLanguage;
		}
		else if (status == Response.Status.TOO_MANY_REQUESTS) {
			throw new TranslatorException(
				"The status is TOO_MANY_REQUESTS. Please retry after a while.");
		}
		else {
			throw new PortalException("The status(" + status + ") is invalid.");
		}
	}

	@Reference
	private Http _http;

}