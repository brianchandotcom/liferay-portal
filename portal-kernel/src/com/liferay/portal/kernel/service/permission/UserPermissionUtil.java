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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class UserPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long userId,
			long[] organizationIds, String actionId)
		throws PrincipalException {

		getUserPermission().check(
			permissionChecker, userId, organizationIds, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long userId, String actionId)
		throws PrincipalException {

		getUserPermission().check(permissionChecker, userId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long userId,
		long[] organizationIds, String actionId) {

		return getUserPermission().contains(
			permissionChecker, userId, organizationIds, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long userId, String actionId) {

		return getUserPermission().contains(
			permissionChecker, userId, actionId);
	}

	public static UserPermission getUserPermission() {
		return _userPermission;
	}

	public void setUserPermission(UserPermission userPermission) {
		_userPermission = userPermission;
	}

	private static UserPermission _userPermission;

}