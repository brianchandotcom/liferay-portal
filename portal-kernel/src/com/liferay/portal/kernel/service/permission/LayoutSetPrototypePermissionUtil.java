/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_layoutSetPrototypePermission.check(
			permissionChecker, layoutSetPrototypeId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long layoutSetPrototypeId,
		String actionId) {

		return _layoutSetPrototypePermission.contains(
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