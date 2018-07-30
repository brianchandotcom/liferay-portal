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
public class LayoutSetPrototypePermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, long layoutSetPrototypeId,
			String actionId)
		throws PrincipalException {

		getLayoutSetPrototypePermission().check(
			permissionChecker, layoutSetPrototypeId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long layoutSetPrototypeId,
		String actionId) {

		return getLayoutSetPrototypePermission().contains(
			permissionChecker, layoutSetPrototypeId, actionId);
	}

	public static LayoutSetPrototypePermission
		getLayoutSetPrototypePermission() {

		return _layoutSetPrototypePermission;
	}

	public void setLayoutSetPrototypePermission(
		LayoutSetPrototypePermission layoutSetPrototypePermission) {

		_layoutSetPrototypePermission = layoutSetPrototypePermission;
	}

	private static LayoutSetPrototypePermission _layoutSetPrototypePermission;

}