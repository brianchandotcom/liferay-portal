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

package com.liferay.portal.security.permission;

import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Roberto Díaz
 */
public class ResourcePermissionCheckerUtil {

	public static Boolean containsResourcePermission(
		PermissionChecker permissionChecker, String className, long classPK,
		String actionId) {

		ResourcePermissionChecker resourcePermissionChecker =
			_serviceTrackerMap.getService(className);

		if (resourcePermissionChecker == null) {
			return null;
		}

		Boolean resource = resourcePermissionChecker.checkResource(
			permissionChecker, classPK, actionId);

		if (resource != null) {
			return resource.booleanValue();
		}

		return null;
	}

	private static final ServiceTrackerMap<String, ResourcePermissionChecker>
		_serviceTrackerMap = ServiceTrackerCollections.openSingleValueMap(
			ResourcePermissionChecker.class, "resource.name");

}