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

package com.liferay.data.engine.rest.dto.v2_0.util;

import com.liferay.data.engine.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinitionField;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.util.SettingsDDMFormFieldsUtil;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcos Martins
 */
public class DataDefinitionDDMFormUtil {

	public static DDMForm toDDMForm(
		DataDefinition dataDefinition,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker) {

		if (dataDefinition == null) {
			return new DDMForm();
		}

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(
			_toLocales(dataDefinition.getAvailableLanguageIds()));
		ddmForm.setDDMFormFields(
			_toDDMFormFields(
				dataDefinition.getDataDefinitionFields(),
				ddmFormFieldTypeServicesTracker,
				dataDefinition.getDefaultLanguageId()));
		ddmForm.setDefaultLocale(
			LocaleUtil.fromLanguageId(dataDefinition.getDefaultLanguageId()));

		return ddmForm;
	}

	private static DDMFormFieldOptions _getDDMFormFieldOptions(
		Locale locale, Map<String, ?> options) {

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		if (MapUtil.isEmpty(options)) {
			return ddmFormFieldOptions;
		}

		for (Map.Entry<String, ?> entry : options.entrySet()) {
			Object value = entry.getValue();

			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				Object[] values = (Object[])value;

				for (Object curValue : values) {
					try {
						JSONObject jsonObject =
							JSONFactoryUtil.createJSONObject(
								curValue.toString());

						ddmFormFieldOptions.addOptionLabel(
							jsonObject.getString("value"),
							LocaleUtil.fromLanguageId(entry.getKey()),
							jsonObject.getString("label"));

						ddmFormFieldOptions.addOptionReference(
							jsonObject.getString("value"),
							jsonObject.getString("reference"));
					}
					catch (JSONException jsonException) {
						if (_log.isDebugEnabled()) {
							_log.debug(jsonException, jsonException);
						}
					}
				}
			}
			else if (value instanceof List) {
				for (Object option : (List<Object>)value) {
					if (option instanceof Map) {
						ddmFormFieldOptions.addOptionLabel(
							MapUtil.getString((Map<String, ?>)option, "value"),
							LocaleUtil.fromLanguageId(entry.getKey()),
							MapUtil.getString((Map<String, ?>)option, "label"));

						ddmFormFieldOptions.addOptionReference(
							MapUtil.getString((Map<String, ?>)option, "value"),
							MapUtil.getString(
								(Map<String, ?>)option, "reference"));
					}
					else if (option instanceof String) {
						try {
							JSONObject optionJSONObject =
								JSONFactoryUtil.createJSONObject(
									option.toString());

							ddmFormFieldOptions.addOptionLabel(
								JSONUtil.getValueAsString(
									optionJSONObject, "Object/value"),
								LocaleUtil.fromLanguageId(entry.getKey()),
								JSONUtil.getValueAsString(
									optionJSONObject, "Object/label"));

							ddmFormFieldOptions.addOptionReference(
								JSONUtil.getValueAsString(
									optionJSONObject, "Object/value"),
								JSONUtil.getValueAsString(
									optionJSONObject, "Object/reference"));
						}
						catch (JSONException jsonException) {
							if (_log.isDebugEnabled()) {
								_log.debug(jsonException, jsonException);
							}
						}
					}
				}
			}
		}

		ddmFormFieldOptions.setDefaultLocale(locale);

