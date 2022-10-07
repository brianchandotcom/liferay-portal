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

package com.liferay.dynamic.data.mapping.form.builder.internal.servlet;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionFactory;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunctionTracker;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = {
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-functions",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFormFunctionsServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-functions/*"
	},
	service = Servlet.class
)
public class DDMFormFunctionsServlet extends BaseDDMFormBuilderServlet {

	@Override
	protected void doGet(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		Map<String, DDMExpressionFunctionFactory>
			ddmExpressionFunctionFactories =
				_getDDMExpressionFunctionFactories();

		JSONArray jsonArray = _toJSONArray(
			ddmExpressionFunctionFactories.entrySet(),
			LocaleUtil.fromLanguageId(
				ParamUtil.getString(httpServletRequest, "languageId")));

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);
		httpServletResponse.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());
	}

	protected JSONObject toJSONObject(
		Map.Entry<String, DDMExpressionFunctionFactory> entry,
		ResourceBundle resourceBundle) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		String key = entry.getKey();

		String labelLanguageKey = key + "_function";

		jsonObject.put(
			"label", _language.get(resourceBundle, labelLanguageKey)
		).put(
			"value", key
		);

		String tooltipLanguageKey = key + "_tooltip";

		jsonObject.put(
			"tooltip", _language.get(resourceBundle, tooltipLanguageKey));

		return jsonObject;
	}

	private Map<String, DDMExpressionFunctionFactory>
		_getDDMExpressionFunctionFactories() {

		Set<String> functionNames = new HashSet<>();

		functionNames.add("sum");

		return _ddmExpressionFunctionTracker.getDDMExpressionFunctionFactories(
			functionNames);
	}

	private JSONArray _toJSONArray(
		Set<Map.Entry<String, DDMExpressionFunctionFactory>> entries,
		Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		for (Map.Entry<String, DDMExpressionFunctionFactory> entry : entries) {
			jsonArray.put(toJSONObject(entry, resourceBundle));
		}

		return jsonArray;
	}

	private static final long serialVersionUID = 1L;

	@Reference
	private DDMExpressionFunctionTracker _ddmExpressionFunctionTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}