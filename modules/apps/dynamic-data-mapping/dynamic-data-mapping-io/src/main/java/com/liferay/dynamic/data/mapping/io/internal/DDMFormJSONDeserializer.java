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

package com.liferay.dynamic.data.mapping.io.internal;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormRule;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, property = "ddm.form.deserializer.type=json")
public class DDMFormJSONDeserializer implements DDMFormDeserializer {

	@Override
	public DDMFormDeserializerDeserializeResponse deserialize(
		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest) {

		DDMForm ddmForm = new DDMForm();

		DDMFormDeserializerDeserializeResponse.Builder builder =
			DDMFormDeserializerDeserializeResponse.Builder.newBuilder(ddmForm);

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				ddmFormDeserializerDeserializeRequest.getContent());

			setDDMFormAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
			setDDMFormDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmForm);
			setDDMFormFields(jsonObject.getJSONArray("fields"), ddmForm);
			setDDMFormRules(jsonObject.getJSONArray("rules"), ddmForm);

			setDDMFormLocalizedValuesDefaultLocale(ddmForm);
			setDDMFormSuccessPageSettings(
				jsonObject.getJSONObject("successPage"), ddmForm);

			return builder.build();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return builder.build();
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

	protected DDMFormFieldOptions deserializeDDMFormFieldOptions(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return new DDMFormFieldOptions();
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			serializedDDMFormFieldProperty);

		return getDDMFormFieldOptions(jsonArray);
	}

	protected Object deserializeDDMFormFieldProperty(
			String serializedDDMFormFieldProperty,
			DDMFormField ddmFormFieldTypeSetting)
		throws PortalException {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return deserializeLocalizedValue(serializedDDMFormFieldProperty);
		}

		String dataType = ddmFormFieldTypeSetting.getDataType();

		if (Objects.equals(dataType, "boolean")) {
			return Boolean.valueOf(serializedDDMFormFieldProperty);
		}
		else if (Objects.equals(dataType, "ddm-options")) {
			return deserializeDDMFormFieldOptions(
				serializedDDMFormFieldProperty);
		}
		else if (Objects.equals(
					 ddmFormFieldTypeSetting.getType(), "validation")) {

			return deserializeDDMFormFieldValidation(
				serializedDDMFormFieldProperty);
		}
		else {
			return serializedDDMFormFieldProperty;
		}
	}

	protected DDMFormFieldValidation deserializeDDMFormFieldValidation(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return ddmFormFieldValidation;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormFieldProperty);

		ddmFormFieldValidation.setExpression(
			jsonObject.getString("expression"));
		ddmFormFieldValidation.setErrorMessage(
			jsonObject.getString("errorMessage"));

		return ddmFormFieldValidation;
	}

	protected LocalizedValue deserializeLocalizedValue(String value)
		throws PortalException {

		LocalizedValue localizedValue = new LocalizedValue();

		if (Validator.isNull(value)) {
			return localizedValue;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(value);

		Iterator<String> itr = jsonObject.keys();

		while (itr.hasNext()) {
			String languageId = itr.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
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

	protected DDMFormField getDDMFormField(JSONObject jsonObject)
		throws PortalException {

		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		setDDMFormFieldProperties(jsonObject, ddmFormField);

		setNestedDDMFormField(
			jsonObject.getJSONArray("nestedFields"), ddmFormField);

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

	protected List<DDMFormField> getDDMFormFields(JSONArray jsonArray)
		throws PortalException {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField ddmFormField = getDDMFormField(
				jsonArray.getJSONObject(i));

			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected DDMForm getDDMFormFieldTypeSettingsDDMForm(String type) {
		DDMFormFieldType ddmFormFieldType =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		return DDMFormFactory.create(ddmFormFieldTypeSettings);
	}

	protected DDMFormRule getDDMFormRule(JSONObject jsonObject) {
		String condition = jsonObject.getString("condition");

		List<String> actions = getDDMFormRuleActions(
			jsonObject.getJSONArray("actions"));

		DDMFormRule ddmFormRule = new DDMFormRule(condition, actions);

		boolean enabled = jsonObject.getBoolean("enabled", true);

		ddmFormRule.setEnabled(enabled);

		return ddmFormRule;
	}

	protected List<String> getDDMFormRuleActions(JSONArray jsonArray) {
		List<String> actions = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			actions.add(jsonArray.getString(i));
		}

		return actions;
	}

	protected List<DDMFormRule> getDDMFormRules(JSONArray jsonArray) {
		List<DDMFormRule> ddmFormRules = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormRule ddmFormRule = getDDMFormRule(
				jsonArray.getJSONObject(i));

			ddmFormRules.add(ddmFormRule);
		}

		return ddmFormRules;
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

	protected void setDDMFormFieldProperties(
			JSONObject jsonObject, DDMFormField ddmFormField)
		throws PortalException {

		DDMForm ddmFormFieldTypeSettingsDDMForm =
			getDDMFormFieldTypeSettingsDDMForm(ddmFormField.getType());

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			setDDMFormFieldProperty(
				jsonObject, ddmFormField, ddmFormFieldTypeSetting);
		}
	}

	protected void setDDMFormFieldProperty(
			JSONObject jsonObject, DDMFormField ddmFormField,
			DDMFormField ddmFormFieldTypeSetting)
		throws PortalException {

		String settingName = ddmFormFieldTypeSetting.getName();

		if (jsonObject.has(settingName)) {
			Object deserializedDDMFormFieldProperty =
				deserializeDDMFormFieldProperty(
					jsonObject.getString(settingName), ddmFormFieldTypeSetting);

			ddmFormField.setProperty(
				settingName, deserializedDDMFormFieldProperty);
		}
	}

	protected void setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm)
		throws PortalException {

		List<DDMFormField> ddmFormFields = getDDMFormFields(jsonArray);

		ddmForm.setDDMFormFields(ddmFormFields);
	}

	@Reference(unbind = "-")
	protected void setDDMFormFieldTypeServicesTracker(
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
	}

	protected void setDDMFormLocalizedValuesDefaultLocale(DDMForm ddmForm) {
		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected void setDDMFormRules(JSONArray jsonArray, DDMForm ddmForm) {
		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormRule> ddmFormRules = getDDMFormRules(jsonArray);

		ddmForm.setDDMFormRules(ddmFormRules);
	}

	protected void setDDMFormSuccessPageSettings(
			JSONObject jsonObject, DDMForm ddmForm)
		throws PortalException {

		if (jsonObject == null) {
			return;
		}

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			new DDMFormSuccessPageSettings(
				deserializeLocalizedValue(jsonObject.getString("body")),
				deserializeLocalizedValue(jsonObject.getString("title")),
				jsonObject.getBoolean("enabled"));

		ddmForm.setDDMFormSuccessPageSettings(ddmFormSuccessPageSettings);
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	protected void setNestedDDMFormField(
			JSONArray jsonArray, DDMFormField ddmFormField)
		throws PortalException {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		List<DDMFormField> nestedDDMFormFields = getDDMFormFields(jsonArray);

		ddmFormField.setNestedDDMFormFields(nestedDDMFormFields);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormJSONDeserializer.class);

	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;
	private JSONFactory _jsonFactory;

}