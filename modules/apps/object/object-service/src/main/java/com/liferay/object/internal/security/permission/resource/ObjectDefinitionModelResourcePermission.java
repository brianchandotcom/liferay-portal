/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.security.permission.resource;

import com.liferay.object.constants.ObjectConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "model.class.name=com.liferay.object.model.ObjectDefinition",
	service = ModelResourcePermission.class
)
public class ObjectDefinitionModelResourcePermission
	implements ModelResourcePermission<ObjectDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long objectDefinitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectDefinitionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ObjectDefinition.class.getName(),
				objectDefinitionId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			ObjectDefinition objectDefinition, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ObjectDefinition.class.getName(),
				objectDefinition.getPrimaryKey(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long objectDefinitionId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			ObjectDefinition objectDefinition, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				ObjectDefinition.class.getName(),
				objectDefinition.getObjectDefinitionId(),
				objectDefinition.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				null, ObjectDefinition.class.getName(),
				objectDefinition.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return ObjectDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference(target = "(resource.name=" + ObjectConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}