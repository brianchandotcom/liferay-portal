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

package com.liferay.data.engine.rest.internal.security.permission;

import com.liferay.data.engine.rest.internal.constants.DataDefinitionConstants;
import com.liferay.data.engine.rest.internal.model.InternalDataDefinition;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "model.class.name=" + DataDefinitionConstants.RESOURCE_NAME,
	service = ModelResourcePermission.class
)
public class InternalDataDefinitionModelResourcePermission
	implements ModelResourcePermission<InternalDataDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			InternalDataDefinition internalDataDefinition, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, internalDataDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DataDefinitionConstants.RESOURCE_NAME,
				(long)internalDataDefinition.getPrimaryKeyObj(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		InternalDataDefinition internalDataDefinition =
			new InternalDataDefinition();

		internalDataDefinition.setPrimaryKeyObj(primaryKey);

		check(permissionChecker, internalDataDefinition, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			InternalDataDefinition internalDataDefinition, String actionId)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			(long)internalDataDefinition.getPrimaryKeyObj());

		if (permissionChecker.hasOwnerPermission(
				ddmStructure.getCompanyId(),
				InternalDataDefinition.class.getName(),
				(long)internalDataDefinition.getPrimaryKeyObj(),
				ddmStructure.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddmStructure.getGroupId(), InternalDataDefinition.class.getName(),
			(long)internalDataDefinition.getPrimaryKeyObj(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		InternalDataDefinition internalDataDefinition =
			new InternalDataDefinition();

		internalDataDefinition.setPrimaryKeyObj(primaryKey);

		return contains(permissionChecker, internalDataDefinition, actionId);
	}

	@Override
	public String getModelName() {
		return InternalDataDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

}