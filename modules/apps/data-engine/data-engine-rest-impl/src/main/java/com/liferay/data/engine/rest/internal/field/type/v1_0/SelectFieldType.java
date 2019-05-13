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

import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.DataFieldOptionUtil;
import com.liferay.data.engine.spi.field.type.BaseFieldType;
import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.definition.SPIDataDefinitionField;
import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.data.domain=list",
		"data.engine.field.type.description=select-field-type-description",
		"data.engine.field.type.display.order:Integer=3",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=list",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/metal/Select/Select.es",
		"data.engine.field.type.label=select-field-type-label"
	},
	service = FieldType.class
)
public class SelectFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put(
			"dataSourceType", jsonObject.getString("dataSourceType"));
		customProperties.put("multiple", jsonObject.getBoolean("multiple"));
		customProperties.put(
			"options",
			DataFieldOptionUtil.toDataFieldOptions(
				jsonObject.getJSONObject("options")));
		customProperties.put(
			"predefinedValue",
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("predefinedValue")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "select";
	}

	@Override
	public JSONObject toJSONObject(
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(spiDataDefinitionField);

		return jsonObject.put(
			"dataSourceType",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(), "dataSourceType")
		).put(
			"multiple",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "multiple", false)
		).put(
			"options",
			DataFieldOptionUtil.toJSONObject(
				CustomPropertiesUtil.getDataFieldOptions(
					spiDataDefinitionField.getCustomProperties(), "options"))
		).put(
			"predefinedValue",
			CustomPropertiesUtil.getMap(
				spiDataDefinitionField.getCustomProperties(), "predefinedValue")
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		context.put(
			"dataSourceType",
			MapUtil.getString(
				spiDataDefinitionField.getCustomProperties(),
				"dataSourceType"));
		context.put(
			"multiple",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "multiple",
				false));
		context.put(
			"options",
			DataFieldOptionUtil.toDataFieldOptions(
				CustomPropertiesUtil.getDataFieldOptions(
					spiDataDefinitionField.getCustomProperties(), "options"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"predefinedValue",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"predefinedValue"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put("strings", _getLanguageTerms(httpServletRequest));
		context.put(
			"value",
			CustomPropertiesUtil.getValues(
				spiDataDefinitionField.getCustomProperties(), "value"));
	}

	private Map<String, String> _getLanguageTerms(
		HttpServletRequest httpServletRequest) {

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