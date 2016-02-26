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

	public AuthException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public AuthException(Throwable cause) {
		super(cause);
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private int _type;

}