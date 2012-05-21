/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.staging.permission;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Jorge Ferrer
 */
public class StagingPermissionUtil {

	public static StagingPermission getStagingPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			StagingPermissionUtil.class);

		return _stagingPermission;
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		return getStagingPermission().hasPermission(
			permissionChecker, group, className, classPK, portletId, actionId);
	}

	public static Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		return getStagingPermission().hasPermission(
			permissionChecker, groupId, className, classPK, portletId,
			actionId);
	}

	public void setStagingPermission(StagingPermission stagingPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_stagingPermission = stagingPermission;
	}

	private static StagingPermission _stagingPermission;

}