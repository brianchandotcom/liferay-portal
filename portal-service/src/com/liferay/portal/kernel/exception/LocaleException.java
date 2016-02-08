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

import java.util.Collection;
import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 */
public class LocaleException extends PortalException {

	public static final int TYPE_CONTENT = 3;

	public static final int TYPE_DEFAULT = 4;

	public static final int TYPE_DISPLAY_SETTINGS = 1;

	public static final int TYPE_EXPORT_IMPORT = 2;

	public LocaleException() {
		_type = 0;
	}

	public LocaleException(int type) {
		_type = type;
	}

	public LocaleException(int type, String msg) {
		super(msg);

		_type = type;
	}

	public LocaleException(int type, String msg, Throwable cause) {
		super(msg, cause);

		_type = type;
	}

	public LocaleException(int type, Throwable cause) {
		super(cause);

		_type = type;
	}

	public Collection<Locale> getSourceAvailableLocales() {
		return _sourceAvailableLocales;
	}

	public Collection<Locale> getTargetAvailableLocales() {
		return _targetAvailableLocales;
	}

	public int getType() {
		return _type;
	}

	public void setSourceAvailableLocales(
		Collection<Locale> sourceAvailableLocales) {

		_sourceAvailableLocales = sourceAvailableLocales;
	}

	public void setTargetAvailableLocales(
		Collection<Locale> targetAvailableLocales) {

		_targetAvailableLocales = targetAvailableLocales;
	}

	private Collection<Locale> _sourceAvailableLocales;
	private Collection<Locale> _targetAvailableLocales;
	private final int _type;

}