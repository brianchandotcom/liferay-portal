/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.Role;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Charles May
 */
public class RolePermissionImpl implements RolePermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long roleId, String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, roleId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long groupId, long roleId,
		String actionId) {

		return permissionChecker.hasPermission(
			groupId, Role.class.getName(), roleId, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long roleId, String actionId) {

		return contains(permissionChecker, 0, roleId, actionId);
	}

}