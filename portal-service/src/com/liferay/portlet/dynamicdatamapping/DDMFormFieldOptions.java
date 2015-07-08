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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.util.LocaleUtil;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public class DDMFormFieldOptions implements Serializable {

	public DDMFormFieldOptions() {
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

	public void setDefaultLocale(Locale locale) {
		_defaultLocale = locale;
	}

	public void setOptions(Map<String, LocalizedValue> options) {
		_options = options;
	}

	private Locale _defaultLocale = LocaleUtil.getDefault();
	private Map<String, LocalizedValue> _options = new LinkedHashMap<>();

}