/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Roberto Díaz
 */
public class EscapableLocalizableFunction implements Serializable {

	public EscapableLocalizableFunction(Function<Locale, String> function) {
		this(function, true);
	}

	public EscapableLocalizableFunction(
		Function<Locale, String> function, boolean escape) {

		_function = function;
		_escape = escape;
	}

	public String getEscapedValue(Locale locale) {
		if (Validator.isNull(_escapedValueMap.get(locale))) {
			if (_escape) {
				_escapedValueMap.put(locale, escape(locale));
			}
			else {
				_escapedValueMap.put(locale, getOriginalValue(locale));
			}
		}

		return _escapedValueMap.get(locale);
	}

	public String getOriginalValue(Locale locale) {
		return _function.apply(locale);
	}

	protected String escape(Locale locale) {
		return HtmlUtil.escape(getOriginalValue(locale));
	}

	private final boolean _escape;
	private final Map<Locale, String> _escapedValueMap = new HashMap<>();
	private final Function<Locale, String> _function;

}