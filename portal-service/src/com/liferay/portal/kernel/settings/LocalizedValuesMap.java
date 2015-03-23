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

package com.liferay.portal.kernel.settings;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class LocalizedValuesMap {

	public LocalizedValuesMap() {
		this(null);
	}

	public LocalizedValuesMap(String defaultValue) {
		_defaultValue = defaultValue;
	}

	public String get(Locale locale) {
		String value = _values.get(locale);

		if (value == null) {
			value = _defaultValue;
		}

		return value;
	}

	public String getDefaultValue() {
		return _defaultValue;
	}

	public Map<Locale, String> getValues() {
		return new HashMap<>(_values);
	}

	public void put(Locale locale, String value) {
		_values.put(locale, value);
	}

	private final String _defaultValue;
	private final Map<Locale, String> _values = new HashMap<>();

}