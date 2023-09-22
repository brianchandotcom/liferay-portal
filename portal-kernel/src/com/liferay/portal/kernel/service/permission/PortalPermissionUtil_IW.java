/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalPermissionUtil_IW {
	public static PortalPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String actionId)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {
		PortalPermissionUtil.check(permissionChecker, actionId);
	}

	public boolean contains(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String actionId) {
		return PortalPermissionUtil.contains(permissionChecker, actionId);
	}

	private PortalPermissionUtil_IW() {
	}

	private static PortalPermissionUtil_IW _instance = new PortalPermissionUtil_IW();
}