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

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;

/**
 * @author Jorge Ferrer
 */
public class LayoutPrototypePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long layoutPrototypeId,
			String actionId)
		throws PrincipalException {

		getLayoutPrototypePermission().check(
			permissionChecker, layoutPrototypeId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long layoutPrototypeId,
		String actionId) {

		return getLayoutPrototypePermission().contains(
			permissionChecker, layoutPrototypeId, actionId);
	}

	public static LayoutPrototypePermission getLayoutPrototypePermission() {
		PortalRuntimePermission.checkGetBeanProperty(
			LayoutPrototypePermissionUtil.class);

		return _layoutPrototypePermission;
	}

	public void setLayoutPrototypePermission(
		LayoutPrototypePermission layoutPrototypePermission) {

		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_layoutPrototypePermission = layoutPrototypePermission;
	}

	private static LayoutPrototypePermission _layoutPrototypePermission;

}