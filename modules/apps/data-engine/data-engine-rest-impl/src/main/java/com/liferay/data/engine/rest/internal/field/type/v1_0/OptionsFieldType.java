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
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.DataFieldOptionUtil;
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.portal.kernel.json.JSONObject;
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
		"data.engine.field.type.icon=icon-font",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Options/Options.es",
		"data.engine.field.type.system=true"
	},
	service = FieldType.class
)
public class OptionsFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put(
			"allowEmptyOptions", jsonObject.getBoolean("allowEmptyOptions"));
		customProperties.put("value", jsonObject.getString("value"));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "options";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		return jsonObject.put(
			"allowEmptyOptions",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(),
				"allowEmptyOptions", false)
		).put(
			"value",
			DataFieldOptionUtil.toJSONObject(
				spiDataDefinitionField.getCustomProperties(), "value")
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		context.put(
			"allowEmptyOptions",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(),
				"allowEmptyOptions", false));
		context.put(
			"value",
			DataFieldOptionUtil.toJSONObject(
				spiDataDefinitionField.getCustomProperties(), "value"));
	}

}