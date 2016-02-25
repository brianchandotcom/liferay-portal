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

package com.liferay.portal.kernel.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergio González
 */
public class LocalizedException extends PortalException {

	public LocalizedException() {
	}

	public LocalizedException(String msg) {
		super(msg);
	}

	public LocalizedException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LocalizedException(Throwable cause) {
		super(cause);
	}

	public void addLocalizedException(Locale locale, Exception exception) {
		_localizedExceptionsMap.put(locale, exception);
	}

	public Map<Locale, Exception> getLocalizedExceptionsMap() {
		return _localizedExceptionsMap;
	}

	private final Map<Locale, Exception> _localizedExceptionsMap =
		new HashMap<>();

}