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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertyUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.DataFieldOptionUtil;
import com.liferay.data.engine.rest.internal.util.v1_0.LocalizationUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Albuquerque
 */
public class SelectFieldType extends BaseFieldType {

	public SelectFieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		super(
			dataDefinitionField, httpServletRequest, httpServletResponse,
			soyDataFactory);
	}

	@Override
	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "dataSourceType",
				jsonObject.getString("dataSourceType")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "multiple",
				jsonObject.getBoolean("multiple")));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "options",
				DataFieldOptionUtil.toDataFieldOptions(
					jsonObject.getJSONObject("options"))));
		dataDefinitionField.setCustomProperties(
			CustomPropertyUtil.add(
				dataDefinitionField.getCustomProperties(), "predefinedValue",
				LocalizationUtil.toLocalizedValues(
					jsonObject.getJSONObject("predefinedValue"))));

		return dataDefinitionField;
	}

	@Override
	public JSONObject toJSONObject() throws Exception {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put(
			"dataSourceType",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "dataSourceType")
		).put(
			"multiple",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "multiple", false)
		).put(
			"options",
			DataFieldOptionUtil.toJSONObject(
				CustomPropertyUtil.getDataFieldOptions(
					dataDefinitionField.getCustomProperties(), "options"))
		).put(
			"predefinedValue",
			CustomPropertyUtil.getMap(
				dataDefinitionField.getCustomProperties(), "predefinedValue")
		);
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		context.put(
			"dataSourceType",
			CustomPropertyUtil.getString(
				dataDefinitionField.getCustomProperties(), "dataSourceType"));
		context.put(
			"multiple",
			CustomPropertyUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "multiple", false));
		context.put(
			"options",
			DataFieldOptionUtil.toDataFieldOptions(
				CustomPropertyUtil.getDataFieldOptions(
					dataDefinitionField.getCustomProperties(), "options"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"predefinedValue",
			LocalizationUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertyUtil.getMap(
					dataDefinitionField.getCustomProperties(),
					"predefinedValue")));
		context.put("strings", _getLanguageTerms());
		context.put(
			"value",
			CustomPropertyUtil.getValues(
				dataDefinitionField.getCustomProperties(), "value"));
	}

	private Map<String, String> _getLanguageTerms() {
		Map<String, String> languageTerms = new HashMap<>();

		languageTerms.put(
			"chooseAnOption",
			LanguageUtil.get(httpServletRequest, "choose-an-option"));
		languageTerms.put(
			"chooseOptions",
			LanguageUtil.get(httpServletRequest, "choose-options"));
		languageTerms.put(
			"dynamicallyLoadedData",
			LanguageUtil.get(httpServletRequest, "dynamically-loaded-data"));
		languageTerms.put(
			"emptyList", LanguageUtil.get(httpServletRequest, "empty-list"));
		languageTerms.put(
			"search", LanguageUtil.get(httpServletRequest, "search"));

		return languageTerms;
	}

}