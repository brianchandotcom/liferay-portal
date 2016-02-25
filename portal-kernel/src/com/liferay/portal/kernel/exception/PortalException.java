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
 * The base class for all exceptions related to business logic. Examples include
 * invalid input, portlet errors, and references to non existent database
 * records.
 *
 * <p>
 * Portal exceptions are generally caused by user error, and do not indicate
 * that anything is wrong with the portal itself.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    SystemException
 */
public class PortalException extends NestableException {

	public PortalException() {
	}

	public PortalException(String msg) {
		super(msg);
	}

	public PortalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PortalException(Throwable cause) {
		super(cause);
	}

}