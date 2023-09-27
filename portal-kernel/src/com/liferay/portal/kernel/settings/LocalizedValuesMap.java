/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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