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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class LocalizedValue implements Serializable {

	public LocalizedValue() {
		this(LocaleUtil.getDefault());
	}

	public LocalizedValue(Locale defaultLocale) {
		setDefaultLocale(defaultLocale);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedValue)) {
			return false;
		}

		LocalizedValue localizedValue = (LocalizedValue)obj;

		if (Validator.equals(_defaultLocale, localizedValue._defaultLocale) &&
			Validator.equals(_values, localizedValue._values)) {

			return true;
		}

		return false;
	}

	public Set<Locale> getAvailableLocales() {
		return _values.keySet();
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public String getString(Locale locale) {
		String value = _values.get(locale);

		if (value == null) {
			value = _values.get(_defaultLocale);
		}

		return value;
	}

	public Map<Locale, String> getValues() {
		return _values;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _defaultLocale);

		return HashUtil.hash(hash, _values);
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	public void setValues(Map<Locale, String> values) {
		_values = values;
	}

	private Locale _defaultLocale;
	private Map<Locale, String> _values = new HashMap<>();

}