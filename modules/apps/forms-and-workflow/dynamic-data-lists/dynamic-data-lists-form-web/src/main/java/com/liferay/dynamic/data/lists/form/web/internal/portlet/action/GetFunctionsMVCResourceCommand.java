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

package com.liferay.dynamic.data.lists.form.web.internal.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
		"mvc.command.name=getFunctions"
	},
	service = MVCResourceCommand.class
)
public class GetFunctionsMVCResourceCommand extends BaseMVCResourceCommand {

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDDMExpressionFunction"
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
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set<Map.Entry<String, DDMExpressionFunction>> entries =
			_ddmExpressionFunctions.entrySet();

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			toJSONArray(entries, themeDisplay.getLocale()));
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

		for (Map.Entry<String, DDMExpressionFunction> entry : entries) {
			jsonArray.put(toJSONObject(entry, locale));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(
		Map.Entry<String, DDMExpressionFunction> entry, Locale locale) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		String key = entry.getKey();

		String labelLanguageKey = key + CharPool.UNDERLINE + "function";

		jsonObject.put("label", LanguageUtil.get(locale, labelLanguageKey));

		jsonObject.put("value", key);

		String tooltipLanguageKey = key + CharPool.UNDERLINE + "tooltip";

		jsonObject.put("tooltip", LanguageUtil.get(locale, tooltipLanguageKey));

		return jsonObject;
	}

	private final Map<String, DDMExpressionFunction> _ddmExpressionFunctions =
		new ConcurrentHashMap<>();

	@Reference
	private JSONFactory _jsonFactory;

}