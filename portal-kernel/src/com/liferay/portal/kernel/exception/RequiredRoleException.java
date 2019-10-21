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
 * @author Brian Wing Shun Chan
 * @author Roberto Díaz
 */
public class RequiredRoleException extends PortalException {

	public RequiredRoleException() {
	}

	public RequiredRoleException(String msg) {
		super(msg);
	}

	public RequiredRoleException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RequiredRoleException(Throwable cause) {
		super(cause);
	}

	public static final class RequiredAdminRoleException
		extends RequiredRoleException {

		public RequiredAdminRoleException() {
		}

		public RequiredAdminRoleException(String msg) {
			super(msg);
		}

		public RequiredAdminRoleException(String msg, Throwable cause) {
			super(msg, cause);
		}

		public RequiredAdminRoleException(Throwable cause) {
			super(cause);
		}

	}

	public static final class RequiredUserRoleException
		extends RequiredRoleException {

		public RequiredUserRoleException() {
		}

		public RequiredUserRoleException(String msg) {
			super(msg);
		}

		public RequiredUserRoleException(String msg, Throwable cause) {
			super(msg, cause);
		}

		public RequiredUserRoleException(Throwable cause) {
			super(cause);
		}

	}

}