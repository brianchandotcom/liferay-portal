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

package com.liferay.data.engine.internal.service;

import com.liferay.data.engine.internal.security.permission.DEDataEnginePermissionSupport;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

/**
 * @author Leonardo Barros
 */
public abstract class DEBaseServiceImpl {

	protected void checkPermission(
			long classPK, String actionId, PermissionChecker permissionChecker)
		throws PortalException {

		String resourceName = DEDataEnginePermissionSupport.RESOURCE_NAME;

		DEDataEnginePermissionSupport deDataEnginePermissionSupport =
			getDEDataEnginePermissionSupport();

		if (!deDataEnginePermissionSupport.contains(
				permissionChecker, resourceName, classPK, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, resourceName, classPK, actionId);
		}
	}

	protected abstract DEDataEnginePermissionSupport
		getDEDataEnginePermissionSupport();

	protected PermissionChecker getPermissionChecker()
		throws PrincipalException {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			throw new PrincipalException(
				"Permission checker is not initialized");
		}

		return permissionChecker;
	}

}