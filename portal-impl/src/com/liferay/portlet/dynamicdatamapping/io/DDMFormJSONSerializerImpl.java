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

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSetting;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeSettingJSONConverter;

import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONSerializerImpl implements DDMFormJSONSerializer {

	@Override
	public String serialize(DDMForm ddmForm) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addAvailableLanguageIds(jsonObject, ddmForm.getAvailableLocales());
		addDefaultLanguageId(jsonObject, ddmForm.getDefaultLocale());
		addFields(jsonObject, ddmForm.getDDMFormFields());

		return jsonObject.toString();
	}

	protected void addAvailableLanguageIds(
		JSONObject jsonObject, Set<Locale> availableLocales) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : availableLocales) {
			jsonArray.put(LocaleUtil.toLanguageId(availableLocale));
		}

		jsonObject.put("availableLanguageIds", jsonArray);
	}

	protected void addDefaultLanguageId(
		JSONObject jsonObject, Locale defaultLocale) {

		jsonObject.put(
			"defaultLanguageId", LocaleUtil.toLanguageId(defaultLocale));
	}

	protected void addFields(
		JSONObject jsonObject, List<DDMFormField> ddmFormFields) {

		jsonObject.put("fields", toJSONArray(ddmFormFields));
	}

	protected void addNestedFields(
		JSONObject jsonObject, List<DDMFormField> nestedDDMFormFields) {

		if (nestedDDMFormFields.isEmpty()) {
			return;
		}

		jsonObject.put("nestedFields", toJSONArray(nestedDDMFormFields));
	}

	protected void addOptionalProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		DDMFormFieldType ddmFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(
				ddmFormField.getType());

		for (DDMFormFieldTypeSetting ddmFormFieldTypeSetting :
				ddmFormFieldType.getOptionalSettings()) {

			addProperty(jsonObject, ddmFormField, ddmFormFieldTypeSetting);
		}
	}

	protected void addProperty(
		JSONObject jsonObject, DDMFormField ddmFormField,
		DDMFormFieldTypeSetting ddmFormFieldTypeSetting) {

		Object property = ddmFormField.getProperty(
			ddmFormFieldTypeSetting.getName());

		if (property == null) {
			return;
		}

		DDMFormFieldTypeSettingJSONConverter<?, ?>
			ddmFormFieldTypeSettingJSONConverter =
				ddmFormFieldTypeSetting.
					getDDMFormFieldTypeSettingJSONConverter();

		addProperty(
			jsonObject, ddmFormFieldTypeSetting.getName(),
			ddmFormFieldTypeSettingJSONConverter.toJSON(property));
	}

	protected void addProperty(
		JSONObject jsonObject, String propertyName, Object propertyValue) {

		if (propertyValue instanceof JSONArray) {
			jsonObject.put(propertyName, (JSONArray)propertyValue);
		}
		else if (propertyValue instanceof JSONObject) {
			jsonObject.put(propertyName, (JSONObject)propertyValue);
		}
		else {
			jsonObject.put(propertyName, propertyValue);
		}
	}

	protected void addRequiredProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		for (DDMFormFieldTypeSetting ddmFormFieldTypeSetting :
				DDMFormFieldType.REQUIRED_PROPERTIES) {

			addProperty(jsonObject, ddmFormField, ddmFormFieldTypeSetting);
		}
	}

	protected JSONArray toJSONArray(List<DDMFormField> ddmFormFields) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDMFormField ddmFormField : ddmFormFields) {
			jsonArray.put(toJSONObject(ddmFormField));
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(DDMFormField ddmFormField) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		addOptionalProperties(jsonObject, ddmFormField);
		addRequiredProperties(jsonObject, ddmFormField);

		addNestedFields(jsonObject, ddmFormField.getNestedDDMFormFields());

		return jsonObject;
	}

}