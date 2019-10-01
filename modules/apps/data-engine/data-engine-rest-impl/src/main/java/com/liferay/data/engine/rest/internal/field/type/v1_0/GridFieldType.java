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
		"data.engine.field.type.description=grid-field-type-description",
		"data.engine.field.type.display.order:Integer=8",
		"data.engine.field.type.group=basic",
		"data.engine.field.type.icon=table2",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Grid/Grid.es",
		"data.engine.field.type.label=grid-field-type-label"
	},
	service = FieldType.class
)
public class GridFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put(
			"columns",
			DataFieldOptionUtil.toLocalizedDataFieldOptions(
				(JSONObject)GetterUtil.getObject(
					jsonObject.getJSONObject("columns"),
					JSONFactoryUtil.createJSONObject())));
		customProperties.put(
			"rows",
			DataFieldOptionUtil.toLocalizedDataFieldOptions(
				(JSONObject)GetterUtil.getObject(
					jsonObject.getJSONObject("rows"),
					JSONFactoryUtil.createJSONObject())));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "grid";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		return jsonObject.put(
			"columns",
			DataFieldOptionUtil.toJSONObject(
				spiDataDefinitionField.getCustomProperties(), "columns")
		).put(
			"rows",
			DataFieldOptionUtil.toJSONObject(
				spiDataDefinitionField.getCustomProperties(), "rows")
		);
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		context.put(
			"columns",
			DataFieldOptionUtil.getLocalizedDataFieldOptions(
				spiDataDefinitionField.getCustomProperties(), "columns",
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"rows",
			DataFieldOptionUtil.getLocalizedDataFieldOptions(
				spiDataDefinitionField.getCustomProperties(), "rows",
				LanguageUtil.getLanguageId(httpServletRequest)));
		context.put(
			"value",
			JSONFactoryUtil.looseDeserialize(
				CustomPropertiesUtil.getString(
					spiDataDefinitionField.getCustomProperties(), "value",
					"{}")));
	}

}