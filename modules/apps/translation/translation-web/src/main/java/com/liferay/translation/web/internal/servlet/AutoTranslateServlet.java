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

package com.liferay.translation.web.internal.servlet;

import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.translation.translator.JSONTranslatorPacket;
import com.liferay.translation.translator.Translator;
import com.liferay.translation.translator.TranslatorPacket;
import com.liferay.translation.web.internal.constants.TranslationWebConstants;

import java.io.IOException;

import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.http.whiteboard.servlet.name=com.liferay.translation.web.internal.servlet.AutoTranslateServlet",
		"osgi.http.whiteboard.servlet.pattern=" + TranslationWebConstants.AUTO_TRANSLATE_SERVLET_PATH,
		"servlet.init.httpMethods=POST"
	},
	service = Servlet.class
)
public class AutoTranslateServlet extends HttpServlet {

	@Override
	protected void doPost(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		try {
			httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

			String content = StreamUtil.toString(
				httpServletRequest.getInputStream());

			TranslatorPacket translatedTranslatorPacket = _translator.translate(
				new JSONTranslatorPacket(
					JSONFactoryUtil.createJSONObject(content)));

			ServletResponseUtil.write(
				httpServletResponse, _toJSON(translatedTranslatorPacket));

			httpServletResponse.flushBuffer();
		}
		catch (JSONException jsonException) {
			throw new ServletException(jsonException);
		}
	}

	private JSONArray _getContentJSONArray(Map<String, String> fieldsMap) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<String, String> entry : fieldsMap.entrySet()) {
			jsonArray.put(JSONUtil.put(entry.getKey(), entry.getValue()));
		}

		return jsonArray;
	}

	private String _toJSON(TranslatorPacket translatorPacket) {
		return JSONUtil.put(
			"fields", _getContentJSONArray(translatorPacket.getFieldsMap())
		).put(
			"sourceLanguageId", translatorPacket.getSourceLanguageId()
		).put(
			"targetLanguageId", translatorPacket.getTargetLanguageId()
		).toString();
	}

	@Reference
	private Translator _translator;

}