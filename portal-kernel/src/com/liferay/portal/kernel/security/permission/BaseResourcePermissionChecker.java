/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author     Preston Crary
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public abstract class BaseResourcePermissionChecker
	implements ResourcePermissionChecker {

	public static boolean contains(
		PermissionChecker permissionChecker, String name, long classPK,
		String actionId) {

		Group group = GroupLocalServiceUtil.fetchGroup(classPK);

		if ((group != null) && group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		return permissionChecker.hasPermission(group, name, classPK, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String name, String portletId,
		long classPK, String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, classPK, name, classPK, portletId, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return contains(permissionChecker, name, classPK, actionId);
	}

}