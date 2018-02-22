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

package com.liferay.dynamic.data.mapping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseResourcePermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.ResourcePermissionChecker;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 * @deprecated As of 3.0.0, replaced by {@link DDMFormPermission}
 */
@Component(
	immediate = true,
	property = {"resource.name=" + DDMFormPermission.RESOURCE_NAME},
	service = ResourcePermissionChecker.class
)
@Deprecated
public class DDMDataProviderPermission extends BaseResourcePermissionChecker {

	public static final String RESOURCE_NAME = DDMFormPermission.RESOURCE_NAME;

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		DDMFormPermission.check(permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return DDMFormPermission.contains(permissionChecker, groupId, actionId);
	}

	@Override
	public Boolean checkResource(
		PermissionChecker permissionChecker, long classPK, String actionId) {

		return DDMFormPermission.contains(permissionChecker, classPK, actionId);
	}

}