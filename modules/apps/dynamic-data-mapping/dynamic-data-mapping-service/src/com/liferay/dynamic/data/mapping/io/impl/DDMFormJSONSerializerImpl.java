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

import com.liferay.dynamic.data.mapping.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.registry.DDMFormFactory;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.registry.DDMFormFieldTypeRegistryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Map;
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

	protected void addProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		DDMForm ddmFormFieldTypeSettingsDDMForm =
			getDDMFormFieldTypeSettingsDDMForm(ddmFormField.getType());

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			addProperty(jsonObject, ddmFormField, ddmFormFieldTypeSetting);
		}
	}

	protected void addProperty(
		JSONObject jsonObject, DDMFormField ddmFormField,
		DDMFormField ddmFormFieldTypeSetting) {

		Object property = ddmFormField.getProperty(
			ddmFormFieldTypeSetting.getName());

		if (property == null) {
			return;
		}

		addProperty(
			jsonObject, ddmFormFieldTypeSetting.getName(),
			serializeDDMFormFieldProperty(property, ddmFormFieldTypeSetting));
	}

	protected void addProperty(
		JSONObject jsonObject, String propertyName, Object propertyValue) {

		if (propertyValue == null) {
			return;
		}

		jsonObject.put(propertyName, propertyValue);
	}

	protected DDMForm getDDMFormFieldTypeSettingsDDMForm(String type) {
		DDMFormFieldType ddmFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(type);

		if (ddmFormFieldType == null) {
			ddmFormFieldType = DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(
				"text");
		}

		return DDMFormFactory.create(
			ddmFormFieldType.getDDMFormFieldTypeSettings());
	}

	protected Object serializeDDMFormFieldProperty(
		Object property, DDMFormField ddmFormFieldTypeSetting) {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return toJSONObject((LocalizedValue)property);
		}

		String dataType = ddmFormFieldTypeSetting.getDataType();

		if (Validator.equals(dataType, "boolean")) {
			return GetterUtil.getBoolean(property);
		}
		else if (Validator.equals(dataType, "ddm-options")) {
			return toJSONArray((DDMFormFieldOptions)property);
		}
		else {
			return String.valueOf(property);
		}
	}

	protected JSONArray toJSONArray(DDMFormFieldOptions ddmFormFieldOptions) {
		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		if (optionValues.isEmpty()) {
			return null;
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String optionValue : optionValues) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			jsonObject.put("value", optionValue);
			jsonObject.put(
				"label",
				toJSONObject(ddmFormFieldOptions.getOptionLabels(optionValue)));

			jsonArray.put(jsonObject);
		}

		return jsonArray;
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

		addProperties(jsonObject, ddmFormField);

		addNestedFields(jsonObject, ddmFormField.getNestedDDMFormFields());

		return jsonObject;
	}

	protected JSONObject toJSONObject(LocalizedValue localizedValue) {
		Map<Locale, String> values = localizedValue.getValues();

		if (values.isEmpty()) {
			return null;
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				localizedValue.getString(availableLocale));
		}

		return jsonObject;
	}

}