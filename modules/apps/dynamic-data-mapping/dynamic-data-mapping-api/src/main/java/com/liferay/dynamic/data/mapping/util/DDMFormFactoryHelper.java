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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactoryHelper {

	public DDMFormFactoryHelper(Class<?> clazz, Method method) {
		_clazz = clazz;
		_method = method;

		_ddmForm = clazz.getAnnotation(DDMForm.class);
		_ddmFormField = method.getAnnotation(DDMFormField.class);

		setAvailableLocales();
		setDefaultLocale();
	}

	public String getDDMFormFieldDataType() {
		if (Validator.isNotNull(_ddmFormField.dataType())) {
			return _ddmFormField.dataType();
		}

		Class<?> returnType = _method.getReturnType();

		if (returnType.isArray()) {
			returnType = returnType.getComponentType();
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
		else {
			return "string";
		}
	}

	public LocalizedValue getDDMFormFieldLabel() {
		return createLocalizedValue(_ddmFormField.label());
	}

	public String getDDMFormFieldName() {
		if (Validator.isNotNull(_ddmFormField.name())) {
			return _ddmFormField.name();
		}

		return _method.getName();
	}

	public DDMFormFieldOptions getDDMFormFieldOptions() {
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
		}

		return ddmFormFieldOptions;
	}

	public LocalizedValue getDDMFormFieldPredefinedValue() {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		String predefinedValue = _ddmFormField.predefinedValue();

		String fieldType = getDDMFormFieldType();

		if (Validator.isNotNull(predefinedValue)) {
			localizedValue.addString(_defaultLocale, predefinedValue);
		}
		else if (fieldType.equals("checkbox")) {
			localizedValue.addString(_defaultLocale, Boolean.FALSE.toString());
		}

		return localizedValue;
	}

	public LocalizedValue getDDMFormFieldTip() {
		return createLocalizedValue(_ddmFormField.tip());
	}

	public String getDDMFormFieldType() {
		if (Validator.isNotNull(_ddmFormField.type())) {
			return _ddmFormField.type();
		}

		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(boolean.class) ||
			returnType.isAssignableFrom(Boolean.class)) {

			return "checkbox";
		}

		return "text";
	}

	public DDMFormFieldValidation getDDMFormFieldValidation() {
		DDMFormFieldValidation ddmFormFieldValidation =
			new DDMFormFieldValidation();

		if (Validator.isNotNull(_ddmFormField.validationExpression())) {
			ddmFormFieldValidation.setExpression(
				_ddmFormField.validationExpression());
		}

		if (Validator.isNotNull(_ddmFormField.validationErrorMessage())) {
			ddmFormFieldValidation.setErrorMessage(
				_ddmFormField.validationErrorMessage());
		}

		return ddmFormFieldValidation;
	}

	public String getDDMFormFieldVisibilityExpression() {
		if (Validator.isNotNull(_ddmFormField.visibilityExpression())) {
			return _ddmFormField.visibilityExpression();
		}

		return StringPool.TRUE;
	}

	public Map<String, Object> getProperties() {
		Map<String, Object> propertiesMap = new HashMap<>();

		for (String property : _ddmFormField.properties()) {
			String key = StringUtil.extractFirst(property, StringPool.EQUAL);
			String value = StringUtil.extractLast(property, StringPool.EQUAL);

			propertiesMap.put(key, value);
		}

		return propertiesMap;
	}

	public LocalizedValue getPropertyValue(Object value) {
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

	public boolean isDDMFormFieldLocalizable(Method method) {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(LocalizedValue.class)) {
			return true;
		}

		return false;
	}

	public boolean isDDMFormFieldRepeatable() {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isArray()) {
			return true;
		}

		return false;
	}

	public boolean isDDMFormFieldRequired() {
		return _ddmFormField.required();
	}

	public boolean isLocalizableValue(String value) {
		if (StringUtil.startsWith(value, StringPool.PERCENT)) {
			return true;
		}

		return false;
	}

	protected LocalizedValue createLocalizedValue(String property) {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		if (Validator.isNull(property)) {
			return localizedValue;
		}

		if (isLocalizableValue(property)) {
			String languageKey = extractLanguageKey(property);

			localizedValue.addString(
				_defaultLocale, getLocalizedValue(_defaultLocale, languageKey));
		}
		else {
			localizedValue.addString(_defaultLocale, property);
		}

		return localizedValue;
	}

	protected String extractLanguageKey(String value) {
		return StringUtil.extractLast(value, StringPool.PERCENT);
	}

	protected String getLocalizedValue(Locale locale, String value) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, value);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle portalResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, PortalClassLoaderUtil.getClassLoader());

		List<ResourceBundle> resourceBundles = new ArrayList<>();
		resourceBundles.add(portalResourceBundle);

		mountResourceBundleHierarchy(_clazz, resourceBundles, locale);

		ResourceBundle[] resourceBundleArray = resourceBundles.toArray(
			new ResourceBundle[resourceBundles.size()]);

		return new AggregateResourceBundle(resourceBundleArray);
	}

	protected String getResourceBundleBaseName(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(DDMForm.class)) {
			return null;
		}

		DDMForm ddmForm = clazz.getAnnotation(DDMForm.class);

		if (Validator.isNotNull(ddmForm.localization())) {
			return ddmForm.localization();
		}

		return "content.Language";
	}

	protected void mountResourceBundleHierarchy(
		Class<?> clazz, List<ResourceBundle> resourceBundles, Locale locale) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			mountResourceBundleHierarchy(
				interfaceClass, resourceBundles, locale);
		}

		String baseName = getResourceBundleBaseName(clazz);

		if (Validator.isNull(baseName)) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			baseName, locale, clazz.getClassLoader());

		if (resourceBundle != null) {
			resourceBundles.add(resourceBundle);
		}
	}

	protected void setAvailableLocales() {
		if (Validator.isNull(_ddmForm.availableLanguageIds())) {
			_availableLocales = LanguageUtil.getAvailableLocales();

			return;
		}

		_availableLocales = new TreeSet<>();

		for (String availableLanguageId :
				StringUtil.split(_ddmForm.availableLanguageIds())) {

			_availableLocales.add(
				LocaleUtil.fromLanguageId(availableLanguageId));
		}
	}

	protected void setDefaultLocale() {
		if (Validator.isNull(_ddmForm.defaultLanguageId())) {
			_defaultLocale = LocaleThreadLocal.getThemeDisplayLocale();

			if (_defaultLocale == null) {
				_defaultLocale = LocaleUtil.getDefault();
			}

			return;
		}

		_defaultLocale = LocaleUtil.fromLanguageId(
			_ddmForm.defaultLanguageId());
	}

	private Set<Locale> _availableLocales;
	private final Class<?> _clazz;
	private final DDMForm _ddmForm;
	private final DDMFormField _ddmFormField;
	private Locale _defaultLocale;
	private final Method _method;

}