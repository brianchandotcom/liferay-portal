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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;

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
		JSONObject jsonObject, List<Locale> availableLocales) {

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

	protected void addLocalizedProperty(
		JSONObject jsonObject, String propertyName,
		LocalizedValue localizedValue) {

		Map<Locale, String> values = localizedValue.getValues();

		if (values.isEmpty()) {
			return;
		}

		jsonObject.put(propertyName, toJSONObject(localizedValue));
	}

	protected void addNestedFields(
		JSONObject jsonObject, List<DDMFormField> nestedDDMFormFields) {

		if (nestedDDMFormFields.isEmpty()) {
			return;
		}

		jsonObject.put("nestedFields", toJSONArray(nestedDDMFormFields));
	}

	protected void addOptions(
		JSONObject jsonObject, DDMFormFieldOptions ddmFormFieldOptions) {

		Set<String> optionValues = ddmFormFieldOptions.getOptionsValues();

		if (optionValues.isEmpty()) {
			return;
		}

		jsonObject.put("options", toJSONArray(ddmFormFieldOptions));
	}

	protected void addSimpleProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		jsonObject.put("dataType", ddmFormField.getDataType());
		jsonObject.put("fieldNamespace", ddmFormField.getNamespace());
		jsonObject.put("indexType", ddmFormField.getIndexType());
		jsonObject.put("localizable", ddmFormField.isLocalizable());
		jsonObject.put("multiple", ddmFormField.isMultiple());
		jsonObject.put("name", ddmFormField.getName());
		jsonObject.put("readOnly", ddmFormField.isReadOnly());
		jsonObject.put("repeatable", ddmFormField.isRepeatable());
		jsonObject.put("required", ddmFormField.isRequired());
		jsonObject.put("type", ddmFormField.getType());
	}

	protected JSONArray toJSONArray(DDMFormFieldOptions ddmFormFieldOptions) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String optionValue : ddmFormFieldOptions.getOptionsValues()) {
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

		addLocalizedProperty(jsonObject, "label", ddmFormField.getLabel());
		addLocalizedProperty(
			jsonObject, "predefinedValue", ddmFormField.getPredefinedValue());
		addLocalizedProperty(jsonObject, "style", ddmFormField.getStyle());
		addLocalizedProperty(jsonObject, "tip", ddmFormField.getTip());
		addNestedFields(jsonObject, ddmFormField.getNestedDDMFormFields());
		addOptions(jsonObject, ddmFormField.getDDMFormFieldOptions());
		addSimpleProperties(jsonObject, ddmFormField);

		return jsonObject;
	}

	protected JSONObject toJSONObject(LocalizedValue localizedValue) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				localizedValue.getValue(availableLocale));
		}

		return jsonObject;
	}

}