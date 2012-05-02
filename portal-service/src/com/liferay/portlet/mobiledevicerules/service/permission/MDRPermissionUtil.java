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

package com.liferay.portlet.mobiledevicerules.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Edward Han
 */
public class MDRPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long groupId, String actionId)
		throws PortalException {

		getMDRPermission().check(permissionChecker, groupId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long groupId, String actionId) {

		return getMDRPermission().contains(
			permissionChecker, groupId, actionId);
	}

	public static MDRPermission getMDRPermission() {
		return _mdrPermission;
	}

	public void setMDRPermission(MDRPermission mdrPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_mdrPermission = mdrPermission;
	}

	private static MDRPermission _mdrPermission;

}