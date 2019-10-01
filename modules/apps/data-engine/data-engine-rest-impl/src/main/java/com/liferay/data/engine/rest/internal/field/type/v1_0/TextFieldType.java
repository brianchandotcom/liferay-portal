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

import com.liferay.data.engine.field.type.BaseFieldType;
import com.liferay.data.engine.field.type.FieldType;
import com.liferay.data.engine.field.type.FieldTypeTracker;
import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.DataFieldOptionUtil;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcelo Mello
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.description=text-field-type-description",
		"data.engine.field.type.display.order:Integer=1",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=text",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Text/Text.es",
		"data.engine.field.type.label=text-field-type-label"
	},
	service = FieldType.class
)
public class TextFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put(
			"autocompleteEnabled",
			jsonObject.getBoolean("autocompleteEnabled"));
		customProperties.put(
			"displayStyle", jsonObject.getString("displayStyle"));
		customProperties.put(
			"options",
			DataFieldOptionUtil.toLocalizedDataFieldOptions(
				(JSONObject)GetterUtil.getObject(
					jsonObject.getJSONObject("options"),
					JSONFactoryUtil.createJSONObject())));
		customProperties.put(
			"placeholder",
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("placeholder")));
		customProperties.put(
			"tooltip",
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("tooltip")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "text";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		return jsonObject.put(
			"autocompleteEnabled",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(),
				"autocompleteEnabled", false)
		).put(
			"displayStyle",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(), "displayStyle")
		).put(
			"inline",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "inline", false)
		).put(
			"options",
			DataFieldOptionUtil.toJSONObject(
				spiDataDefinitionField.getCustomProperties(), "options")
		).put(
			"showAsSwitcher",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "showAsSwitcher",
				true)
		).put(
			"placeholder",
			CustomPropertiesUtil.getMap(
				spiDataDefinitionField.getCustomProperties(), "placeholder")
		).put(
			"tooltip",
			CustomPropertiesUtil.getMap(
				spiDataDefinitionField.getCustomProperties(), "tooltip")
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		context.put(
			"autocompleteEnabled",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(),
				"autocompleteEnabled", false));
		context.put(
			"inline",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "inline", false));
		context.put(
			"options",
			DataFieldOptionUtil.getLocalizedDataFieldOptions(
				spiDataDefinitionField.getCustomProperties(), "options",
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"placeholder",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"placeholder"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"predefinedValue",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"predefinedValue"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"showAsSwitcher",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "showAsSwitcher",
				false));
		context.put(
			"tooltip",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "tooltip"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"value",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(), "value"));
	}

}