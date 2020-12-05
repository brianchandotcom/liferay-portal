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

package com.liferay.dynamic.data.mapping.internal.io.util;

import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
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
import java.util.Objects;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormFieldDeserializerUtil {

	public static List<DDMFormField> deserialize(
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			JSONArray jsonArray, JSONFactory jsonFactory)
		throws PortalException {

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormField ddmFormField = _getDDMFormField(
				ddmFormFieldTypeServicesTracker, jsonFactory,
				jsonArray.getJSONObject(i));

			ddmFormFields.add(ddmFormField);
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

			return _deserializeDDMFormFieldValidation(
				jsonFactory, serializedDDMFormFieldProperty);
		}

		return serializedDDMFormFieldProperty;
	}

	private static DDMFormFieldValidation _deserializeDDMFormFieldValidation(
			JSONFactory jsonFactory, String serializedDDMFormFieldProperty)
		throws PortalException {

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNull(serializedDDMFormFieldProperty)) {
			return ddmFormFieldValidation;
		}

		JSONObject jsonObject = jsonFactory.createJSONObject(
			serializedDDMFormFieldProperty);

		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			_deserializeLocalizedValue(
				jsonFactory, jsonObject.getString("errorMessage")));

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
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			JSONFactory jsonFactory, JSONObject jsonObject)
		throws PortalException {

		String name = jsonObject.getString("name");
		String type = jsonObject.getString("type");

		DDMFormField ddmFormField = new DDMFormField(name, type);

		_setDDMFormFieldProperties(
			ddmFormField, ddmFormFieldTypeServicesTracker, jsonFactory,
			jsonObject);

		_setNestedDDMFormField(
			ddmFormField, ddmFormFieldTypeServicesTracker,
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
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String type) {

		DDMFormFieldType ddmFormFieldType =
			ddmFormFieldTypeServicesTracker.getDDMFormFieldType(type);

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
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			JSONFactory jsonFactory, JSONObject jsonObject)
		throws PortalException {

		DDMForm ddmFormFieldTypeSettingsDDMForm =
			_getDDMFormFieldTypeSettingsDDMForm(
				ddmFormFieldTypeServicesTracker, ddmFormField.getType());

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

		if (jsonObject.has(settingName)) {
			Object deserializedDDMFormFieldProperty =
				_deserializeDDMFormFieldProperty(
					ddmFormFieldTypeSetting, jsonFactory,
					jsonObject.getString(settingName));

			ddmFormField.setProperty(
				settingName, deserializedDDMFormFieldProperty);
		}
	}

	private static void _setNestedDDMFormField(
			DDMFormField ddmFormField,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			JSONArray jsonArray, JSONFactory jsonFactory)
		throws PortalException {

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			return;
		}

		ddmFormField.setNestedDDMFormFields(
			deserialize(
				ddmFormFieldTypeServicesTracker, jsonArray, jsonFactory));
	}

}