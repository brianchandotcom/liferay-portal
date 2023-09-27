/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.localized;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Locale;
import java.util.Set;

/**
 * @author Jorge Ferrer
 */
public class SingleValueInfoLocalizedValue<T> implements InfoLocalizedValue<T> {

	public SingleValueInfoLocalizedValue(T value) {
		_value = value;
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		return _availableLocales;
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.getSiteDefault();
	}

	@Override
	public T getValue() {
		return _value;
	}

	@Override
	public T getValue(Locale locale) {
		return _value;
	}

	private final Set<Locale> _availableLocales = SetUtil.fromArray(
		LocaleUtil.getSiteDefault());
	private final T _value;

}