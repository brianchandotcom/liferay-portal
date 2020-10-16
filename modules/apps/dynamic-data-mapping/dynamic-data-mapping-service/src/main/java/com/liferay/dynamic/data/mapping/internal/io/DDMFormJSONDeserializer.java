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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
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
@Component(
	immediate = true, property = "ddm.form.deserializer.type=json",
	service = DDMFormDeserializer.class
)
public class DDMFormJSONDeserializer implements DDMFormDeserializer {

	public static DDMFormDeserializerDeserializeResponse internalDeserialize(
		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest) {

		DDMForm ddmForm = new DDMForm();

		DDMFormDeserializerDeserializeResponse.Builder builder =
			DDMFormDeserializerDeserializeResponse.Builder.newBuilder(ddmForm);

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				ddmFormDeserializerDeserializeRequest.getContent());

			if (Validator.isNotNull(
					jsonObject.getString("definitionSchemaVersion"))) {

				ddmForm.setDefinitionSchemaVersion(
					jsonObject.getString("definitionSchemaVersion"));
			}

			setDDMFormAvailableLocales(
				jsonObject.getJSONArray("availableLanguageIds"), ddmForm);
			setDDMFormDefaultLocale(
				jsonObject.getString("defaultLanguageId"), ddmForm);
			setDDMFormFields(jsonObject.getJSONArray("fields"), ddmForm);

			setDDMFormLocalizedValuesDefaultLocale(ddmForm);
			setDDMFormRules(jsonObject.getJSONArray("rules"), ddmForm);
			setDDMFormSuccessPageSettings(
				jsonObject.getJSONObject("successPage"), ddmForm);

			return builder.build();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			builder = builder.exception(exception);
		}

		return builder.build();
	}

	@Override
	public DDMFormDeserializerDeserializeResponse deserialize(
		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest) {

		return internalDeserialize(ddmFormDeserializerDeserializeRequest);
	}

	protected static void addOptionValueLabels(
		JSONObject jsonObject, DDMFormFieldOptions ddmFormFieldOptions,
		String optionValue) {

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String languageId = iterator.next();

			ddmFormFieldOptions.addOptionLabel(
				optionValue, LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}
	}

	protected static DDMFormFieldOptions deserializeDDMFormFieldOptions(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return new DDMFormFieldOptions();
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray(
			serializedDDMFormFieldProperty);

		return getDDMFormFieldOptions(jsonArray);
	}

	protected static Object deserializeDDMFormFieldProperty(
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

		return serializedDDMFormFieldProperty;
	}

	protected static DDMFormFieldValidation deserializeDDMFormFieldValidation(
			String serializedDDMFormFieldProperty)
		throws PortalException {

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return ddmFormFieldValidation;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			serializedDDMFormFieldProperty);

		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			deserializeLocalizedValue(jsonObject.getString("errorMessage")));

		JSONObject expressionJSONObject = jsonObject.getJSONObject(
			"expression");

		if (expressionJSONObject != null) {
			ddmFormFieldValidation.setDDMFormFieldValidationExpression(
				new DDMFormFieldValidationExpression() {
					{
						setName(expressionJSONObject.getString("name"));
						setValue(expressionJSONObject.getString("value"));
					}
				});
		}
		else {
			ddmFormFieldValidation.setDDMFormFieldValidationExpression(
				new DDMFormFieldValidationExpression() {
					{
						setValue(jsonObject.getString("expression"));
					}
				});
		}

		ddmFormFieldValidation.setParameterLocalizedValue(
			deserializeLocalizedValue(jsonObject.getString("parameter")));

		return ddmFormFieldValidation;
	}

	protected static LocalizedValue deserializeLocalizedValue(String value)
		throws PortalException {

		LocalizedValue localizedValue = new LocalizedValue();

		if (Validator.isNull(value)) {
			return localizedValue;
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject(value);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String languageId = iterator.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	protected static Set<Locale> getAvailableLocales(JSONArray jsonArray) {
		Set<Locale> availableLocales = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			Locale availableLocale = LocaleUtil.fromLanguageId(
				jsonArray.getString(i));

			availableLocales.add(availableLocale);
		}

		return availableLocales;
	}

	protected static DDMFormField getDDMFormField(JSONObject jsonObject)
		throws PortalException {

		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		setDDMFormFieldProperties(jsonObject, ddmFormField);

		setNestedDDMFormField(
			jsonObject.getJSONArray("nestedFields"), ddmFormField);

		return ddmFormField;
	}

	protected static DDMFormFieldOptions getDDMFormFieldOptions(
		JSONArray jsonArray) {

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

	protected static List<DDMFormField> getDDMFormFields(JSONArray jsonArray)
		throws PortalException {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField ddmFormField = getDDMFormField(
				jsonArray.getJSONObject(i));

			ddmFormFields.add(ddmFormField);
		}

		return ddmFormFields;
	}

	protected static DDMForm getDDMFormFieldTypeSettingsDDMForm(String type) {
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

	protected static void setDDMFormAvailableLocales(
		JSONArray jsonArray, DDMForm ddmForm) {

		ddmForm.setAvailableLocales(getAvailableLocales(jsonArray));
	}

	protected static void setDDMFormDefaultLocale(
		String defaultLanguageId, DDMForm ddmForm) {

		ddmForm.setDefaultLocale(LocaleUtil.fromLanguageId(defaultLanguageId));
	}

	protected static void setDDMFormFieldLocalizedValueDefaultLocale(
		LocalizedValue localizedValue, Locale defaultLocale) {

		if (localizedValue == null) {
			return;
		}

		localizedValue.setDefaultLocale(defaultLocale);
	}

	protected static void setDDMFormFieldLocalizedValuesDefaultLocale(
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

	protected static void setDDMFormFieldProperties(
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

	protected static void setDDMFormFieldProperty(
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

	protected static void setDDMFormFields(JSONArray jsonArray, DDMForm ddmForm)
		throws PortalException {

		ddmForm.setDDMFormFields(getDDMFormFields(jsonArray));
	}

	protected static void setDDMFormLocalizedValuesDefaultLocale(
		DDMForm ddmForm) {

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			setDDMFormFieldLocalizedValuesDefaultLocale(
				ddmFormField, ddmForm.getDefaultLocale());
		}
	}

	protected static void setDDMFormRules(
		JSONArray jsonArray, DDMForm ddmForm) {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		ddmForm.setDDMFormRules(
			DDMFormRuleJSONDeserializer.deserialize(jsonArray));
	}

	protected static void setDDMFormSuccessPageSettings(
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

	protected static void setNestedDDMFormField(
			JSONArray jsonArray, DDMFormField ddmFormField)
		throws PortalException {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		ddmFormField.setNestedDDMFormFields(getDDMFormFields(jsonArray));
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

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormJSONDeserializer.class);

	private static DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private static JSONFactory _jsonFactory;

}