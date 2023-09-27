/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class UnlocalizedValue implements Value {

	public UnlocalizedValue(String value) {
		_values.put(LocaleUtil.ROOT, value);
	}

	public UnlocalizedValue(UnlocalizedValue unlocalizedValue) {
		_values.put(
			LocaleUtil.ROOT, unlocalizedValue.getString(LocaleUtil.ROOT));
	}

	@Override
	public void addString(Locale locale, String value) {
		_values.put(LocaleUtil.ROOT, value);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UnlocalizedValue)) {
			return false;
		}

		UnlocalizedValue unlocalizedValue = (UnlocalizedValue)object;

		if (Objects.equals(_values, unlocalizedValue._values)) {
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
		return LocaleUtil.ROOT;
	}

	@Override
	public String getString(Locale locale) {
		return _values.get(LocaleUtil.ROOT);
	}

	@Override
	public Map<Locale, String> getValues() {
		return _values;
	}

	@Override
	public int hashCode() {
		return _values.hashCode();
	}

	@Override
	public boolean isLocalized() {
		return false;
	}

	@Override
	public void setDefaultLocale(Locale defaultLocale) {
		throw new UnsupportedOperationException();
	}

	private final Map<Locale, String> _values = new HashMap<>();

}