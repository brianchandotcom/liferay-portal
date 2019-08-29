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

package com.liferay.dynamic.data.mapping.internal.io;

import com.liferay.dynamic.data.mapping.form.field.type.BaseDDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldRenderer;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeResponse;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true, property = "ddm.form.field.types.serializer.type=json",
	service = DDMFormFieldTypesSerializer.class
)
public class DDMFormFieldTypesJSONSerializer
	implements DDMFormFieldTypesSerializer {

	@Override
	public DDMFormFieldTypesSerializerSerializeResponse serialize(
		DDMFormFieldTypesSerializerSerializeRequest
			ddmFormFieldTypesSerializerSerializeRequest) {

		List<DDMFormFieldType> ddmFormFieldTypes =
			ddmFormFieldTypesSerializerSerializeRequest.getDdmFormFieldTypes();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		try {
			for (DDMFormFieldType ddmFormFieldType : ddmFormFieldTypes) {
				jsonArray.put(toJSONObject(ddmFormFieldType));
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		DDMFormFieldTypesSerializerSerializeResponse.Builder builder =
			DDMFormFieldTypesSerializerSerializeResponse.Builder.newBuilder(
				jsonArray.toJSONString());

		return builder.build();
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected JSONObject toJSONObject(DDMFormFieldType ddmFormFieldType)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Map<String, Object> ddmFormFieldTypeProperties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldType.getName());

		jsonObject.put(
			"group",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.group")
		).put(
			"icon",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.icon",
				"icon-ok-circle")
		).put(
			"javaScriptClass",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.js.class.name",
				"Liferay.DDM.Renderer.Field")
		).put(
			"javaScriptModule",
			MapUtil.getString(
				ddmFormFieldTypeProperties, "ddm.form.field.type.js.module",
				"liferay-ddm-form-renderer-field")
		);

		String description = MapUtil.getString(
			ddmFormFieldTypeProperties, "ddm.form.field.type.description");

		String label = MapUtil.getString(
			ddmFormFieldTypeProperties, "ddm.form.field.type.label");

		Locale locale = LocaleThreadLocal.getThemeDisplayLocale();

		try {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", locale, ddmFormFieldType.getClass());

			if (Validator.isNotNull(description)) {
				jsonObject.put(
					"description",
					LanguageUtil.get(resourceBundle, description));
			}

			if (Validator.isNotNull(label)) {
				jsonObject.put(
					"label", LanguageUtil.get(resourceBundle, label));
			}
		}
		catch (MissingResourceException mre) {
			if (_log.isWarnEnabled()) {
				_log.warn(mre, mre);
			}
		}

		jsonObject.put(
			"name", ddmFormFieldType.getName()
		).put(
			"system",
			MapUtil.getBoolean(
				ddmFormFieldTypeProperties, "ddm.form.field.type.system")
		);

		DDMFormFieldRenderer ddmFormFieldRenderer =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldRenderer(
				ddmFormFieldType.getName());

		if (ddmFormFieldRenderer instanceof BaseDDMFormFieldRenderer) {
			BaseDDMFormFieldRenderer baseDDMFormFieldRenderer =
				(BaseDDMFormFieldRenderer)ddmFormFieldRenderer;

			jsonObject.put(
				"templateNamespace",
				baseDDMFormFieldRenderer.getTemplateNamespace());
		}

		return jsonObject;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormFieldTypesJSONSerializer.class);

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private JSONFactory _jsonFactory;

}