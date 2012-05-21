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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class CommonPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long classNameId, long classPK,
			String actionId)
		throws PortalException, SystemException {

		getCommonPermission().check(
			permissionChecker, classNameId, classPK, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException, SystemException {

		getCommonPermission().check(
			permissionChecker, className, classPK, actionId);
	}

	public static CommonPermission getCommonPermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			CommonPermissionUtil.class);

		return _commonPermission;
	}

	public void setCommonPermission(CommonPermission commonPermission) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_commonPermission = commonPermission;
	}

	private static CommonPermission _commonPermission;

}