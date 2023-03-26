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

package com.liferay.document.library.web.internal.security.permission.resource;

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
			PermissionChecker permissionChecker,
			com.liferay.dynamic.data.mapping.kernel.DDMStructure ddmStructure,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMStructure> modelResourcePermission =
			_ddmStructureModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ddmStructure.getPrimaryKey(), actionId);
	}

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