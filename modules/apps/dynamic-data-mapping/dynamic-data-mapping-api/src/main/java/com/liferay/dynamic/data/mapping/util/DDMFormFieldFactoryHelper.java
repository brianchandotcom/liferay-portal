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

package com.liferay.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.annotations.DDMFormField;
import com.liferay.dynamic.data.mapping.annotations.DDMFormFieldProperty;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFieldFactoryHelper {

	public DDMFormFieldFactoryHelper(
		DDMFormFactoryHelper ddmFormFactoryHelper, Method method) {

		_ddmFormFactoryHelper = ddmFormFactoryHelper;
		_method = method;

		_ddmFormField = method.getAnnotation(DDMFormField.class);
	}

	public com.liferay.dynamic.data.mapping.model.DDMFormField
		createDDMFormField() {

		String name = getDDMFormFieldName();
		String type = getDDMFormFieldType();

		com.liferay.dynamic.data.mapping.model.DDMFormField ddmFormField =
			new com.liferay.dynamic.data.mapping.model.DDMFormField(name, type);

		Map<String, Object> properties = getProperties();

		properties.putAll(getDDMFormFieldProperties());

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			Object value = entry.getValue();

			if (value instanceof String[]) {
				ddmFormField.setProperty(
					entry.getKey(),
					Stream.of(
						(String[])value
					).map(
						this::getValue
					).toArray());
			}
			else {
				ddmFormField.setProperty(entry.getKey(), getValue(value));
			}
		}

		ddmFormField.setDataType(getDDMFormFieldDataType());
		ddmFormField.setDDMFormFieldOptions(getDDMFormFieldOptions());
		ddmFormField.setDDMFormFieldValidation(getDDMFormFieldValidation());
		ddmFormField.setLabel(getDDMFormFieldLabel());
		ddmFormField.setLocalizable(isDDMFormFieldLocalizable());
		ddmFormField.setPredefinedValue(getDDMFormFieldPredefinedValue());
		ddmFormField.setRepeatable(isDDMFormFieldRepeatable());
		ddmFormField.setRequired(isDDMFormFieldRequired());
		ddmFormField.setTip(getDDMFormFieldTip());
		ddmFormField.setVisibilityExpression(
			getDDMFormFieldVisibilityExpression());

		if (Objects.equals(type, "fieldset")) {
			com.liferay.dynamic.data.mapping.model.DDMForm nestedDDMForm =
				_getNestedDDMForm();

			ddmFormField.setNestedDDMFormFields(
				nestedDDMForm.getDDMFormFields());
		}

		return ddmFormField;
	}

	protected LocalizedValue createLocalizedValue(String property) {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		if (Validator.isNull(property)) {
			return localizedValue;
		}

		if (isLocalizableValue(property)) {
			String languageKey = extractLanguageKey(property);

			for (Locale availableLocale : _availableLocales) {
				localizedValue.addString(
					availableLocale,
					getLocalizedValue(availableLocale, languageKey));
			}
		}
		else {
			localizedValue.addString(_defaultLocale, property);
		}

		return localizedValue;
	}

	protected String extractLanguageKey(String value) {
		return StringUtil.extractLast(value, StringPool.PERCENT);
	}

	protected String getDDMFormFieldDataType() {
		if (Validator.isNotNull(_ddmFormField.dataType())) {
			return _ddmFormField.dataType();
		}

		Class<?> returnType = _getReturnType();

		if (returnType.isAnnotationPresent(DDMForm.class) ||
			returnType.isAssignableFrom(void.class)) {

			return StringPool.BLANK;
		}

		if (returnType.isAssignableFrom(boolean.class) ||
			returnType.isAssignableFrom(Boolean.class)) {

			return "boolean";
		}
		else if (returnType.isAssignableFrom(double.class) ||
				 returnType.isAssignableFrom(Double.class)) {

			return "double";
		}
		else if (returnType.isAssignableFrom(float.class) ||
				 returnType.isAssignableFrom(Float.class)) {

			return "float";
		}
		else if (returnType.isAssignableFrom(int.class) ||
				 returnType.isAssignableFrom(Integer.class)) {

			return "integer";
		}
		else if (returnType.isAssignableFrom(long.class) ||
				 returnType.isAssignableFrom(Long.class)) {

			return "long";
		}
		else if (returnType.isAssignableFrom(short.class) ||
				 returnType.isAssignableFrom(Short.class)) {

			return "short";
		}

		return "string";
	}

	protected LocalizedValue getDDMFormFieldLabel() {
		return createLocalizedValue(_ddmFormField.label());
	}

	protected String getDDMFormFieldName() {
		if (Validator.isNotNull(_ddmFormField.name())) {
			return _ddmFormField.name();
		}

		return _method.getName();
	}

	protected DDMFormFieldOptions getDDMFormFieldOptions() {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(_defaultLocale);

		String[] optionLabels = _ddmFormField.optionLabels();
		String[] optionValues = _ddmFormField.optionValues();

		if (ArrayUtil.isEmpty(optionLabels) ||
			ArrayUtil.isEmpty(optionValues)) {

			return ddmFormFieldOptions;
		}

		for (int i = 0; i < optionLabels.length; i++) {
			String optionLabel = optionLabels[i];

			if (isLocalizableValue(optionLabel)) {
				String languageKey = extractLanguageKey(optionLabel);

				ddmFormFieldOptions.addOptionLabel(
					optionValues[i], _defaultLocale,
					getLocalizedValue(_defaultLocale, languageKey));
			}
			else {
				ddmFormFieldOptions.addOptionLabel(
					optionValues[i], _defaultLocale, optionLabel);
			}

			ddmFormFieldOptions.addOptionReference(
				optionValues[i], optionValues[i]);
		}

		return ddmFormFieldOptions;
	}

	protected LocalizedValue getDDMFormFieldPredefinedValue() {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		String predefinedValue = _ddmFormField.predefinedValue();

		String fieldType = getDDMFormFieldType();

		if (Validator.isNotNull(predefinedValue)) {
			if (StringUtil.startsWith(predefinedValue, StringPool.PERCENT)) {
				return createLocalizedValue(predefinedValue);
			}

			localizedValue.addString(_defaultLocale, predefinedValue);
		}
		else if (StringUtil.equals(fieldType, "checkbox")) {
			localizedValue.addString(_defaultLocale, Boolean.FALSE.toString());
		}
		else if (StringUtil.equals(fieldType, "validation") &&
				 StringUtil.equals(getDDMFormFieldDataType(), "date")) {

			localizedValue.addString(
				_defaultLocale,
				JSONUtil.put(
					"errorMessage", JSONFactoryUtil.createJSONObject()
				).put(
					"expression", JSONFactoryUtil.createJSONObject()
				).put(
					"parameter",
					JSONUtil.put(
						LocaleUtil.toLanguageId(_defaultLocale),
						JSONUtil.put(
							"endsOn",
							JSONUtil.put(
								"date", "responseDate"
							).put(
								"quantity", 1
							).put(
								"type", "responseDate"
							).put(
								"unit", "days"
							)
						).put(
							"startsFrom",
							JSONUtil.put(
								"date", "responseDate"
							).put(
								"quantity", 1
							).put(
								"type", "responseDate"
							).put(
								"unit", "days"
							)
						))
				).toString());
		}

		return localizedValue;
	}

	protected Map<String, String[]> getDDMFormFieldProperties() {
		return Stream.of(
			_ddmFormField.ddmFormFieldProperties()
		).collect(
			Collectors.toMap(
				DDMFormFieldProperty::name, DDMFormFieldProperty::value)
		);
	}

	protected LocalizedValue getDDMFormFieldTip() {
		return createLocalizedValue(_ddmFormField.tip());
	}

	protected String getDDMFormFieldType() {
		if (Validator.isNotNull(_ddmFormField.type())) {
			return _ddmFormField.type();
		}

		Class<?> returnType = _getReturnType();

		if (returnType.isAnnotationPresent(DDMForm.class)) {
			return "fieldset";
		}

		if (returnType.isAssignableFrom(boolean.class) ||
			returnType.isAssignableFrom(Boolean.class)) {

			return "checkbox";
		}

		return "text";
	}

	protected DDMFormFieldValidation getDDMFormFieldValidation() {
		if (Validator.isNull(_ddmFormField.validationExpression()) &&
			Validator.isNull(_ddmFormField.validationErrorMessage())) {

			return null;
		}

		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNotNull(_ddmFormField.validationExpression())) {
			ddmFormFieldValidation.setDDMFormFieldValidationExpression(
				new DDMFormFieldValidationExpression() {
					{
						setValue(_ddmFormField.validationExpression());
					}
				});
		}

		if (Validator.isNotNull(_ddmFormField.validationErrorMessage())) {
			String validationErrorMessage =
				_ddmFormField.validationErrorMessage();

			if (isLocalizableValue(validationErrorMessage)) {
				String languageKey = extractLanguageKey(validationErrorMessage);

				validationErrorMessage = getLocalizedValue(
					_defaultLocale, languageKey);
			}

			ddmFormFieldValidation.setErrorMessageLocalizedValue(
				createLocalizedValue(validationErrorMessage));
		}

		return ddmFormFieldValidation;
	}

	protected String getDDMFormFieldVisibilityExpression() {
		if (Validator.isNotNull(_ddmFormField.visibilityExpression())) {
			return _ddmFormField.visibilityExpression();
		}

		return StringUtil.toUpperCase(StringPool.TRUE);
	}

	protected String getLocalizedValue(Locale locale, String value) {
		return LanguageUtil.get(
			_ddmFormFactoryHelper.getResourceBundle(locale), value);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	protected Map<String, Object> getProperties() {
		Map<String, Object> propertiesMap = new HashMap<>();

		for (String property : _ddmFormField.properties()) {
			String[] propertyParts = property.split(StringPool.EQUAL, 2);

			propertiesMap.put(propertyParts[0], propertyParts[1]);
		}

		return propertiesMap;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	protected LocalizedValue getPropertyValue(Object value) {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		if (Validator.isNull(value)) {
			return localizedValue;
		}

		String valueString = (String)value;

		if (isLocalizableValue(valueString)) {
			String languageKey = extractLanguageKey(valueString);

			localizedValue.addString(
				_defaultLocale, getLocalizedValue(_defaultLocale, languageKey));
		}
		else {
			localizedValue.addString(_defaultLocale, valueString);
		}

		return localizedValue;
	}

	protected Object getValue(Object value) {
		if (!isLocalizableValue(value.toString())) {
			return value;
		}

		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		if (Validator.isNull(value)) {
			return localizedValue;
		}

		String valueString = (String)value;

		if (isLocalizableValue(valueString)) {
			String languageKey = extractLanguageKey(valueString);

			localizedValue.addString(
				_defaultLocale, getLocalizedValue(_defaultLocale, languageKey));
		}
		else {
			localizedValue.addString(_defaultLocale, valueString);
		}

		return localizedValue;
	}

	protected boolean isDDMFormFieldLocalizable() {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(LocalizedValue.class)) {
			return true;
		}

		return false;
	}

	protected boolean isDDMFormFieldRepeatable() {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isArray()) {
			return true;
		}

		return false;
	}

	protected boolean isDDMFormFieldRequired() {
		return _ddmFormField.required();
	}

	protected boolean isLocalizableValue(String value) {
		if ((value != null) && !value.isEmpty() &&
			(value.charAt(0) == CharPool.PERCENT)) {

			return true;
		}

		return false;
	}

	protected void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	protected void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	private com.liferay.dynamic.data.mapping.model.DDMForm _getNestedDDMForm() {
		return DDMFormFactory.create(_getReturnType());
	}

	private Class<?> _getReturnType() {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isArray()) {
			returnType = returnType.getComponentType();
		}

		return returnType;
	}

	private Set<Locale> _availableLocales;
	private final DDMFormFactoryHelper _ddmFormFactoryHelper;
	private final DDMFormField _ddmFormField;
	private Locale _defaultLocale;
	private final Method _method;

}