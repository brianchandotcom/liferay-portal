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

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Pablo Carvalho
 */
public class DDMFormFieldOptions implements Serializable {

	public void addOption(String value) {
		_options.put(value, new LocalizedValue(_defaultLocale));
	}

	public void addOptionLabel(
		String optionValue, Locale locale, String label) {

		LocalizedValue labels = _options.get(optionValue);

		if (labels == null) {
			labels = new LocalizedValue(_defaultLocale);

			_options.put(optionValue, labels);
		}

		labels.addString(locale, label);
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public LocalizedValue getOptionLabels(String optionValue) {
		return _options.get(optionValue);
	}

	public Map<String, LocalizedValue> getOptions() {
		return _options;
	}

	public Set<String> getOptionsValues() {
		return _options.keySet();
	}

	public void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;

		for (LocalizedValue localizedValue : _options.values()) {
			localizedValue.setDefaultLocale(defaultLocale);
		}
	}

	private Locale _defaultLocale = LocaleUtil.getDefault();
	private final Map<String, LocalizedValue> _options = new LinkedHashMap<>();

}