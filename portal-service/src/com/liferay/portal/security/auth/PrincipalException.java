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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class PrincipalException extends PortalException {

	public static Class<?>[] getNestedClasses() {
		return _NESTED_CLASSES;
	}

	public PrincipalException() {
	}

	public PrincipalException(String msg) {
		super(msg);
	}

	public PrincipalException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public PrincipalException(Throwable cause) {
		super(cause);
	}

	public static class MustHavePermission extends PrincipalException {

		public MustHavePermission(long userId, String... actionIds) {
			super(
				String.format(
					"User %s must have permission to perform action %s",
					Validator.isNull(userId) ? "" : userId,
					StringUtil.merge(actionIds, ",")));

			this.actionId = actionIds;
			this.resourceId = 0;
			this.resourceName = null;
			this.userId = userId;
		}

		public MustHavePermission(
			long userId, String resourceName, long resourceId,
			String... actionIds) {

			super(
				String.format(
					"User %s must have %s permission for %s %s",
					Validator.isNull(userId) ? "" : userId,
					StringUtil.merge(actionIds, ","), resourceName,
					Validator.isNull(resourceId) ? "" : resourceId));

			this.actionId = actionIds;
			this.resourceName = resourceName;
			this.resourceId = resourceId;
			this.userId = userId;
		}

		public MustHavePermission(
			PermissionChecker permissionChecker, String... actionIds) {

			this(permissionChecker.getUserId(), actionIds);
		}

		public MustHavePermission(
			PermissionChecker permissionChecker, String resourceName,
			long resourceId, String... actionIds) {

			this(
				permissionChecker.getUserId(), resourceName, resourceId,
				actionIds);
		}

		public final String[] actionId;
		public final long resourceId;
		public final String resourceName;
		public final long userId;

	}

	private static final Class<?>[] _NESTED_CLASSES = {
		PrincipalException.class, PrincipalException.MustHavePermission.class
	};

}