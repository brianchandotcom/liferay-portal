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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Gabriel Albuquerque
 */
public class DDMFormFieldSerializerUtil {

	public static void serialize(
		List<DDMFormField> ddmFormFields,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		JSONFactory jsonFactory, JSONObject jsonObject) {

		jsonObject.put(
			"fields",
			_fieldsToJSONArray(
				ddmFormFields, ddmFormFieldTypeServicesRegistry, jsonFactory));
	}

	private static void _addNestedFields(
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		JSONFactory jsonFactory, JSONObject jsonObject,
		List<DDMFormField> nestedDDMFormFields) {

		if (nestedDDMFormFields.isEmpty()) {
			return;
		}

		jsonObject.put(
			"nestedFields",
			_fieldsToJSONArray(
				nestedDDMFormFields, ddmFormFieldTypeServicesRegistry,
				jsonFactory));
	}

	private static void _addProperties(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		JSONFactory jsonFactory, JSONObject jsonObject) {

		DDMForm ddmFormFieldTypeSettingsDDMForm =
			_getDDMFormFieldTypeSettingsDDMForm(
				ddmFormFieldTypeServicesRegistry, ddmFormField.getType());

		for (DDMFormField ddmFormFieldTypeSetting :
				ddmFormFieldTypeSettingsDDMForm.getDDMFormFields()) {

			_addProperty(
				ddmFormField, ddmFormFieldTypeSetting, jsonFactory, jsonObject);
		}
	}

	private static void _addProperty(
		DDMFormField ddmFormField, DDMFormField ddmFormFieldTypeSetting,
		JSONFactory jsonFactory, JSONObject jsonObject) {

		Object property = ddmFormField.getProperty(
			ddmFormFieldTypeSetting.getName());

		if (property == null) {
			return;
		}

		_addProperty(
			jsonObject, ddmFormFieldTypeSetting.getName(),
			_serializeDDMFormFieldProperty(
				ddmFormFieldTypeSetting, jsonFactory, property));
	}

	private static void _addProperty(
		JSONObject jsonObject, String propertyName, Object propertyValue) {

		if (propertyValue == null) {
			return;
		}

		jsonObject.put(propertyName, propertyValue);
	}

