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

package com.liferay.depot.test.util;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.petra.function.UnsafeBiConsumer;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;

/**
 * @author Alejandro Tardín
 */
public class DepotTestUtil {

	public static void withAssetLibraryAdministrator(
			DepotEntry depotEntry,
			UnsafeConsumer<User, Exception> unsafeConsumer)
		throws Exception {

		_withGroupUser(
			depotEntry.getGroupId(),
			DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR, unsafeConsumer);
	}

	public static void withAssetLibraryContentReviewer(
			DepotEntry depotEntry,
			UnsafeConsumer<User, Exception> unsafeConsumer)
		throws Exception {

		_withGroupUser(
			depotEntry.getGroupId(),
			DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER, unsafeConsumer);
	}

	public static void withAssetLibraryMember(
			DepotEntry depotEntry,
			UnsafeConsumer<User, Exception> unsafeConsumer)
		throws Exception {

		_withGroupUser(
			depotEntry.getGroupId(), DepotRolesConstants.ASSET_LIBRARY_MEMBER,
			unsafeConsumer);
	}

	public static void withAssetLibraryPermissions(
			DepotEntry depotEntry, String roleName, String resourceName,
			String actionId, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		withGroupPermissions(
			depotEntry.getGroup(), roleName, resourceName, actionId,
			unsafeRunnable);
	}

	public static void withDepotDisabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", false);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(_PID, dictionary)) {

			unsafeRunnable.run();
		}
	}

	public static void withDepotEnabled(
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("enabled", true);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(_PID, dictionary)) {

			unsafeRunnable.run();
		}
	}

	public static void withDepotUser(
			UnsafeBiConsumer<User, Role, Exception> unsafeBiConsumer)
		throws Exception {

		_withUser(unsafeBiConsumer, RoleConstants.TYPE_DEPOT);
	}

	public static void withGroupPermissions(
			Group group, String roleName, String resourceName, String actionId,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			group.getCompanyId(), roleName);

		RoleTestUtil.addResourcePermission(
			role, resourceName, ResourceConstants.SCOPE_GROUP,
			String.valueOf(group.getGroupId()), actionId);

		try {
			unsafeRunnable.run();
		}
		finally {
			RoleTestUtil.removeResourcePermission(
				roleName, resourceName, ResourceConstants.SCOPE_GROUP,
				String.valueOf(group.getGroupId()), actionId);
		}
	}

	public static void withRegularUser(
			UnsafeBiConsumer<User, Role, Exception> unsafeBiConsumer)
		throws Exception {

		_withUser(unsafeBiConsumer, RoleConstants.TYPE_REGULAR);
	}

	public static void withSiteMember(
			Group group, UnsafeConsumer<User, Exception> unsafeConsumer)
		throws Exception {

		_withGroupUser(
			group.getGroupId(), RoleConstants.SITE_MEMBER, unsafeConsumer);
	}

	private static boolean _isAsignableRole(String roleName) {
		if (roleName.equals(DepotRolesConstants.ASSET_LIBRARY_MEMBER) ||
			roleName.equals(RoleConstants.SITE_MEMBER)) {

			return false;
		}

		return true;
	}

	private static void _withGroupUser(
			long groupId, String roleName,
			UnsafeConsumer<User, Exception> unsafeConsumer)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		User user = UserTestUtil.addUser();

		if (_isAsignableRole(roleName)) {
			UserGroupRoleLocalServiceUtil.addUserGroupRoles(
				user.getUserId(), groupId, new long[] {role.getRoleId()});
		}

		UserLocalServiceUtil.addGroupUsers(
			groupId, new long[] {user.getUserId()});

		if (_isAsignableRole(roleName)) {
			UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			unsafeConsumer.accept(user);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			UserLocalServiceUtil.deleteUser(user);
		}
	}

	private static void _withUser(
			UnsafeBiConsumer<User, Role, Exception> unsafeBiConsumer,
			int roleType)
		throws Exception {

		Role role = RoleTestUtil.addRole(roleType);
		User user = UserTestUtil.addUser();

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(user));

			unsafeBiConsumer.accept(user, role);
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			RoleLocalServiceUtil.deleteRole(role);
			UserLocalServiceUtil.deleteUser(user);
		}
	}

	private static final String _PID =
		"com.liferay.depot.web.internal.configuration.FFDepotConfiguration";

}