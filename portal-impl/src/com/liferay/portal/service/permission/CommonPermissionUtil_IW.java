/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.permission;

/**
 * @author Brian Wing Shun Chan
 */
public class CommonPermissionUtil_IW {
	public static CommonPermissionUtil_IW getInstance() {
		return _instance;
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		long classNameId, long classPK, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		CommonPermissionUtil.check(permissionChecker, classNameId, classPK,
			actionId);
	}

	public void check(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker,
		java.lang.String className, long classPK, java.lang.String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		CommonPermissionUtil.check(permissionChecker, className, classPK,
			actionId);
	}

	private CommonPermissionUtil_IW() {
	}

	private static CommonPermissionUtil_IW _instance = new CommonPermissionUtil_IW();
}