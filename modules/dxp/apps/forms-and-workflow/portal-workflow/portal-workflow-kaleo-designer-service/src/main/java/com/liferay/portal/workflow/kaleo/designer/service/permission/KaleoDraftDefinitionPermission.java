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

package com.liferay.portal.workflow.kaleo.designer.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.workflow.kaleo.designer.model.KaleoDraftDefinition;
import com.liferay.portal.workflow.kaleo.designer.service.KaleoDraftDefinitionLocalServiceUtil;

/**
 * @author Marcellus Tavares
 */
public class KaleoDraftDefinitionPermission {

	public static void check(
			PermissionChecker permissionChecker,
			KaleoDraftDefinition kaleoDraftDefinition, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoDraftDefinition, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long kaleoDraftDefinitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, kaleoDraftDefinitionId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker,
		KaleoDraftDefinition kaleoDraftDefinition, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				kaleoDraftDefinition.getCompanyId(),
				KaleoDraftDefinition.class.getName(),
				kaleoDraftDefinition.getKaleoDraftDefinitionId(),
				kaleoDraftDefinition.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			kaleoDraftDefinition.getGroupId(),
			KaleoDraftDefinition.class.getName(),
			kaleoDraftDefinition.getKaleoDraftDefinitionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long kaleoDraftDefinitionId,
			String actionId)
		throws PortalException {

		KaleoDraftDefinition kaleoDraftDefinition =
			KaleoDraftDefinitionLocalServiceUtil.getKaleoDraftDefinition(
				kaleoDraftDefinitionId);

		return contains(permissionChecker, kaleoDraftDefinition, actionId);
	}

}