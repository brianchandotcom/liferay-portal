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

package com.liferay.portal.workflow.kaleo.designer.web.internal.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;

/**
 * @author Marcellus Tavares
 */
public class KaleoDefinitionVersionPermission {

	public static void check(
			PermissionChecker permissionChecker,
			KaleoDefinitionVersion kaleoDefinitionVersion, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoDefinitionVersion, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long kaleoDefinitionVersionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoDefinitionVersionId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		KaleoDefinitionVersion kaleoDefinitionVersion, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				kaleoDefinitionVersion.getCompanyId(),
				KaleoDefinitionVersion.class.getName(),
				kaleoDefinitionVersion.getKaleoDefinitionVersionId(),
				kaleoDefinitionVersion.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			kaleoDefinitionVersion.getGroupId(),
			KaleoDefinitionVersion.class.getName(),
			kaleoDefinitionVersion.getKaleoDefinitionVersionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long kaleoDefinitionVersionId,
			String actionId)
		throws PortalException {

		KaleoDefinitionVersion kaleoDefinitionVersion =
			KaleoDefinitionVersionLocalServiceUtil.getKaleoDefinitionVersion(
				kaleoDefinitionVersionId);

		return contains(permissionChecker, kaleoDefinitionVersion, actionId);
	}

}