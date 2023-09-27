/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Michael Young
 */
public class CookieNotSupportedException extends PortalException {

	public CookieNotSupportedException() {
	}

	public CookieNotSupportedException(String msg) {
		super(msg);
	}

	public CookieNotSupportedException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CookieNotSupportedException(Throwable throwable) {
		super(throwable);
	}

}