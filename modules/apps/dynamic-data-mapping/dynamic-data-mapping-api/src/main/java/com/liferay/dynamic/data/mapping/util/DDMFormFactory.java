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

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactory {

	public static DDMForm create(Class<?> clazz) {
		if (!clazz.isAnnotationPresent(_DDM_FORM_ANNOTATION)) {
			throw new IllegalArgumentException(
				"Unsupported class " + clazz.getName());
		}

		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(getAvailableLocales(clazz));
		ddmForm.setDefaultLocale(getDefaultLocale(clazz));

		for (Method method : getDDMFormFieldMethods(clazz)) {
			DDMFormField ddmFormField = createDDMFormField(clazz, method);

			ddmForm.addDDMFormField(ddmFormField);
		}

		return ddmForm;
	}

	protected static void collectDDMFormFieldMethodsMap(
		Class<?> clazz, Map<String, Method> methodsMap) {

		for (Class<?> interfaceClass : clazz.getInterfaces()) {
			collectDDMFormFieldMethodsMap(interfaceClass, methodsMap);
		}

		for (Method method : clazz.getDeclaredMethods()) {
			if (method.isAnnotationPresent(_DDM_FORM_FIELD_ANNOTATION)) {
				methodsMap.put(method.getName(), method);
			}
		}
	}

	protected static DDMFormField createDDMFormField(
		Class<?> clazz, Method method) {

		DDMFormFactoryHelper ddmFormFactoryHelper = new DDMFormFactoryHelper(
			clazz, method);

		String name = ddmFormFactoryHelper.getDDMFormFieldName();
		String type = ddmFormFactoryHelper.getDDMFormFieldType();

		DDMFormField ddmFormField = new DDMFormField(name, type);

		Map<String, Object> properties = ddmFormFactoryHelper.getProperties();

		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (ddmFormFactoryHelper.isLocalizableValue((String)value)) {
				value = ddmFormFactoryHelper.getPropertyValue(value);
			}

			ddmFormField.setProperty(key, value);
		}

		ddmFormField.setDataType(
			ddmFormFactoryHelper.getDDMFormFieldDataType());
		ddmFormField.setDDMFormFieldOptions(
			ddmFormFactoryHelper.getDDMFormFieldOptions());
		ddmFormField.setDDMFormFieldValidation(
			ddmFormFactoryHelper.getDDMFormFieldValidation());
		ddmFormField.setLabel(ddmFormFactoryHelper.getDDMFormFieldLabel());
		ddmFormField.setLocalizable(
			ddmFormFactoryHelper.isDDMFormFieldLocalizable(method));
		ddmFormField.setPredefinedValue(
			ddmFormFactoryHelper.getDDMFormFieldPredefinedValue());
		ddmFormField.setRepeatable(
			ddmFormFactoryHelper.isDDMFormFieldRepeatable());
		ddmFormField.setRequired(ddmFormFactoryHelper.isDDMFormFieldRequired());
		ddmFormField.setTip(ddmFormFactoryHelper.getDDMFormFieldTip());
		ddmFormField.setVisibilityExpression(
			ddmFormFactoryHelper.getDDMFormFieldVisibilityExpression());

		return ddmFormField;
	}

	protected static Set<Locale> getAvailableLocales(Class<?> clazz) {
		com.liferay.dynamic.data.mapping.annotations.DDMForm ddmForm =
			clazz.getAnnotation(
				com.liferay.dynamic.data.mapping.annotations.DDMForm.class);

		if (Validator.isNull(ddmForm.availableLanguageIds())) {
			Locale defaultLocale = getDefaultLocale(clazz);

			return SetUtil.fromArray(new Locale[] {defaultLocale});
		}

		Set<Locale> availableLocales = new TreeSet<>();

		for (String availableLanguageId :
				StringUtil.split(ddmForm.availableLanguageIds())) {

			availableLocales.add(
				LocaleUtil.fromLanguageId(availableLanguageId));
		}

		return availableLocales;
	}

	protected static Collection<Method> getDDMFormFieldMethods(Class<?> clazz) {
		Map<String, Method> methodsMap = new HashMap<>();

		collectDDMFormFieldMethodsMap(clazz, methodsMap);

		return methodsMap.values();
	}

	protected static Locale getDefaultLocale(Class<?> clazz) {
		com.liferay.dynamic.data.mapping.annotations.DDMForm ddmForm =
			clazz.getAnnotation(
				com.liferay.dynamic.data.mapping.annotations.DDMForm.class);

		if (Validator.isNull(ddmForm.defaultLanguageId())) {
			Locale defaultLocale = LocaleThreadLocal.getThemeDisplayLocale();

			if (defaultLocale == null) {
				defaultLocale = LocaleUtil.getDefault();
			}

			return defaultLocale;
		}

		return LocaleUtil.fromLanguageId(ddmForm.defaultLanguageId());
	}

	private static final Class<? extends Annotation> _DDM_FORM_ANNOTATION =
		com.liferay.dynamic.data.mapping.annotations.DDMForm.class;

	private static final Class<? extends Annotation>
		_DDM_FORM_FIELD_ANNOTATION =
			com.liferay.dynamic.data.mapping.annotations.DDMFormField.class;

}