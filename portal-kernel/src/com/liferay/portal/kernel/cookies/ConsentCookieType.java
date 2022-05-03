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

package com.liferay.portal.kernel.cookies;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;

import java.util.Locale;

/**
 * @author Jürgen Kappler
 */
public class ConsentCookieType {

	public ConsentCookieType(
		String name, LocalizedValuesMap descriptionMap, boolean prechecked) {

		_name = name;
		_descriptionMap = descriptionMap;
		_prechecked = prechecked;
	}

	public String getDescription(Locale locale) {
		return _descriptionMap.get(locale);
	}

	public LocalizedValuesMap getDescriptionMap() {
		return _descriptionMap;
	}

	public String getName() {
		return _name;
	}

	public boolean isPrechecked() {
		return _prechecked;
	}

	private final LocalizedValuesMap _descriptionMap;
	private final String _name;
	private final boolean _prechecked;

}