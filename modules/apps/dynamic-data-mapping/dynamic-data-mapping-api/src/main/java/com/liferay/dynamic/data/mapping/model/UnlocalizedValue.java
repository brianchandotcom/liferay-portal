/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Marcellus Tavares
 */
public class UnlocalizedValue implements Value {

	public UnlocalizedValue(String value) {
		_value = value;
	}

	public UnlocalizedValue(UnlocalizedValue unlocalizedValue) {
		_value = unlocalizedValue.getString(null);
	}

	@Override
	public void addString(Locale locale, String value) {
		_value = value;
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

		return Objects.equals(_value, unlocalizedValue._value);
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		return Collections.singleton(LocaleUtil.ROOT);
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.ROOT;
	}

	@Override
	public String getString(Locale locale) {
		return _value;
	}

	@Override
	public Map<Locale, String> getValues() {
		return Collections.singletonMap(LocaleUtil.ROOT, _value);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _value);
	}

	@Override
	public boolean isLocalized() {
		return false;
	}

	@Override
	public void removeLocale(Locale locale) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setDefaultLocale(Locale defaultLocale) {
		throw new UnsupportedOperationException();
	}

	private String _value;

}