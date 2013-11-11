/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Sergio González
 */
public class LocalizedException extends PortalException {

	public void addLocale(Locale locale) {
		_locales.add(locale);
	}

	public List<Locale> getLocales() {
		return _locales;
	}

	private List<Locale> _locales = new ArrayList<Locale>();

}