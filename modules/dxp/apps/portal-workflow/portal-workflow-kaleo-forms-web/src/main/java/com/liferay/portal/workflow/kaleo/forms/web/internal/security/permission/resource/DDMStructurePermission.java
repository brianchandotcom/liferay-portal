/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.forms.web.internal.security.permission.resource;

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
			PermissionChecker permissionChecker, DDMStructure structure,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMStructure> modelResourcePermission =
			_ddmStructureModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, structure, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long structureId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<DDMStructure> modelResourcePermission =
			_ddmStructureModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, structureId, actionId);
	}

	public static boolean containsAddStructurePermission(
			PermissionChecker permissionChecker, long groupId, long classNameId)
		throws PortalException {

		DDMPermissionSupport ddmPermissionSupport =
			_ddmPermissionSupportSnapshot.get();

		return ddmPermissionSupport.containsAddStructurePermission(
			permissionChecker, groupId, classNameId);
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