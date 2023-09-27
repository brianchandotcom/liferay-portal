/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.io.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.form.field.type.DefaultDDMFormFieldTypeSettings;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormFieldDeserializerUtil {

	public static List<DDMFormField> deserialize(
			DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
			JSONArray jsonArray, JSONFactory jsonFactory)
		throws PortalException {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			ddmFormFields.add(
				_getDDMFormField(
					ddmFormFieldTypeServicesRegistry, jsonFactory,
					jsonArray.getJSONObject(i)));
		}

		return ddmFormFields;
	}

	private static void _addOptionValueLabels(
		DDMFormFieldOptions ddmFormFieldOptions, JSONObject jsonObject,
		String optionValue) {

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String languageId = iterator.next();

			ddmFormFieldOptions.addOptionLabel(
				optionValue, LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}
	}

	private static DDMFormFieldOptions _deserializeDDMFormFieldOptions(
			JSONFactory jsonFactory, String serializedDDMFormFieldProperty)
		throws PortalException {

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return new DDMFormFieldOptions();
		}

		JSONArray jsonArray = jsonFactory.createJSONArray(
			serializedDDMFormFieldProperty);

		return _getDDMFormFieldOptions(jsonArray);
	}

	private static Object _deserializeDDMFormFieldProperty(
			DDMFormField ddmFormFieldTypeSetting, JSONFactory jsonFactory,
			String serializedDDMFormFieldProperty)
		throws PortalException {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return _deserializeLocalizedValue(
				jsonFactory, serializedDDMFormFieldProperty);
		}

		String dataType = ddmFormFieldTypeSetting.getDataType();

		if (Objects.equals(dataType, "boolean")) {
			return Boolean.valueOf(serializedDDMFormFieldProperty);
		}
		else if (Objects.equals(dataType, "ddm-options")) {
			return _deserializeDDMFormFieldOptions(
				jsonFactory, serializedDDMFormFieldProperty);
		}
		else if (Objects.equals(
					ddmFormFieldTypeSetting.getType(), "validation")) {

			DDMForm ddmForm = ddmFormFieldTypeSetting.getDDMForm();

			return _deserializeDDMFormFieldValidation(
				ddmForm.getAvailableLocales(), jsonFactory,
				serializedDDMFormFieldProperty);
		}

		return serializedDDMFormFieldProperty;
	}

	private static DDMFormFieldValidation _deserializeDDMFormFieldValidation(
			Set<Locale> availableLocales, JSONFactory jsonFactory,
			String serializedDDMFormFieldProperty)
		throws PortalException {

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return ddmFormFieldValidation;
		}

		JSONObject jsonObject = jsonFactory.createJSONObject(
			serializedDDMFormFieldProperty);

		DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
			new DDMFormFieldValidationExpression();

		JSONObject expressionJSONObject = jsonObject.getJSONObject(
			"expression");

		if (expressionJSONObject != null) {
			ddmFormFieldValidationExpression.setName(
				expressionJSONObject.getString("name"));
			ddmFormFieldValidationExpression.setValue(
				expressionJSONObject.getString("value"));
		}
		else {
			ddmFormFieldValidationExpression.setValue(
				jsonObject.getString("expression"));
		}

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			ddmFormFieldValidationExpression);

		JSONObject errorMessageJSONObject = jsonObject.getJSONObject(
			"errorMessage");

		if (errorMessageJSONObject == null) {
			LocalizedValue errorMessageLocalizedValue = new LocalizedValue();

			for (Locale locale : availableLocales) {
				errorMessageLocalizedValue.addString(
					locale, jsonObject.getString("errorMessage"));
			}

			ddmFormFieldValidation.setErrorMessageLocalizedValue(
				errorMessageLocalizedValue);
		}
		else {
			ddmFormFieldValidation.setErrorMessageLocalizedValue(
				_deserializeLocalizedValue(
					jsonFactory, jsonObject.getString("errorMessage")));
		}

		ddmFormFieldValidation.setParameterLocalizedValue(
			_deserializeLocalizedValue(
				jsonFactory, jsonObject.getString("parameter")));

		return ddmFormFieldValidation;
	}

	private static LocalizedValue _deserializeLocalizedValue(
			JSONFactory jsonFactory, String value)
		throws PortalException {

		LocalizedValue localizedValue = new LocalizedValue();

		if (Validator.isNull(value)) {
			return localizedValue;
		}

		JSONObject jsonObject = jsonFactory.createJSONObject(value);

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String languageId = iterator.next();

			localizedValue.addString(
				LocaleUtil.fromLanguageId(languageId),
				jsonObject.getString(languageId));
		}

		return localizedValue;
	}

	private static DDMFormField _getDDMFormField(
			DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
			JSONFactory jsonFactory, JSONObject jsonObject)
		throws PortalException {

		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		_setDDMFormFieldProperties(
			ddmFormField, ddmFormFieldTypeServicesRegistry, jsonFactory,
			jsonObject);

		_setNestedDDMFormField(
			ddmFormField, ddmFormFieldTypeServicesRegistry,
			jsonObject.getJSONArray("nestedFields"), jsonFactory);

		return ddmFormField;
	}

	private static DDMFormFieldOptions _getDDMFormFieldOptions(
		JSONArray jsonArray) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			String value = jsonObject.getString("value");

			ddmFormFieldOptions.addOption(value);
			ddmFormFieldOptions.addOptionReference(
				value, jsonObject.getString("reference"));

			_addOptionValueLabels(
				ddmFormFieldOptions, jsonObject.getJSONObject("label"), value);
		}

		return ddmFormFieldOptions;
	}

	private static DDMForm _getDDMFormFieldTypeSettingsDDMForm(
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		String type) {

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesRegistry.getDDMFormFieldType(type);

		Class<? extends DDMFormFieldTypeSettings> ddmFormFieldTypeSettings =
			DefaultDDMFormFieldTypeSettings.class;

		if (ddmFormFieldType != null) {
			ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();
		}

		return DDMFormFactory.create(ddmFormFieldTypeSettings);
	}

	private static void _setDDMFormFieldProperties(
			DDMFormField ddmFormField,
			DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
			JSONFactory jsonFactory, JSONObject jsonObject)
		throws PortalException {

		DDMForm ddmFormFieldTypeSettingsDDMForm =
			_getDDMFormFieldTypeSettingsDDMForm(
				ddmFormFieldTypeServicesRegistry, ddmFormField.getType());

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			_setDDMFormFieldProperty(
				ddmFormField, ddmFormFieldTypeSetting, jsonFactory, jsonObject);
		}
	}

	private static void _setDDMFormFieldProperty(
			DDMFormField ddmFormField, DDMFormField ddmFormFieldTypeSetting,
			JSONFactory jsonFactory, JSONObject jsonObject)
		throws PortalException {

		String settingName = ddmFormFieldTypeSetting.getName();

		if (!jsonObject.has(settingName)) {
			return;
		}

		String settingValue = jsonObject.getString(settingName);

		if (Objects.equals(settingName, "fieldReference") &&
			Validator.isNull(settingValue)) {

			return;
		}

		ddmFormField.setProperty(
			settingName,
			_deserializeDDMFormFieldProperty(
				ddmFormFieldTypeSetting, jsonFactory, settingValue));
	}

	private static void _setNestedDDMFormField(
			DDMFormField ddmFormField,
			DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
			JSONArray jsonArray, JSONFactory jsonFactory)
		throws PortalException {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		ddmFormField.setNestedDDMFormFields(
			deserialize(
				ddmFormFieldTypeServicesRegistry, jsonArray, jsonFactory));
	}

}