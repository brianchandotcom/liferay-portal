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