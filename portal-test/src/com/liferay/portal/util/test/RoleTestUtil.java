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

package com.liferay.portal.util.test;

import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.test.randomizerbumpers.UniqueStringRandomizerBumper;

/**
 * @author Roberto Díaz
 */
public class RoleTestUtil {

	public static long addGroupRole(long groupId) throws Exception {
		Role role = addRole(RoleConstants.TYPE_SITE);

		RoleLocalServiceUtil.addGroupRole(groupId, role.getRoleId());

		return role.getRoleId();
	}

	public static long addOrganizationRole(long groupId) throws Exception {
		Role role = addRole(RoleConstants.TYPE_ORGANIZATION);

		RoleLocalServiceUtil.addGroupRole(groupId, role.getRoleId());

		return role.getRoleId();
	}

	public static long addRegularRole(long groupId) throws Exception {
		Role role = addRole(RoleConstants.TYPE_REGULAR);

		RoleLocalServiceUtil.addGroupRole(groupId, role.getRoleId());

		return role.getRoleId();
	}

	public static void addResourcePermission(
			Role role, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			role.getCompanyId(), resourceName, scope, primKey, role.getRoleId(),
			actionId);
	}

	public static void addResourcePermission(
			String roleName, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		addResourcePermission(role, resourceName, scope, primKey, actionId);
	}

	public static Role addRole(int roleType) throws Exception {
		return addRole(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			roleType);
	}

	public static Role addRole(String roleName, int roleType) throws Exception {
		Role role = null;

		try {
			role = RoleLocalServiceUtil.getRole(
				TestPropsValues.getCompanyId(), roleName);
		}
		catch (NoSuchRoleException nsre) {
			role = RoleLocalServiceUtil.addRole(
				TestPropsValues.getUserId(), null, 0, roleName, null, null,
				roleType, null, null);
		}

		return role;
	}

	public static Role addRole(
			String roleName, int roleType, String resourceName, int scope,
			String primKey, String actionId)
		throws Exception {

		Role role = addRole(roleName, roleType);

		addResourcePermission(role, resourceName, scope, primKey, actionId);

		return role;
	}

	public static void removeResourcePermission(
			String roleName, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		ResourcePermissionLocalServiceUtil.removeResourcePermission(
			role.getCompanyId(), resourceName, scope, primKey, role.getRoleId(),
			actionId);
	}

}