	private static JSONArray _fieldsToJSONArray(
		List<DDMFormField> ddmFormFields,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		JSONFactory jsonFactory) {

		_trim(ddmFormFields);

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (DDMFormField ddmFormField : ddmFormFields) {
			jsonArray.put(
				_toJSONObject(
					ddmFormField, ddmFormFieldTypeServicesRegistry,
					jsonFactory));
		}

		return jsonArray;
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

	private static boolean _isArray(Object value) {
		if (value == null) {
			return false;
		}

		Class<?> clazz = value.getClass();

		return clazz.isArray();
	}

	private static Object _serializeDDMFormFieldProperty(
		DDMFormField ddmFormFieldTypeSetting, JSONFactory jsonFactory,
		Object property) {

		if (ddmFormFieldTypeSetting.isLocalizable()) {
			return _toJSONObject(jsonFactory, (LocalizedValue)property);
		}

		String dataType = ddmFormFieldTypeSetting.getDataType();

		if (Objects.equals(dataType, "boolean")) {
			return GetterUtil.getBoolean(property);
		}
		else if (Objects.equals(dataType, "ddm-options")) {
			return _toJSONArray((DDMFormFieldOptions)property, jsonFactory);
		}
		else if (Objects.equals(
					ddmFormFieldTypeSetting.getType(), "validation")) {

			return _toJSONObject((DDMFormFieldValidation)property, jsonFactory);
		}
		else if (_isArray(property)) {
			return jsonFactory.createJSONArray((Object[])property);
		}

		if (property instanceof Collection) {
			return jsonFactory.createJSONArray((Collection<?>)property);
		}
		else if (property instanceof Map) {
			return jsonFactory.createJSONObject((Map<?, ?>)property);
		}

		return String.valueOf(property);
	}

	private static JSONArray _toJSONArray(
		DDMFormFieldOptions ddmFormFieldOptions, JSONFactory jsonFactory) {

		Set<String> optionsValues = ddmFormFieldOptions.getOptionsValues();

		if (optionsValues.isEmpty()) {
			return null;
		}

		JSONArray jsonArray = jsonFactory.createJSONArray();

		for (String optionValue : optionsValues) {
			JSONObject jsonObject = jsonFactory.createJSONObject();

			jsonObject.put(
				"label",
				_toJSONObject(
					jsonFactory,
					ddmFormFieldOptions.getOptionLabels(optionValue))
			).put(
				"reference", ddmFormFieldOptions.getOptionReference(optionValue)
			).put(
				"value", optionValue
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	private static JSONObject _toJSONObject(
		DDMFormField ddmFormField,
		DDMFormFieldTypeServicesRegistry ddmFormFieldTypeServicesRegistry,
		JSONFactory jsonFactory) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		_addProperties(
			ddmFormField, ddmFormFieldTypeServicesRegistry, jsonFactory,
			jsonObject);

		_addNestedFields(
			ddmFormFieldTypeServicesRegistry, jsonFactory, jsonObject,
			ddmFormField.getNestedDDMFormFields());

		return jsonObject;
	}

	private static JSONObject _toJSONObject(
		DDMFormFieldValidation ddmFormFieldValidation,
		JSONFactory jsonFactory) {

		if (ddmFormFieldValidation == null) {
			return null;
		}

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put(
			"errorMessage",
			_toJSONObject(
				jsonFactory,
				ddmFormFieldValidation.getErrorMessageLocalizedValue())
		).put(
			"expression",
			_toJSONObject(
				ddmFormFieldValidation.getDDMFormFieldValidationExpression(),
				jsonFactory)
		).put(
			"parameter",
			_toJSONObject(
				jsonFactory,
				ddmFormFieldValidation.getParameterLocalizedValue())
		);

		return jsonObject;
	}

	private static JSONObject _toJSONObject(
		DDMFormFieldValidationExpression ddmFormFieldValidationExpression,
		JSONFactory jsonFactory) {

		JSONObject jsonObject = jsonFactory.createJSONObject();

		jsonObject.put(
			"name", ddmFormFieldValidationExpression.getName()
		).put(
			"value", ddmFormFieldValidationExpression.getValue()
		);

		return jsonObject;
	}

	private static JSONObject _toJSONObject(
		JSONFactory jsonFactory, LocalizedValue localizedValue) {

		if (localizedValue == null) {
			return jsonFactory.createJSONObject();
		}

		JSONObject jsonObject = jsonFactory.createJSONObject();

		Map<Locale, String> values = localizedValue.getValues();

		if (values.isEmpty()) {
			return jsonObject;
		}

		for (Locale availableLocale : localizedValue.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(availableLocale),
				localizedValue.getString(availableLocale));
		}

		return jsonObject;
	}

	private static void _trim(List<DDMFormField> ddmFormFields) {
		if (ddmFormFields.isEmpty()) {
			return;
		}

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue localizedValue = _trim(
				ddmFormField.getPredefinedValue());

			ddmFormField.setPredefinedValue(localizedValue);

			localizedValue = _trim(ddmFormField.getStyle());

			ddmFormField.setStyle(localizedValue);

			localizedValue = _trim(ddmFormField.getTip());

			ddmFormField.setTip(localizedValue);
		}
	}

	private static LocalizedValue _trim(LocalizedValue rawLocalizedValue) {
		if (rawLocalizedValue == null) {
			return null;
		}

		LocalizedValue localizedValue = new LocalizedValue();

		Map<Locale, String> predefinedValuesMap = rawLocalizedValue.getValues();

		for (Map.Entry<Locale, String> entry : predefinedValuesMap.entrySet()) {
			String value = entry.getValue();

			if (value != null) {
				value = StringUtil.trim(value);

				if (value.length() == 0) {
					localizedValue.addString(entry.getKey(), StringPool.BLANK);
				}
				else {
					localizedValue.addString(entry.getKey(), entry.getValue());
				}
			}
			else {
				localizedValue.addString(entry.getKey(), entry.getValue());
			}
		}

		return localizedValue;
	}

}