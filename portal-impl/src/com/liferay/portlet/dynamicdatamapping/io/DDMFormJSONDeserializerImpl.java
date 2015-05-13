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
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormFieldOptions;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldType;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeProperty;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypePropertyJSONTransformer;
import com.liferay.portlet.dynamicdatamapping.registry.DDMFormFieldTypeRegistryUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormJSONDeserializerImpl implements DDMFormJSONDeserializer {

	@Override
	public DDMForm deserialize(String serializedDDMForm)
		throws PortalException {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				serializedDDMForm);

			DDMForm ddmForm = new DDMForm();

			setDDMFormAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
			setDDMFormDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmForm);
			setDDMFormFields(jsonObject.getJSONArray("fields"), ddmForm);
			setDDMFormLocalizedValuesDefaultLocale(ddmForm);

			return ddmForm;
		}
		catch (JSONException jsone) {
			throw new PortalException(jsone);
		}
	}

	protected Object fromJSON(
		String serializedDDMFormFieldPropertyValue,
		DDMFormFieldTypePropertyJSONTransformer
			ddmFormFieldTypePropertyJSONTransformer) {

		return ddmFormFieldTypePropertyJSONTransformer.fromJSON(
			serializedDDMFormFieldPropertyValue);
	}

	protected Set<Locale> getAvailableLocales(JSONArray jsonArray) {
		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected DDMFormField getDDMFormField(JSONObject jsonObject) {
		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		setDDMFormFieldOptionalProperties(jsonObject, ddmFormField);
		setDDMFormFieldRequiredProperties(jsonObject, ddmFormField);

		setNestedDDMFormField(
			jsonObject.getJSONArray("nestedFields"), ddmFormField);

		return ddmFormField;
	}

	protected List<DDMFormField> getDDMFormFields(JSONArray jsonArray) {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField ddmFormField = getDDMFormField(
				jsonArray.getJSONObject(i));

			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected void setDDMFormAvailableLocales(
		JSONArray jsonArray, DDMForm ddmForm) {

		Set<Locale> availableLocales = getAvailableLocales(jsonArray);

		ddmForm.setAvailableLocales(availableLocales);
	}

	protected void setDDMFormDefaultLocale(
		String defaultLanguageId, DDMForm ddmForm) {

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		ddmForm.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFieldLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale defaultLocale) {

		if (localizedValue == null) {
			return;
		}

		localizedValue.setDefaultLocale(defaultLocale);
	}

	protected void setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getLabel(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getPredefinedValue(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getStyle(), defaultLocale);

		setDDMFormFieldLocalizedValueDefaultLocale(
			ddmFormField.getTip(), defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		if (ddmFormFieldOptions != null) {
			ddmFormFieldOptions.setDefaultLocale(defaultLocale);
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	protected void setDDMFormFieldOptionalProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		DDMFormFieldType ddmFormFieldType =
			DDMFormFieldTypeRegistryUtil.getDDMFormFieldType(
				ddmFormField.getType());

		for (DDMFormFieldTypeProperty ddmFormFieldTypeProperty :
				ddmFormFieldType.getOptionalProperties()) {

			setDDMFormFieldOptionalProperty(
				jsonObject, ddmFormField, ddmFormFieldTypeProperty);
		}
	}

	protected void setDDMFormFieldOptionalProperty(
		JSONObject jsonObject, DDMFormField ddmFormField,
		DDMFormFieldTypeProperty ddmFormFieldTypeProperty) {

		String propertyName = ddmFormFieldTypeProperty.getName();

		Object ddmFormFieldPropertyValue = fromJSON(
			jsonObject.getString(propertyName),
			ddmFormFieldTypeProperty.
				getDDMFormFieldTypePropertyJSONTransformer());

		if (ddmFormFieldPropertyValue == null) {
			return;
		}

		ddmFormField.setOptionalProperty(
			propertyName, ddmFormFieldPropertyValue);
	}

	protected void setDDMFormFieldRequiredProperties(
		JSONObject jsonObject, DDMFormField ddmFormField) {

		for (DDMFormFieldTypeProperty ddmFormFieldTypeProperty :
				DDMFormFieldType.REQUIRED_PROPERTIES) {

			setDDMFormFieldRequiredProperty(
				jsonObject, ddmFormField, ddmFormFieldTypeProperty);
		}
	}

	protected void setDDMFormFieldRequiredProperty(
		JSONObject jsonObject, DDMFormField ddmFormField,
		DDMFormFieldTypeProperty ddmFormFieldTypeProperty) {

		String propertyName = ddmFormFieldTypeProperty.getName();

		Object ddmFormFieldPropertyValue = fromJSON(
			jsonObject.getString(propertyName),
			ddmFormFieldTypeProperty.
				getDDMFormFieldTypePropertyJSONTransformer());

		if (ddmFormFieldPropertyValue == null) {
			return;
		}

		ddmFormField.setRequiredProperty(
			propertyName, ddmFormFieldPropertyValue);
	}

	protected void setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = getDDMFormFields(jsonArray);

		ddmForm.setDDMFormFields(ddmFormFields);
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected void setNestedDDMFormField(
		JSONArray jsonArray, DDMFormField ddmFormField) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormField> nestedDDMFormFields = getDDMFormFields(jsonArray);

		ddmFormField.setNestedDDMFormFields(nestedDDMFormFields);
	}

}