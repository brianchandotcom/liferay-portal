/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo Lundgren
 */
public class CalendarResourceNameException extends PortalException {

	public CalendarResourceNameException() {
	}

	public CalendarResourceNameException(String msg) {
		super(msg);
	}

	public CalendarResourceNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CalendarResourceNameException(Throwable throwable) {
		super(throwable);
	}

}