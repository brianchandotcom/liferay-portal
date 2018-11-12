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

package com.liferay.dynamic.data.mapping.storage;

import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.petra.lang.HashUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marcellus Tavares
 * @author Pablo Carvalho
 */
public class DDMFormValues implements Serializable {

	public DDMFormValues(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	public void addAvailableLocale(Locale locale) {
		_availableLocales.add(locale);
	}

	public void addDDMFormFieldValue(DDMFormFieldValue ddmFormFieldValue) {
		ddmFormFieldValue.setDDMFormValues(this);

		_ddmFormFieldValues.add(ddmFormFieldValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMFormValues)) {
			return false;
		}

		DDMFormValues ddmFormValues = (DDMFormValues)obj;

		if (Objects.equals(
				_availableLocales, ddmFormValues._availableLocales) &&
			Objects.equals(_defaultLocale, ddmFormValues._defaultLocale) &&
			Objects.equals(
				_ddmFormFieldValues, ddmFormValues._ddmFormFieldValues)) {

			return true;
		}

		return false;
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

	public Map<String, List<DDMFormFieldValue>> getDDMFormFieldValuesMap() {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			new LinkedHashMap<>();

		for (DDMFormFieldValue ddmFormFieldValue : _ddmFormFieldValues) {
			List<DDMFormFieldValue> ddmFormFieldValues =
				ddmFormFieldValuesMap.get(ddmFormFieldValue.getName());

			if (ddmFormFieldValues == null) {
				ddmFormFieldValues = new ArrayList<>();

				ddmFormFieldValuesMap.put(
					ddmFormFieldValue.getName(), ddmFormFieldValues);
			}

			ddmFormFieldValues.add(ddmFormFieldValue);
		}

		return ddmFormFieldValuesMap;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _availableLocales);

		hash = HashUtil.hash(hash, _defaultLocale);

		return HashUtil.hash(hash, _ddmFormFieldValues);
	}

	public void setAvailableLocales(Set<Locale> availableLocales) {
		_availableLocales = availableLocales;
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

	private Set<Locale> _availableLocales = new LinkedHashSet<>();
	private final DDMForm _ddmForm;
	private List<DDMFormFieldValue> _ddmFormFieldValues = new ArrayList<>();
	private Locale _defaultLocale;

}