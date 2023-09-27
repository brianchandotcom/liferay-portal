/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Eduardo Lundgren
 */
public class NoSuchCalendarException extends NoSuchModelException {

	public NoSuchCalendarException() {
	}

	public NoSuchCalendarException(String msg) {
		super(msg);
	}

	public NoSuchCalendarException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCalendarException(Throwable throwable) {
		super(throwable);
	}

}