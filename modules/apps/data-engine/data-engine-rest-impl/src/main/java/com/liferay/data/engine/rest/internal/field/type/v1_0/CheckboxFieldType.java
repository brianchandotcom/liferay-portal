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
import com.liferay.data.engine.spi.definition.SPIDataDefinitionField;
import com.liferay.data.engine.spi.field.type.BaseFieldType;
import com.liferay.data.engine.spi.field.type.FieldType;
import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.data.domain=boolean",
		"data.engine.field.type.description=checkbox-field-type-description",
		"data.engine.field.type.display.order:Integer=6",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=check-circle",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/metal/Checkbox/Checkbox.es",
		"data.engine.field.type.label=checkbox-field-type-label",
		"data.engine.field.type.system=true"
	},
	service = FieldType.class
)
public class CheckboxFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put(
			"showAsSwitcher", jsonObject.getBoolean("showAsSwitcher"));

		spiDataDefinitionField.setDefaultValue(
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("predefinedValue")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "checkbox";
	}

	@Override
	public JSONObject toJSONObject(
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(spiDataDefinitionField);

		return jsonObject.put(
			"predefinedValue",
			LocalizedValueUtil.toJSONObject(
				spiDataDefinitionField.getDefaultValue())
		).put(
			"showAsSwitcher",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "showAsSwitcher",
				true)
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		context.put(
			"predefinedValue",
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(),
					"predefinedValue"),
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"showAsSwitcher",
			MapUtil.getBoolean(context, "showAsSwitcher", false));
		context.put("value", MapUtil.getBoolean(context, "value", false));
	}

}