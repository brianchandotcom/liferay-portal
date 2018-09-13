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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.dynamic.data.mapping.form.builder.internal.servlet.base.BaseDDMFormBuilderServlet;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = {
		"dynamic.data.mapping.form.builder.servlet=true",
		"osgi.http.whiteboard.context.path=/dynamic-data-mapping-form-builder-functions",
		"osgi.http.whiteboard.servlet.name=com.liferay.dynamic.data.mapping.form.builder.internal.servlet.DDMFormFunctionsServlet",
		"osgi.http.whiteboard.servlet.pattern=/dynamic-data-mapping-form-builder-functions/*"
	},
	service = Servlet.class
)
public class DDMFormFunctionsServlet extends BaseDDMFormBuilderServlet {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction,
		Map<String, Object> properties) {

		if (properties.containsKey(
				"ddm.form.evaluator.function.available.on.calculation.rule") &&
			properties.containsKey("ddm.form.evaluator.function.name")) {

			boolean available = MapUtil.getBoolean(
				properties,
				"ddm.form.evaluator.function.available.on.calculation.rule");

			if (!available) {
				return;
			}

			String functionName = MapUtil.getString(
				properties, "ddm.form.evaluator.function.name");

			_ddmExpressionFunctions.putIfAbsent(
				functionName, ddmExpressionFunction);
		}
	}

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		String languageId = ParamUtil.getString(request, "languageId");

		Set<Map.Entry<String, DDMExpressionFunction>> entries =
			_ddmExpressionFunctions.entrySet();

		JSONArray jsonArray = toJSONArray(
			entries, LocaleUtil.fromLanguageId(languageId));

		response.setContentType(ContentTypes.APPLICATION_JSON);
		response.setStatus(HttpServletResponse.SC_OK);

		ServletResponseUtil.write(response, jsonArray.toJSONString());
	}

	protected void removeDDMExpressionFunction(
		DDMExpressionFunction ddmExpressionFunction,
		Map<String, Object> properties) {

		if (properties.containsKey("ddm.form.evaluator.function.name")) {
			String functionName = MapUtil.getString(
				properties, "ddm.form.evaluator.function.name");

			_ddmExpressionFunctions.remove(functionName);
		}
	}

	protected JSONArray toJSONArray(
		Set<Map.Entry<String, DDMExpressionFunction>> entries, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		for (Map.Entry<String, DDMExpressionFunction> entry : entries) {
			jsonArray.put(toJSONObject(entry, resourceBundle));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(
		Map.Entry<String, DDMExpressionFunction> entry,
		ResourceBundle resourceBundle) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		String key = entry.getKey();

		String labelLanguageKey = key + CharPool.UNDERLINE + "function";

		jsonObject.put(
			"label", LanguageUtil.get(resourceBundle, labelLanguageKey));

		jsonObject.put("value", key);

		String tooltipLanguageKey = key + CharPool.UNDERLINE + "tooltip";

		jsonObject.put(
			"tooltip", LanguageUtil.get(resourceBundle, tooltipLanguageKey));

		return jsonObject;
	}

	private static final long serialVersionUID = 1L;

	private final Map<String, DDMExpressionFunction> _ddmExpressionFunctions =
		new ConcurrentHashMap<>();

	@Reference
	private JSONFactory _jsonFactory;

}