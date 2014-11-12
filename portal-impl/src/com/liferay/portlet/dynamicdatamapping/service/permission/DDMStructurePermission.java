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

package com.liferay.portlet.dynamicdatamapping.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

/**
 * @author Bruno Basto
 */
public class DDMStructurePermission {

	public static void check(
			PermissionChecker permissionChecker, DDMStructure structure,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, structure, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String structureKey, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, groupId, classNameId, structureKey,
				actionId)) {

			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long structureId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, structureId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDMStructure structure,
		String actionId) {

		return contains(permissionChecker, structure, null, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, DDMStructure structure,
		String portletId, String actionId) {

		if (Validator.isNotNull(portletId)) {
			Boolean hasPermission = StagingPermissionUtil.hasPermission(
				permissionChecker, structure.getGroupId(),
				DDMStructure.class.getName(), structure.getStructureId(),
				portletId, actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (permissionChecker.hasOwnerPermission(
				structure.getCompanyId(), DDMStructure.class.getName(),
				structure.getStructureId(), structure.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			structure.getGroupId(), DDMStructure.class.getName(),
			structure.getStructureId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, long classNameId,
			String structureKey, String actionId)
		throws PortalException {

		DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
			groupId, classNameId, structureKey, true);

		return contains(permissionChecker, structure, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long structureId,
			String actionId)
		throws PortalException {

		return contains(permissionChecker, structureId, null, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long structureId,
			String portletId, String actionId)
		throws PortalException {

		DDMStructure structure = DDMStructureLocalServiceUtil.getStructure(
			structureId);

		return contains(permissionChecker, structure, portletId, actionId);
	}

}