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

package com.liferay.expando.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;

/**
 * @author Cristina Rodríguez
 */
public class MustInformDefaultLocaleException extends PortalException {

	public MustInformDefaultLocaleException() {
	}

	public MustInformDefaultLocaleException(Locale locale) {
		super(
			"A value for the default locale (" + locale.getLanguage() +
				") must be defined");
	}

	public MustInformDefaultLocaleException(String msg) {
		super(msg);
	}

	public MustInformDefaultLocaleException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public MustInformDefaultLocaleException(Throwable throwable) {
		super(throwable);
	}

}