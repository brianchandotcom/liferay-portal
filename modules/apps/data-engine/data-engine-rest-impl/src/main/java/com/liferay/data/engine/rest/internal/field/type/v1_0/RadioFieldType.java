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
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;

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
		"data.engine.field.type.description=radio-field-type-description",
		"data.engine.field.type.display.order:Integer=4",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=radio-button",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/metal/Radio/Radio.es",
		"data.engine.field.type.label=radio-field-type-label"
	},
	service = FieldType.class
)
public class RadioFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put("inline", jsonObject.getBoolean("inline"));
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
		return "radio";
	}

	@Override
	public JSONObject toJSONObject(
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(spiDataDefinitionField);

		return jsonObject.put(
			"inline",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "inline", false)
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
			"inline",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "inline", false));
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
		context.put(
			"value",
			_getValue(
				MapUtil.getString(
					spiDataDefinitionField.getCustomProperties(), "value",
					"[]")));
	}

	private String _getValue(String json) {
		try {
			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

			return String.valueOf(jsonArray.get(0));
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}

			return json;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(RadioFieldType.class);

}