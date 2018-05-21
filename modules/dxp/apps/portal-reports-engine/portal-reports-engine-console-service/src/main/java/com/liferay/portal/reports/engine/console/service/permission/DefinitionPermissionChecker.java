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

package com.liferay.portal.reports.engine.console.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.reports.engine.console.model.Definition;
import com.liferay.portal.reports.engine.console.service.DefinitionLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.portal.reports.engine.console.model.Definition",
	service = BaseModelPermissionChecker.class
)
public class DefinitionPermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, Definition definition,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, definition, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long definitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, definitionId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, Definition definition,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				definition.getCompanyId(), Definition.class.getName(),
				definition.getDefinitionId(), definition.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			definition.getGroupId(), Definition.class.getName(),
			definition.getDefinitionId(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long definitionId,
			String actionId)
		throws PortalException {

		Definition definition = _definitionLocalService.getDefinition(
			definitionId);

		return contains(permissionChecker, definition, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setDefinitionLocalService(
		DefinitionLocalService definitionLocalService) {

		_definitionLocalService = definitionLocalService;
	}

	private static DefinitionLocalService _definitionLocalService;

}