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

import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Charles May
 */
public class UserGroupPermissionImpl implements UserGroupPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, long userGroupId,
			String actionId)
		throws PrincipalException {

		if (!contains(permissionChecker, userGroupId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long userGroupId,
		String actionId) {

		return permissionChecker.hasPermission(
			0, UserGroup.class.getName(), userGroupId, actionId);
	}

}