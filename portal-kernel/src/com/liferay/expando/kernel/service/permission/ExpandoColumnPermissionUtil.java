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

		getExpandoColumnPermission().check(permissionChecker, column, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException {

		getExpandoColumnPermission().check(
			permissionChecker, columnId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long companyId,
			String className, String tableName, String columnName,
			String actionId)
		throws PortalException {

		getExpandoColumnPermission().check(
			permissionChecker, companyId, className, tableName, columnName,
			actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, ExpandoColumn column,
		String actionId) {

		return getExpandoColumnPermission().contains(
			permissionChecker, column, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException {

		return getExpandoColumnPermission().contains(
			permissionChecker, columnId, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, long companyId, String className,
		String tableName, String columnName, String actionId) {

		return getExpandoColumnPermission().contains(
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