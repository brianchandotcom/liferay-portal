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
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.data.domain=list",
		"data.engine.field.type.description=checkbox-multiple-field-type-description",
		"data.engine.field.type.display.order:Integer=4",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=select-from-list",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/CheckboxMultiple/CheckboxMultiple.es",
		"data.engine.field.type.label=checkbox-multiple-field-type-label"
	},
	service = FieldType.class
)
public class CheckboxMultipleFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put("inline", jsonObject.getBoolean("inline"));
		customProperties.put(
			"options",
			DataFieldOptionUtil.toLocalizedDataFieldOptions(
				(JSONObject)GetterUtil.getObject(
					jsonObject.getJSONObject("options"),
					JSONFactoryUtil.createJSONObject())));
		customProperties.put(
			"showAsSwitcher", jsonObject.getBoolean("showAsSwitcher"));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "checkbox_multiple";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		return jsonObject.put(
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
				false)
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
			DataFieldOptionUtil.getLocalizedDataFieldOptions(
				spiDataDefinitionField.getCustomProperties(), "options",
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"showAsSwitcher",
			MapUtil.getBoolean(
				spiDataDefinitionField.getCustomProperties(), "showAsSwitcher",
				false));
		context.put(
			"value",
			CustomPropertiesUtil.getValues(
				spiDataDefinitionField.getCustomProperties(), "value"));
	}

}