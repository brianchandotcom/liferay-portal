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

package com.liferay.data.engine.internal.security.permission;

import com.liferay.data.engine.constants.DEDataDefinitionConstants;
import com.liferay.data.engine.model.DEDataDefinition;
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
	property = "model.class.name=com.liferay.data.engine.model.DEDataDefinition",
	service = ModelResourcePermission.class
)
public class DEDataDefinitionModelResourcePermission
	implements ModelResourcePermission<DEDataDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			DEDataDefinition deDataDefinition, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, deDataDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				DEDataDefinitionConstants.MODEL_RESOURCE_NAME,
				deDataDefinition.getDEDataDefinitionId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DEDataDefinition deDataDefinition = new DEDataDefinition();

		deDataDefinition.setDEDataDefinitionId(primaryKey);

		check(permissionChecker, deDataDefinition, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			DEDataDefinition deDataDefinition, String actionId)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			deDataDefinition.getDEDataDefinitionId());

		if (permissionChecker.hasOwnerPermission(
				ddmStructure.getCompanyId(), DEDataDefinition.class.getName(),
				deDataDefinition.getDEDataDefinitionId(),
				ddmStructure.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddmStructure.getGroupId(), DEDataDefinition.class.getName(),
			deDataDefinition.getDEDataDefinitionId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DEDataDefinition deDataDefinition = new DEDataDefinition();

		deDataDefinition.setDEDataDefinitionId(primaryKey);

		return contains(permissionChecker, deDataDefinition, actionId);
	}

	@Override
	public String getModelName() {
		return DEDataDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

}