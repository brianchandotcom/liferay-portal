/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @author Zsolt Berentey
 */
public class AuthException extends PortalException {

	public static final int INTERNAL_SERVER_ERROR = 1;

	public static final int INVALID_SHARED_SECRET = 2;

	public static final int NO_SHARED_SECRET = 3;

	public AuthException() {
	}

	public AuthException(String msg) {
		super(msg);
	}

	public AuthException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AuthException(Throwable throwable) {
		super(throwable);
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private int _type;

}