		return ddmFormFieldOptions;
	}

	private static DDMFormFieldValidation _getDDMFormFieldValidation(
		Map<String, Object> value) {

		if (MapUtil.isEmpty(value)) {
			return null;
		}

		Map<String, String> expression = (Map<String, String>)value.get(
			"expression");

		if (Validator.isNull(MapUtil.getString(expression, "value"))) {
			return null;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		ddmFormFieldValidation.setDDMFormFieldValidationExpression(
			new DDMFormFieldValidationExpression() {
				{
					setName(MapUtil.getString(expression, "name"));
					setValue(MapUtil.getString(expression, "value"));
				}
			});
		ddmFormFieldValidation.setErrorMessageLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("errorMessage")));
		ddmFormFieldValidation.setParameterLocalizedValue(
			LocalizedValueUtil.toLocalizedValue(
				(Map<String, Object>)value.get("parameter")));

		return ddmFormFieldValidation;
	}

	private static DDMFormField _toDDMFormField(
		DataDefinitionField dataDefinitionField,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String languageId) {

		DDMFormField ddmFormField = new DDMFormField();

		ddmFormField.setIndexType(dataDefinitionField.getIndexTypeAsString());
		ddmFormField.setLabel(
			LocalizedValueUtil.toLocalizedValue(
				dataDefinitionField.getLabel(),
				LocaleUtil.fromLanguageId(languageId)));
		ddmFormField.setLocalizable(
			GetterUtil.getBoolean(dataDefinitionField.getLocalizable()));
		ddmFormField.setName(dataDefinitionField.getName());
		ddmFormField.setNestedDDMFormFields(
			_toDDMFormFields(
				dataDefinitionField.getNestedDataDefinitionFields(),
				ddmFormFieldTypeServicesTracker, languageId));

		Map<String, Object> defaultValue =
			dataDefinitionField.getDefaultValue();

		if (defaultValue != null) {
			defaultValue.forEach(
				(key, value) -> {
					if (value instanceof ArrayList) {
						value = String.valueOf(
							JSONFactoryUtil.createJSONArray((ArrayList)value));
					}

					defaultValue.put(key, value);
				});
		}

		ddmFormField.setPredefinedValue(
			LocalizedValueUtil.toLocalizedValue(
				defaultValue, LocaleUtil.fromLanguageId(languageId)));

		ddmFormField.setReadOnly(
			GetterUtil.getBoolean(dataDefinitionField.getReadOnly()));
		ddmFormField.setRepeatable(
			GetterUtil.getBoolean(dataDefinitionField.getRepeatable()));
		ddmFormField.setRequired(
			GetterUtil.getBoolean(dataDefinitionField.getRequired()));
		ddmFormField.setShowLabel(
			GetterUtil.getBoolean(dataDefinitionField.getShowLabel()));
		ddmFormField.setTip(
			LocalizedValueUtil.toLocalizedValue(
				dataDefinitionField.getTip(),
				LocaleUtil.fromLanguageId(languageId)));
		ddmFormField.setType(dataDefinitionField.getFieldType());

		Map<String, Object> customProperties =
			dataDefinitionField.getCustomProperties();

		if (MapUtil.isNotEmpty(customProperties)) {
			Map<String, DDMFormField> settingsDDMFormFieldsMap =
				SettingsDDMFormFieldsUtil.getSettingsDDMFormFields(
					ddmFormFieldTypeServicesTracker,
					dataDefinitionField.getFieldType());

			for (Map.Entry<String, Object> entry :
					customProperties.entrySet()) {

				if (ArrayUtil.contains(
						_PREDEFINED_PROPERTIES, entry.getKey())) {

					continue;
				}

				DDMFormField settingsDDMFormField =
					settingsDDMFormFieldsMap.get(entry.getKey());

				if (settingsDDMFormField == null) {
					continue;
				}

				if (settingsDDMFormField.isLocalizable()) {
					ddmFormField.setProperty(
						entry.getKey(),
						LocalizedValueUtil.toLocalizedValue(
							(Map<String, Object>)entry.getValue(),
							LocaleUtil.fromLanguageId(languageId)));
				}
				else if (Objects.equals(
							settingsDDMFormField.getDataType(), "boolean")) {

					ddmFormField.setProperty(
						entry.getKey(),
						GetterUtil.getBoolean(entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getDataType(),
							"ddm-options")) {

					ddmFormField.setProperty(
						entry.getKey(),
						_getDDMFormFieldOptions(
							LocaleUtil.fromLanguageId(languageId),
							(Map<String, ?>)entry.getValue()));
				}
				else if (Objects.equals(
							settingsDDMFormField.getType(), "validation")) {

					ddmFormField.setProperty(
						entry.getKey(),
						_getDDMFormFieldValidation(
							(Map<String, Object>)entry.getValue()));
				}
				else {
					ddmFormField.setProperty(entry.getKey(), entry.getValue());
				}
			}
		}

		return ddmFormField;
	}

	private static List<DDMFormField> _toDDMFormFields(
		DataDefinitionField[] dataDefinitionFields,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		String languageId) {

		if (ArrayUtil.isEmpty(dataDefinitionFields)) {
			return Collections.emptyList();
		}

		return Stream.of(
			dataDefinitionFields
		).map(
			dataDefinitionField -> _toDDMFormField(
				dataDefinitionField, ddmFormFieldTypeServicesTracker,
				languageId)
		).collect(
			Collectors.toList()
		);
	}

	private static Set<Locale> _toLocales(String[] languageIds) {
		if (ArrayUtil.isEmpty(languageIds)) {
			return Collections.emptySet();
		}

		return Stream.of(
			languageIds
		).map(
			LocaleUtil::fromLanguageId
		).collect(
			Collectors.toSet()
		);
	}

	private static final String[] _PREDEFINED_PROPERTIES = {
		"indexType", "label", "localizable", "name", "predefinedValue",
		"readOnly", "repeatable", "required", "showLabel", "tip", "type"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DataDefinitionDDMFormUtil.class);

}