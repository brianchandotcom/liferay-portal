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

	public static class MustNotRemoveLastAdministator
		extends RequiredRoleException {

		public MustNotRemoveLastAdministator() {
		}

		public MustNotRemoveLastAdministator(String msg) {
			super(msg);
		}

		public MustNotRemoveLastAdministator(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotRemoveLastAdministator(Throwable cause) {
			super(cause);
		}

	}

	public static class MustNotRemoveUserRole extends RequiredRoleException {

		public MustNotRemoveUserRole() {
		}

		public MustNotRemoveUserRole(String msg) {
			super(msg);
		}

		public MustNotRemoveUserRole(String msg, Throwable cause) {
			super(msg, cause);
		}

		public MustNotRemoveUserRole(Throwable cause) {
			super(cause);
		}

	}

}