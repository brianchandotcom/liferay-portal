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
		return LocaleUtil.getDefault();
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
		LocaleUtil.getDefault());
	private final T _value;

}