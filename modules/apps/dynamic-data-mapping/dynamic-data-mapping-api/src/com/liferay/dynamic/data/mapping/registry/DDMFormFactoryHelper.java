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

package com.liferay.dynamic.data.mapping.registry;

import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMForm;
import com.liferay.dynamic.data.mapping.registry.annotations.DDMFormField;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Locale;
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

		if (returnType.isAssignableFrom(boolean.class) ||
			returnType.isAssignableFrom(Boolean.class)) {

			return "boolean";
		}

		return "string";
	}

	public LocalizedValue getDDMFormFieldLabel() {
		LocalizedValue localizedValue = new LocalizedValue(_defaultLocale);

		String label = _ddmFormField.label();

		if (Validator.isNull(label)) {
			return localizedValue;
		}

		if (!isLocalizableKey(label)) {
			localizedValue.addString(_defaultLocale, label);

			return localizedValue;
		}

		String key = getKey(label);

		for (Locale locale : _availableLocales) {
			localizedValue.addString(locale, getLocalizedKey(locale, key));
		}

		return localizedValue;
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

			if (!isLocalizableKey(optionLabel)) {
				ddmFormFieldOptions.addOptionLabel(
					optionValues[i], _defaultLocale, optionLabel);

				continue;
			}

			String key = getKey(optionLabels[i]);

			for (Locale locale : _availableLocales) {
				ddmFormFieldOptions.addOptionLabel(
					optionValues[i], locale, getLocalizedKey(locale, key));
			}
		}

		return ddmFormFieldOptions;
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

	public String getDDMFormFieldValidationExpression() {
		if (Validator.isNotNull(_ddmFormField.validationExpression())) {
			return _ddmFormField.validationExpression();
		}

		return StringPool.TRUE;
	}

	public String getDDMFormFieldValidationMessage() {
		if (Validator.isNotNull(_ddmFormField.validationMessage())) {
			return _ddmFormField.validationMessage();
		}

		return StringPool.BLANK;
	}

	public String getDDMFormFieldVisibilityExpression() {
		if (Validator.isNotNull(_ddmFormField.visibilityExpression())) {
			return _ddmFormField.visibilityExpression();
		}

		return StringPool.TRUE;
	}

	public boolean isDDMFormFieldLocalizable(Method method) {
		Class<?> returnType = _method.getReturnType();

		if (returnType.isAssignableFrom(LocalizedValue.class)) {
			return true;
		}

		return false;
	}

	protected String getKey(String value) {
		return StringUtil.extractLast(value, StringPool.PERCENT);
	}

	protected String getLocalizedKey(Locale locale, String key) {
		ResourceBundle resourceBundle = getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, key);
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		try {
			return ResourceBundleUtil.getBundle(
				getResourceBundleBaseName(_clazz), locale,
				_clazz.getClassLoader());
		}
		catch (Exception e) {
			return ResourceBundleUtil.getBundle(
				getResourceBundleBaseName(_clazz), locale,
				PortalClassLoaderUtil.getClassLoader());
		}
	}

	protected String getResourceBundleBaseName(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(DDMForm.class)) {
			return null;
		}

		DDMForm ddmForm = clazz.getAnnotation(DDMForm.class);

		if (Validator.isNotNull(ddmForm.localization())) {
			return ddmForm.localization();
		}

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			if (!interfaceClass.isAnnotationPresent(DDMForm.class)) {
				continue;
			}

			String resourceBundleBaseName = getResourceBundleBaseName(
				interfaceClass);

			if (Validator.isNotNull(resourceBundleBaseName)) {
				return resourceBundleBaseName;
			}
		}

		return null;
	}

	protected boolean isLocalizableDDMForm() {
		if (Validator.isNotNull(getResourceBundleBaseName(_clazz))) {
			return true;
		}

		return false;
	}

	protected boolean isLocalizableKey(String key) {
		if (isLocalizableDDMForm() &&
			StringUtil.startsWith(key, StringPool.PERCENT)) {

			return true;
		}

		return false;
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
			_defaultLocale = LocaleUtil.getDefault();

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