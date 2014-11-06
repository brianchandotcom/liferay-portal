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

/**
 * The base class for all exceptions caused by system problems. Examples include
 * database connection errors and file not found errors.
 *
 * <p>
 * System exceptions are always unexpected, and generally indicate that the
 * portal is misconfigured or that a critical service is unavailable.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    PortalException
 */
public class SystemException extends NestableRuntimeException {

	public SystemException() {
	}

	public SystemException(String msg) {
		super(msg);
	}

	public SystemException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

}