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

package com.liferay.list.type.internal.security.permission.resource;

import com.liferay.list.type.constants.ListTypeConstants;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.list.type.service.ListTypeDefinitionLocalService;
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
	property = "model.class.name=com.liferay.list.type.model.ListTypeDefinition",
	service = ModelResourcePermission.class
)
public class ListTypeDefinitionModelResourcePermission
	implements ModelResourcePermission<ListTypeDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			ListTypeDefinition listTypeDefinition, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, listTypeDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ListTypeDefinition.class.getName(),
				listTypeDefinition.getPrimaryKey(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long listTypeDefinitionId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, listTypeDefinitionId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ListTypeDefinition.class.getName(),
				listTypeDefinitionId, actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			ListTypeDefinition listTypeDefinition, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(),
				ListTypeDefinition.class.getName(),
				listTypeDefinition.getListTypeDefinitionId(),
				listTypeDefinition.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				null, ListTypeDefinition.class.getName(),
				listTypeDefinition.getPrimaryKey(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long listTypeDefinitionId,
			String actionId)
		throws PortalException {

		ListTypeDefinition listTypeDefinition =
			_listTypeDefinitionLocalService.getListTypeDefinition(
				listTypeDefinitionId);

		return contains(permissionChecker, listTypeDefinition, actionId);
	}

	@Override
	public String getModelName() {
		return ListTypeDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private ListTypeDefinitionLocalService _listTypeDefinitionLocalService;

	@Reference(
		target = "(resource.name=" + ListTypeConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}