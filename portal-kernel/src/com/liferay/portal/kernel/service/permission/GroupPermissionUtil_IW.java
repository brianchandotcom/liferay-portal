/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class GroupPermissionUtil_IW {
	public static GroupPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		com.liferay.portal.kernel.model.Group group, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		GroupPermissionUtil.check(permissionChecker, group, actionId);
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long groupId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		GroupPermissionUtil.check(permissionChecker, groupId, actionId);
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		GroupPermissionUtil.check(permissionChecker, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		com.liferay.portal.kernel.model.Group group, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return GroupPermissionUtil.contains(permissionChecker, group, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long groupId, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return GroupPermissionUtil.contains(permissionChecker, groupId, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String actionId) {
		return GroupPermissionUtil.contains(permissionChecker, actionId);
	}

	private GroupPermissionUtil_IW() {
	}

	private static GroupPermissionUtil_IW _instance = new GroupPermissionUtil_IW();
}