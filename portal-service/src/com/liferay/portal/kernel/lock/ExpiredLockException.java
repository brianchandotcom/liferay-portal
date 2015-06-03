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

package com.liferay.portal.kernel.lock;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Tina Tian
 */
public class ExpiredLockException extends PortalException {

	public ExpiredLockException() {
	}

	public ExpiredLockException(String msg) {
		super(msg);
	}

	public ExpiredLockException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public ExpiredLockException(Throwable cause) {
		super(cause);
	}

}