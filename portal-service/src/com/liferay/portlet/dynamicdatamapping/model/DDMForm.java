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

package com.liferay.portlet.dynamicdatamapping.model;

import java.io.Serializable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMForm implements Serializable {

	public void addAvailableLocale(Locale locale) {
		_availableLocales.add(locale);
	}

	public List<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public List<DDMFormField> getDDMFormFields() {
		return _ddmFormFields;
	}

	public Map<String, DDMFormField> getDDMFormFieldsMap(
		boolean includeNestedDDMFormFields) {

		Map<String, DDMFormField> ddmFormFieldsMap =
			new HashMap<String, DDMFormField>();

		for (DDMFormField ddmFormField : _ddmFormFields) {
			ddmFormFieldsMap.put(ddmFormField.getName(), ddmFormField);

			if (includeNestedDDMFormFields) {
				ddmFormFieldsMap.putAll(
					ddmFormField.getNestedDDMFormFieldsMap());
			}
		}

		return ddmFormFieldsMap;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public void setAvailableLocales(List<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDDMFormFields(List<DDMFormField> ddmFormFields) {
		_ddmFormFields = ddmFormFields;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	private List<Locale> _availableLocales;
	private List<DDMFormField> _ddmFormFields = new LinkedList<DDMFormField>();
	private Locale _defaultLocale;

}