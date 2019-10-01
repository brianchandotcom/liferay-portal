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
import com.liferay.data.engine.spi.dto.SPIDataDefinitionField;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;
import com.liferay.portal.template.soy.util.SoyRawData;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcelo Mello
 */
@Component(
	immediate = true,
	property = {
		"data.engine.field.type.description=paragraph-field-type-description",
		"data.engine.field.type.display.order:Integer=1",
		"data.engine.field.type.group=interface",
		"data.engine.field.type.icon=paragraph",
		"data.engine.field.type.js.module=dynamic-data-mapping-form-field-type/Paragraph/Paragraph.es",
		"data.engine.field.type.label=paragraph-field-type-label"
	},
	service = FieldType.class
)
public class ParagraphFieldType extends BaseFieldType {

	@Override
	public SPIDataDefinitionField deserialize(
			FieldTypeTracker fieldTypeTracker, JSONObject jsonObject)
		throws Exception {

		SPIDataDefinitionField spiDataDefinitionField = super.deserialize(
			fieldTypeTracker, jsonObject);

		Map<String, Object> customProperties =
			spiDataDefinitionField.getCustomProperties();

		customProperties.put(
			"text",
			LocalizedValueUtil.toLocalizedValues(
				jsonObject.getJSONObject("text")));

		return spiDataDefinitionField;
	}

	@Override
	public String getName() {
		return "paragraph";
	}

	@Override
	public JSONObject toJSONObject(
			FieldTypeTracker fieldTypeTracker,
			SPIDataDefinitionField spiDataDefinitionField)
		throws Exception {

		JSONObject jsonObject = super.toJSONObject(
			fieldTypeTracker, spiDataDefinitionField);

		return jsonObject.put(
			"text",
			LocalizedValueUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "text")));
	}

	@Override
	protected void includeContext(
		Map<String, Object> context, HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SPIDataDefinitionField spiDataDefinitionField) {

		SoyRawData soyRawData = _soyDataFactory.createSoyRawData(
			MapUtil.getString(
				CustomPropertiesUtil.getMap(
					spiDataDefinitionField.getCustomProperties(), "text"),
				LanguageUtil.getLanguageId(httpServletRequest)));

		context.put("text", soyRawData.getValue());
	}

	@Reference
	private SoyDataFactory _soyDataFactory;

}