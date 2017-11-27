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

import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Roberto Díaz
 */
public class ResourcePermissionCheckerUtil {

	public static Boolean containsResourcePermission(
		PermissionChecker permissionChecker, String className, long classPK,
		String actionId) {

		PortletResourcePermission portletResourcePermission =
			_portletPermissions.getService(className);

		if (portletResourcePermission != null) {
			return portletResourcePermission.contains(
				permissionChecker, classPK, actionId);
		}

		ResourcePermissionChecker resourcePermissionChecker =
			_resourcePermissionCheckers.getService(className);

		if (resourcePermissionChecker == null) {
			return null;
		}

		return resourcePermissionChecker.checkResource(
			permissionChecker, classPK, actionId);
	}

	private static final ServiceTrackerMap<String, PortletResourcePermission>
		_portletPermissions = ServiceTrackerCollections.openSingleValueMap(
			PortletResourcePermission.class, "resource.name");
	private static final ServiceTrackerMap<String, ResourcePermissionChecker>
		_resourcePermissionCheckers =
			ServiceTrackerCollections.openSingleValueMap(
				ResourcePermissionChecker.class, "resource.name");

}