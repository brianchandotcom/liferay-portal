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
import com.liferay.dynamic.data.mapping.annotations.DDMFormRule;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author Marcellus Tavares
 */
public class DDMFormFactoryHelper {

	public DDMFormFactoryHelper(Class<?> clazz) {
		_clazz = clazz;
		_ddmForm = clazz.getAnnotation(DDMForm.class);

		_availableLocales = getAvailableLocales();
		_defaultLocale = getDefaultLocale();
	}

	public com.liferay.dynamic.data.mapping.model.DDMForm createDDMForm() {
		com.liferay.dynamic.data.mapping.model.DDMForm ddmForm =
			new com.liferay.dynamic.data.mapping.model.DDMForm();

		ddmForm.setAvailableLocales(_availableLocales);
		ddmForm.setDefaultLocale(_defaultLocale);
		ddmForm.setDDMFormFields(getDDMFormFields());
		ddmForm.setDDMFormRules(getDDMFormRules());

		return ddmForm;
	}

	protected void collectDDMFormFieldMethodsMap(
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

	protected Set<Locale> getAvailableLocales() {
		if (Validator.isNull(_ddmForm.availableLanguageIds())) {
			Locale defaultLocale = getDefaultLocale();

			return SetUtil.fromArray(new Locale[] {defaultLocale});
		}

		Set<Locale> availableLocales = new TreeSet<>();

		for (String availableLanguageId :
				StringUtil.split(_ddmForm.availableLanguageIds())) {

			availableLocales.add(
				LocaleUtil.fromLanguageId(availableLanguageId));
		}

		return availableLocales;
	}

	protected Collection<Method> getDDMFormFieldMethods() {
		Map<String, Method> methodsMap = new TreeMap<>();

		collectDDMFormFieldMethodsMap(_clazz, methodsMap);

		return methodsMap.values();
	}

	protected List<com.liferay.dynamic.data.mapping.model.DDMFormField>
		getDDMFormFields() {

		List<com.liferay.dynamic.data.mapping.model.DDMFormField>
			ddmFormFields = new ArrayList<>();

		for (Method method : getDDMFormFieldMethods()) {
			DDMFormFieldFactoryHelper ddmFormFieldFactoryHelper =
				new DDMFormFieldFactoryHelper(method);

			ddmFormFieldFactoryHelper.setAvailableLocales(_availableLocales);
			ddmFormFieldFactoryHelper.setDefaultLocale(_defaultLocale);

			ddmFormFields.add(ddmFormFieldFactoryHelper.createDDMFormField());
		}

		return ddmFormFields;
	}

	protected List<com.liferay.dynamic.data.mapping.model.DDMFormRule>
		getDDMFormRules() {

		List<com.liferay.dynamic.data.mapping.model.DDMFormRule> ddmFormRules =
			new ArrayList<>();

		for (DDMFormRule ddmFormRule : _ddmForm.rules()) {
			ddmFormRules.add(
				new com.liferay.dynamic.data.mapping.model.DDMFormRule(
					ddmFormRule.condition(),
					ListUtil.fromArray(ddmFormRule.actions())));
		}

		return ddmFormRules;
	}

	protected Locale getDefaultLocale() {
		if (Validator.isNull(_ddmForm.defaultLanguageId())) {
			Locale defaultLocale = LocaleThreadLocal.getThemeDisplayLocale();

			if (defaultLocale == null) {
				defaultLocale = LocaleUtil.getDefault();
			}

			return defaultLocale;
		}

		return LocaleUtil.fromLanguageId(_ddmForm.defaultLanguageId());
	}

	private static final Class<? extends Annotation>
		_DDM_FORM_FIELD_ANNOTATION =
			com.liferay.dynamic.data.mapping.annotations.DDMFormField.class;

	private final Set<Locale> _availableLocales;
	private final Class<?> _clazz;
	private final DDMForm _ddmForm;
	private final Locale _defaultLocale;

}