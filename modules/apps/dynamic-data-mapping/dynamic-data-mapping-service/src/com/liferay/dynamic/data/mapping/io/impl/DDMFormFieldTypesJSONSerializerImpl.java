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

package com.liferay.dynamic.data.mapping.io.impl;

import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.registry.DDMFormFactory;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeSettings;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;

/**
 * @author Bruno Basto
 */
public class DDMFormFieldTypesJSONSerializerImpl
	implements DDMFormFieldTypesJSONSerializer {

	@Override
	public String serialize(List<DDMFormFieldType> ddmFormFieldTypes)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
			jsonArray.put(toJSONObject(ddmFormFieldType));
		}

		return jsonArray.toString();
	}

	protected JSONObject toJSONObject(
			Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings)
		throws PortalException {

		DDMForm ddmFormFieldTypeSettingsDDMForm = DDMFormFactory.create(
			ddmFormFieldTypeSettings);

		String serializedDDMFormFieldTypeSettings =
			DDMFormJSONSerializerUtil.serialize(
				ddmFormFieldTypeSettingsDDMForm);

		return JSONFactoryUtil.createJSONObject(
			serializedDDMFormFieldTypeSettings);
	}

	protected JSONObject toJSONObject(DDMFormFieldType ddmFormFieldType)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("icon", ddmFormFieldType.getIcon());
		jsonObject.put(
			"javaScriptClass",
			ddmFormFieldType.getDDMFormFieldTypeJavaScriptClass());
		jsonObject.put(
			"javaScriptModule",
			ddmFormFieldType.getDDMFormFieldTypeJavaScriptModule());
		jsonObject.put("name", ddmFormFieldType.getName());
		jsonObject.put(
			"settings",
			toJSONObject(ddmFormFieldType.getDDMFormFieldTypeSettings()));

		DDMFormFieldRenderer ddmFormFieldRenderer =
			ddmFormFieldType.getDDMFormFieldRenderer();

		jsonObject.put(
			"templateNamespace", ddmFormFieldRenderer.getTemplateNamespace());

		return jsonObject;
	}

}