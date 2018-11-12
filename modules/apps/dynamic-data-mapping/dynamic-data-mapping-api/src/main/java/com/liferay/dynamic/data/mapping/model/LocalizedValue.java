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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class LocalizedValue implements Value {

	public LocalizedValue() {
		this(LocaleUtil.getDefault());
	}

	public LocalizedValue(Locale defaultLocale) {
		setDefaultLocale(defaultLocale);
	}

	public LocalizedValue(LocalizedValue localizedValue) {
		_defaultLocale = localizedValue._defaultLocale;

		Map<Locale, String> values = localizedValue._values;

		for (Map.Entry<Locale, String> entry : values.entrySet()) {
			addString(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void addString(Locale locale, String value) {
		_values.put(locale, value);
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

		if (Objects.equals(_defaultLocale, localizedValue._defaultLocale) &&
			Objects.equals(_values, localizedValue._values)) {

			return true;
		}

		return false;
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		return _values.keySet();
	}

	@Override
	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	@Override
	public String getString(Locale locale) {
		String value = _values.get(locale);

		if (value == null) {
			value = _values.get(_defaultLocale);
		}

		return value;
	}

	@Override
	public Map<Locale, String> getValues() {
		return _values;
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, _defaultLocale);

		return HashUtil.hash(hash, _values);
	}

	@Override
	public boolean isLocalized() {
		return true;
	}

	@Override
	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	private Locale _defaultLocale;
	private final Map<Locale, String> _values = new HashMap<>();

}