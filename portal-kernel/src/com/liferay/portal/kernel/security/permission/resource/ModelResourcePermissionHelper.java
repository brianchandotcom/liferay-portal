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

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionHelper {

	public static void check(
			ModelResourcePermission<?> modelResourcePermission,
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		if (primaryKey == _DEFAULT_PARENT_PRIMARY_KEY) {
			PortletResourcePermission portletResourcePermission =
				modelResourcePermission.getPortletResourcePermission();

			portletResourcePermission.check(
				permissionChecker, groupId, actionId);
		}
		else {
			modelResourcePermission.check(
				permissionChecker, primaryKey, actionId);
		}
	}

	public static boolean contains(
			ModelResourcePermission<?> modelResourcePermission,
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		if (primaryKey == _DEFAULT_PARENT_PRIMARY_KEY) {
			PortletResourcePermission portletResourcePermission =
				modelResourcePermission.getPortletResourcePermission();

			return portletResourcePermission.contains(
				permissionChecker, groupId, actionId);
		}

		return modelResourcePermission.contains(
			permissionChecker, primaryKey, actionId);
	}

	private static final long _DEFAULT_PARENT_PRIMARY_KEY = 0;

}