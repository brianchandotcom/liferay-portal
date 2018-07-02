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