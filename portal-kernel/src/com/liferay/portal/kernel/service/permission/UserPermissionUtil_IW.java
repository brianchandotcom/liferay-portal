/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class UserPermissionUtil_IW {
	public static UserPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long userId, long[] organizationIds, java.lang.String actionId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		UserPermissionUtil.check(permissionChecker, userId, organizationIds,
			actionId);
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long userId, java.lang.String actionId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		UserPermissionUtil.check(permissionChecker, userId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long userId, long[] organizationIds, java.lang.String actionId) {
		return UserPermissionUtil.contains(permissionChecker, userId,
			organizationIds, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long userId, java.lang.String actionId) {
		return UserPermissionUtil.contains(permissionChecker, userId, actionId);
	}

	private UserPermissionUtil_IW() {
	}

	private static UserPermissionUtil_IW _instance = new UserPermissionUtil_IW();
}