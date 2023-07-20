/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.service.permission;

import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Michael C. Han
 */
public class ExpandoColumnPermissionUtil {

	public static void check(
			PermissionChecker permissionChecker, ExpandoColumn column,
			String actionId)
		throws PortalException {

		_expandoColumnPermission.check(permissionChecker, column, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException {

		_expandoColumnPermission.check(permissionChecker, columnId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, ExpandoColumn column,
		String actionId) {

		return _expandoColumnPermission.contains(
			permissionChecker, column, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long companyId, String className,
		String tableName, String columnName, String actionId) {

		return _expandoColumnPermission.contains(
			permissionChecker, companyId, className, tableName, columnName,
			actionId);
	}

	public static ExpandoColumnPermission getExpandoColumnPermission() {
		return _expandoColumnPermission;
	}

	public void setExpandoColumnPermission(
		ExpandoColumnPermission expandoColumnPermission) {

		_expandoColumnPermission = expandoColumnPermission;
	}

	private static ExpandoColumnPermission _expandoColumnPermission;

}