/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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