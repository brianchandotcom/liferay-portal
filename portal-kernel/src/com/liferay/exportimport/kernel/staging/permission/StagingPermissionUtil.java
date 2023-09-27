/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.staging.permission;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Jorge Ferrer
 */
public class StagingPermissionUtil {

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		return _stagingPermission.hasPermission(
			permissionChecker, group, className, classPK, portletId, actionId);
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		return _stagingPermission.hasPermission(
			permissionChecker, groupId, className, classPK, portletId,
			actionId);
	}

	private static volatile StagingPermission _stagingPermission =
		ServiceProxyFactory.newServiceTrackedInstance(
			StagingPermission.class, StagingPermissionUtil.class,
			"_stagingPermission", false);

}