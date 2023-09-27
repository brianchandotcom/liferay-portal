/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.security.permission.resource;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.security.permission.DDMPermissionSupport;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Rafael Praxedes
 */
public class DDMStructurePermission {

	public static boolean contains(
			PermissionChecker permissionChecker, DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMStructure> modelResourcePermission =
			_ddmStructureModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmStructure, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ddmStructureId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMStructure> modelResourcePermission =
			_ddmStructureModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmStructureId, actionId);
	}

	public static boolean containsAddDDMStructurePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException {

		DDMPermissionSupport ddmPermissionSupport =
			_ddmPermissionSupportSnapshot.get();

		return ddmPermissionSupport.containsAddStructurePermission(
			permissionChecker, groupId, classNameId);
	}

	public static String getStructureModelResourceName(long classNameId)
		throws PortalException {

		DDMPermissionSupport ddmPermissionSupport =
			_ddmPermissionSupportSnapshot.get();

		return ddmPermissionSupport.getStructureModelResourceName(classNameId);
	}

	private static final Snapshot<DDMPermissionSupport>
		_ddmPermissionSupportSnapshot = new Snapshot<>(
			DDMStructurePermission.class, DDMPermissionSupport.class);
	private static final Snapshot<ModelResourcePermission<DDMStructure>>
		_ddmStructureModelResourcePermissionSnapshot = new Snapshot<>(
			DDMStructurePermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.dynamic.data.mapping.model." +
				"DDMStructure)");

}