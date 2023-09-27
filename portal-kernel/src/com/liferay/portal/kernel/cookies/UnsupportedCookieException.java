/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.cookies;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Tamas Molnar
 */
public class UnsupportedCookieException extends PortalException {

	public UnsupportedCookieException() {
	}

	public UnsupportedCookieException(String msg) {
		super(msg);
	}

	public UnsupportedCookieException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public UnsupportedCookieException(Throwable throwable) {
		super(throwable);
	}

}