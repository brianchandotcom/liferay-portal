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

package com.liferay.portlet.dynamicdatamapping.registry;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.language.UTF8Control;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.registry.annotations.DDMForm;
import com.liferay.portlet.dynamicdatamapping.registry.annotations.DDMFormField;

import java.lang.reflect.Method;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactoryHelper {

	public DDMFormFactoryHelper(Class<?> clazz, Method method) {
		_clazz = clazz;
		_method = method;

		_ddmFormField = method.getAnnotation(DDMFormField.class);
	}

	public LocalizedValue getDDMFormFieldLabel() {
		LocalizedValue localizedLabel = new LocalizedValue();

		Set<Locale> availableLocales = LanguageUtil.getAvailableLocales();

		for (Locale currentLocale : availableLocales) {
			localizedLabel.addString(
				currentLocale,
				getLocalizedLabel(currentLocale, _ddmFormField.label()));
		}

		return localizedLabel;
	}

	public String getDDMFormFieldName() {
		if (Validator.isNotNull(_ddmFormField.name())) {
			return _ddmFormField.name();
		}

		return _method.getName();
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

	protected String getDDMFormFieldDataType() {
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

	protected String getLocalizedLabel(Locale locale, String label) {
		DDMForm ddmForm = _clazz.getAnnotation(DDMForm.class);

		ResourceBundle resourceBundle = getResourceBundle(ddmForm, locale);

		return LanguageUtil.get(
			resourceBundle, StringUtil.extractLast(label, StringPool.PERCENT));
	}

	protected ResourceBundle getResourceBundle(DDMForm ddmForm, Locale locale) {
		ResourceBundle resourceBundle;

		try {
			resourceBundle = ResourceBundle.getBundle(
				ddmForm.localization(), locale, _clazz.getClassLoader(),
				UTF8Control.INSTANCE);
		}
		catch (Exception e) {
			resourceBundle = ResourceBundle.getBundle(
				ddmForm.localization(), locale,
				PortalClassLoaderUtil.getClassLoader(), UTF8Control.INSTANCE);
		}

		return resourceBundle;
	}

	private final Class<?> _clazz;
	private final DDMFormField _ddmFormField;
	private final Method _method;

}
