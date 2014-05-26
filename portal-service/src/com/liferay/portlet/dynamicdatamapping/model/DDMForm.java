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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMForm {

	public void addAvailableLocale(Locale locale) {
		_availableLocales.add(locale);
	}

	public List<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public List<DDMFormField> getFields() {
		return _fields;
	}

	public Map<String, DDMFormField> getFieldsMap(boolean includeNestedFields) {
		Map<String, DDMFormField> fieldsMap =
			new HashMap<String, DDMFormField>();

		for (DDMFormField field : _fields) {
			fieldsMap.put(field.getName(), field);

			if (includeNestedFields) {
				fieldsMap.putAll(field.getNestedFieldsMap());
			}
		}

		return fieldsMap;
	}

	public void setAvailableLocales(List<Locale> availableLocales) {
		_availableLocales = availableLocales;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	public void setFields(List<DDMFormField> fields) {
		_fields = fields;
	}

	private List<Locale> _availableLocales;
	private Locale _defaultLocale;
	private List<DDMFormField> _fields = new LinkedList<DDMFormField>();

}