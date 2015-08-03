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

package com.liferay.dynamic.data.mapping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Bruno Basto
 */
public class DDMPermission {

	public static void check(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, name, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, name, groupId, actionId);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String name,
		String actionId) {

		return permissionChecker.hasPermission(
			groupId, name, groupId, actionId);
	}

}