/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class UserPermissionUtil {

	/**
	 * @deprecated
	 */
	public static void check(
			PermissionChecker permissionChecker, long userId,
			long organizationId, long locationId, String actionId)
		throws PrincipalException {

		check(
			permissionChecker, userId, new long[] {organizationId, locationId},
			actionId);
	}

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

	/**
	 * @deprecated
	 */
	public static boolean contains(
		PermissionChecker permissionChecker, long userId, long organizationId,
		long locationId, String actionId) {

		return contains(
			permissionChecker, userId, new long[] {organizationId, locationId},
			actionId);
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
		PortalRuntimePermission.checkGetBeanProperty(UserPermissionUtil.class);

		return _userPermission;
	}

	public void setUserPermission(UserPermission userPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_userPermission = userPermission;
	}

	private static UserPermission _userPermission;

}