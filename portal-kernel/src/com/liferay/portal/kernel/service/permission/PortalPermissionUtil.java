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

package com.liferay.portal.kernel.service.permission;

import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, String actionId)
		throws PrincipalException {

		getPortalPermission().check(permissionChecker, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, String actionId) {

		return getPortalPermission().contains(permissionChecker, actionId);
	}

	public static PortalPermission getPortalPermission() {
		return _portalPermission;
	}

	public void setPortalPermission(PortalPermission portalPermission) {
		_portalPermission = portalPermission;
	}

	private static PortalPermission _portalPermission;

}