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

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Marcellus Tavares
 * @author Pablo Carvalho
 */
public class DDMFormValues {

	public void addDDMFormFieldValue(DDMFormFieldValue ddmFormFieldValue) {
		ddmFormFieldValue.setDDMFormValues(this);

		_ddmFormFieldValues.add(ddmFormFieldValue);
	}

	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public DDMForm getDDMForm() {
		return _ddmForm;
	}

	public List<DDMFormFieldValue> getDDMFormFieldValues() {
		return _ddmFormFieldValues;
	}

	public Map<String, List<Value>> getDDMFormFieldValuesMap() {
		Map<String, List<Value>> ddmFormFieldValuesMap =
			new HashMap<String, List<Value>>();

		for (DDMFormFieldValue ddmFormFieldValue : _ddmFormFieldValues) {
			List<Value> values = ddmFormFieldValuesMap.get(
				ddmFormFieldValue.getName());

			if (values == null) {
				values = new ArrayList<Value>();

				ddmFormFieldValuesMap.put(ddmFormFieldValue.getName(), values);
			}

			values.add(ddmFormFieldValue.getValue());
		}

		return ddmFormFieldValuesMap;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDDMForm(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	public void setDDMFormFieldValues(
		List<DDMFormFieldValue> ddmFormFieldValues) {

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			ddmFormFieldValue.setDDMFormValues(this);
		}

		_ddmFormFieldValues = ddmFormFieldValues;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	private Set<Locale> _availableLocales;
	private DDMForm _ddmForm;
	private List<DDMFormFieldValue> _ddmFormFieldValues =
		new ArrayList<DDMFormFieldValue>();
	private Locale _defaultLocale;

}