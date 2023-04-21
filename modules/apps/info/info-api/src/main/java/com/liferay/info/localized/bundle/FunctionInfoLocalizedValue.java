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

package com.liferay.info.localized.bundle;

import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * @author Jorge Ferrer
 */
public class FunctionInfoLocalizedValue<T> implements InfoLocalizedValue<T> {

	public FunctionInfoLocalizedValue(Function<Locale, T> function) {
		_function = function;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FunctionInfoLocalizedValue)) {
			return false;
		}

		FunctionInfoLocalizedValue functionInfoLocalizedValue =
			(FunctionInfoLocalizedValue)object;

		return Objects.equals(functionInfoLocalizedValue._function, _function);
	}

	@Override
	public Set<Locale> getAvailableLocales() {
		return LanguageUtil.getAvailableLocales();
	}

	@Override
	public Locale getDefaultLocale() {
		return LocaleUtil.getSiteDefault();
	}

	@Override
	public T getValue() {
		return getValue(getDefaultLocale());
	}

	@Override
	public T getValue(Locale locale) {
		return _function.apply(locale);
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, _function);
	}

	private final Function<Locale, T> _function;

}