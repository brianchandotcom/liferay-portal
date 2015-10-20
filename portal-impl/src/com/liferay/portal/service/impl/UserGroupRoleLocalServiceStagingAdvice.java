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

package com.liferay.portal.service.impl;

import com.liferay.portal.service.UserGroupRoleLocalService;

import java.util.Arrays;
import java.util.List;

/**
 * @author Tomas Polesovsky
 */
public class UserGroupRoleLocalServiceStagingAdvice
	extends LiveGroupStagingAdvice {

	public UserGroupRoleLocalServiceStagingAdvice()
		throws NoSuchMethodException {

		super(UserGroupRoleLocalService.class);

		initCustomMethod(
			"addUserGroupRoles", 1, long.class, long.class, long[].class);
		initCustomMethod(
			"addUserGroupRoles", 1, long[].class, long.class, long.class);
		initCustomMethod("deleteUserGroupRoles", 0, long.class, int.class);
		initCustomMethod(
			"deleteUserGroupRoles", 1, long.class, long.class, long[].class);
		initCustomMethod("deleteUserGroupRoles", 1, long.class, long[].class);
		initCustomMethod("deleteUserGroupRoles", 1, long[].class, long.class);
		initCustomMethod(
			"deleteUserGroupRoles", 1, long[].class, long.class, long.class);
		initCustomMethod(
			"deleteUserGroupRoles", 1, long[].class, long.class, int.class);
		initCustomMethod("deleteUserGroupRolesByGroupId", 0, long.class);
		initCustomMethod("getUserGroupRoles", 1, long.class, long.class);
		initCustomMethod(
			"getUserGroupRoles", 1, long.class, long.class, int.class,
			int.class);
		initCustomMethod("getUserGroupRolesByGroup", 0, long.class);
		initCustomMethod(
			"getUserGroupRolesByGroupAndRole", 0, long.class, long.class);
		initCustomMethod(
			"getUserGroupRolesByUserUserGroupAndGroup", 1, long.class,
			long.class);
		initCustomMethod("getUserGroupRolesCount", 1, long.class, long.class);
		initCustomMethod(
			"hasUserGroupRole", 1, long.class, long.class, long.class);
		initCustomMethod(
			"hasUserGroupRole", 1, long.class, long.class, long.class,
			boolean.class);
		initCustomMethod(
			"hasUserGroupRole", 1, long.class, long.class, String.class);
		initCustomMethod(
			"hasUserGroupRole", 1, long.class, long.class, String.class,
			boolean.class);

		checkCoverage(_GROUP_METHODS_WHITELIST);
	}

	private static final List<String> _GROUP_METHODS_WHITELIST = Arrays.asList(
		new String[] {});

}