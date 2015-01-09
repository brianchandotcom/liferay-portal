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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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

	protected void addOptionValueLabels(
		JSONObject jsonObject, DDMFormFieldOptions ddmFormFieldOptions,
		String optionValue) {

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			ddmFormFieldOptions.addOptionLabel(
				optionValue, LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}
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

		ddmFormField.setDataType(jsonObject.getString("dataType"));
		ddmFormField.setIndexType(jsonObject.getString("indexType"));
		ddmFormField.setLocalizable(jsonObject.getBoolean("localizable", true));
		ddmFormField.setMultiple(jsonObject.getBoolean("multiple"));
		ddmFormField.setNamespace(jsonObject.getString("fieldNamespace"));
		ddmFormField.setReadOnly(jsonObject.getBoolean("readOnly"));
		ddmFormField.setRepeatable(jsonObject.getBoolean("repeatable"));
		ddmFormField.setRequired(jsonObject.getBoolean("required"));
		ddmFormField.setShowLabel(jsonObject.getBoolean("showLabel", true));

		setDDMFormFieldLocalizedValue(
			jsonObject.getJSONObject("label"), ddmFormField.getLabel());
		setDDMFormFieldLocalizedValue(
			jsonObject.getJSONObject("predefinedValue"),
			ddmFormField.getPredefinedValue());
		setDDMFormFieldLocalizedValue(
			jsonObject.getJSONObject("style"), ddmFormField.getStyle());
		setDDMFormFieldLocalizedValue(
			jsonObject.getJSONObject("tip"), ddmFormField.getTip());

		if (type.equals("radio") || type.equals("select")) {
			setDDMFormFieldOptions(
				jsonObject.getJSONArray("options"), ddmFormField);
		}
		else {
			setNestedDDMFormField(
				jsonObject.getJSONArray("nestedFields"), ddmFormField);
		}

		return ddmFormField;
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions(JSONArray jsonArray) {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String value = jsonObject.getString("value");

			ddmFormFieldOptions.addOption(value);

			addOptionValueLabels(
				jsonObject.getJSONObject("label"), ddmFormFieldOptions, value);
		}

		return ddmFormFieldOptions;
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

	protected void setDDMFormFieldLocalizedValue(
		JSONObject jsonObject, LocalizedValue localizedValue) {

		if (jsonObject == null) {
			return;
		}

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}
	}

	protected void setDDMFormFieldLocalizedValuesDefaultLocale(
		DDMFormField ddmFormField, Locale defaultLocale) {

		LocalizedValue label = ddmFormField.getLabel();

		label.setDefaultLocale(defaultLocale);

		LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

		predefinedValue.setDefaultLocale(defaultLocale);

		LocalizedValue style = ddmFormField.getStyle();

		style.setDefaultLocale(defaultLocale);

		LocalizedValue tip = ddmFormField.getTip();

		tip.setDefaultLocale(defaultLocale);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(defaultLocale);

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			setDDMFormFieldLocalizedValuesDefaultLocale(
				nestedDDMFormField, defaultLocale);
		}
	}

	protected void setDDMFormFieldOptions(
		JSONArray jsonArray, DDMFormField ddmFormField) {

		DDMFormFieldOptions ddmFormFieldOptions = getDDMFormFieldOptions(
			jsonArray);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
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