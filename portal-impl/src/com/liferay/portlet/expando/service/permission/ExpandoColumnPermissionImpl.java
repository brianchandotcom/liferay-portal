/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;

/**
 * @author Raymond Augé
 */
public class ExpandoColumnPermissionImpl implements ExpandoColumnPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, ExpandoColumn column,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, column, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, columnId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long companyId,
			String className, String tableName, String columnName,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, companyId, className, tableName, columnName,
				actionId)) {

			throw new PrincipalException();
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, ExpandoColumn column,
		String actionId) {

		return permissionChecker.hasPermission(
			0, ExpandoColumn.class.getName(), column.getColumnId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long columnId, String actionId)
		throws PortalException, SystemException {

		ExpandoColumn column = ExpandoColumnLocalServiceUtil.getColumn(
			columnId);

		return contains(permissionChecker, column, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long companyId,
			String className, String tableName, String columnName,
			String actionId)
		throws SystemException {

		ExpandoColumn column = ExpandoColumnLocalServiceUtil.getColumn(
			companyId, className, tableName, columnName);

		return contains(permissionChecker, column, actionId);
	}

}