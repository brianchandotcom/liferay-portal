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
	immediate = true,
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

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectDefinitionId);

		return contains(permissionChecker, objectDefinition, actionId);
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