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

package com.liferay.portlet.dynamicdatamapping.io;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFactory;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldRenderer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettings;

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