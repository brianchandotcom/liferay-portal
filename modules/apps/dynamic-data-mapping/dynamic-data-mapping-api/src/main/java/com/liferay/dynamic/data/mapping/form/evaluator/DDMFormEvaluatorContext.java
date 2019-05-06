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

package com.liferay.dynamic.data.mapping.form.evaluator;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author     Leonardo Barros
 * @deprecated As of Judson (7.1.x)
 */
@Deprecated
public class DDMFormEvaluatorContext {

	public DDMFormEvaluatorContext(
		DDMForm ddmForm, DDMFormValues ddmFormValues, Locale locale) {

		_ddmForm = ddmForm;
		_ddmFormValues = ddmFormValues;
		_locale = locale;
	}

	public void addProperty(String key, Object value) {
		_properties.put(key, value);
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public DDMFormValues getDDMFormValues() {
		return _ddmFormValues;
	}

	public Locale getLocale() {
		return _locale;
	}

	public <T> T getProperty(String key) {
		return (T)_properties.get(key);
	}

	public void setDDMForm(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	public void setDDMFormValues(DDMFormValues ddmFormValues) {
		_ddmFormValues = ddmFormValues;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	private DDMForm _ddmForm;
	private DDMFormValues _ddmFormValues;
	private Locale _locale;
	private final Map<String, Object> _properties = new HashMap<>();

}