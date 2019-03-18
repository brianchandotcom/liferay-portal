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

package com.liferay.portal.workflow.kaleo.designer.web.internal.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = {})
public class KaleoDefinitionVersionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker,
			KaleoDefinitionVersion kaleoDefinitionVersion, String actionId)
		throws PortalException {

		return _kaleoDefinitionVersionModelResourcePermission.contains(
			permissionChecker, kaleoDefinitionVersion, actionId);
	}

	public static boolean hasViewPermission(
			PermissionChecker permissionChecker,
			KaleoDefinitionVersion kaleoDefinitionVersion, long companyGroupId)
		throws PortalException {

		if (contains(
				permissionChecker, kaleoDefinitionVersion, ActionKeys.DELETE) ||
			contains(
				permissionChecker, kaleoDefinitionVersion,
				ActionKeys.PERMISSIONS) ||
			contains(
				permissionChecker, kaleoDefinitionVersion, ActionKeys.UPDATE) ||
			contains(
				permissionChecker, kaleoDefinitionVersion, ActionKeys.VIEW) ||
			KaleoDesignerPermission.contains(
				permissionChecker, companyGroupId, ActionKeys.VIEW)) {

			return true;
		}

		return false;
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion)",
		unbind = "-"
	)
	protected void setModelPermissionChecker(
		ModelResourcePermission<KaleoDefinitionVersion>
			modelResourcePermission) {

		_kaleoDefinitionVersionModelResourcePermission =
			modelResourcePermission;
	}

	private static ModelResourcePermission<KaleoDefinitionVersion>
		_kaleoDefinitionVersionModelResourcePermission;

}