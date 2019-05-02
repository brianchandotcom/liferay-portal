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
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.DataFieldOptionUtil;
import com.liferay.data.engine.rest.internal.util.v1_0.LocalizationUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Marcelo Mello
 */
public class TextFieldType extends BaseFieldType {

	public TextFieldType(
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

		DataDefinitionField dataDefinitionField = super.deserialize(jsonObject);

		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(),
				"autocompleteEnabled",
				jsonObject.getBoolean("autocompleteEnabled")));
		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "displayStyle",
				jsonObject.getString("displayStyle")));
		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "options",
				DataFieldOptionUtil.toDataFieldOptions(
					jsonObject.getJSONObject("options"))));
		dataDefinitionField.setDefaultValue(
			LocalizationUtil.toLocalizedValues(
				jsonObject.getJSONObject("placeholder")));
		dataDefinitionField.setDefaultValue(
			LocalizationUtil.toLocalizedValues(
				jsonObject.getJSONObject("predefinedValue")));
		dataDefinitionField.setDefaultValue(
			LocalizationUtil.toLocalizedValues(
				jsonObject.getJSONObject("tooltip")));

		return dataDefinitionField;
	}

	@Override
	public JSONObject toJSONObject() throws Exception {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put(
			"autocompleteEnabled",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(),
				"autocompleteEnabled", false)
		).put(
			"displayStyle",
			CustomPropertiesUtil.getString(
				dataDefinitionField.getCustomProperties(), "displayStyle")
		).put(
			"inline",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "inline", false)
		).put(
			"options",
			DataFieldOptionUtil.toJSONObject(
				CustomPropertiesUtil.getDataFieldOptions(
					dataDefinitionField.getCustomProperties(), "options"))
		).put(
			"showAsSwitcher",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "showAsSwitcher",
				true)
		).put(
			"placeholder",
			CustomPropertiesUtil.getMap(
				dataDefinitionField.getCustomProperties(), "placeholder")
		).put(
			"predefinedValue",
			LocalizationUtil.toJSONObject(dataDefinitionField.getDefaultValue())
		).put(
			"tooltip",
			CustomPropertiesUtil.getMap(
				dataDefinitionField.getCustomProperties(), "tooltip")
		);
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		context.put(
			"autocompleteEnabled",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(),
				"autocompleteEnabled", false));
		context.put(
			"inline",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "inline", false));
		context.put(
			"options",
			DataFieldOptionUtil.toDataFieldOptions(
				CustomPropertiesUtil.getDataFieldOptions(
					dataDefinitionField.getCustomProperties(), "options"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"placeholder",
			LocalizationUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "placeholder")));
		context.put(
			"predefinedValue",
			LocalizationUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				dataDefinitionField.getDefaultValue()));
		context.put(
			"showAsSwitcher",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "showAsSwitcher",
				false));
		context.put(
			"tooltip",
			LocalizationUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "tooltip")));
		context.put(
			"value",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "value", false));
	}

}