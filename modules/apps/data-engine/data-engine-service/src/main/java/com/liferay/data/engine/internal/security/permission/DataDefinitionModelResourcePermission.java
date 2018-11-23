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

import com.liferay.data.engine.constants.DataDefinitionConstants;
import com.liferay.data.engine.model.DataDefinition;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.Collections;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.data.engine.model.DataDefinition",
	service = ModelResourcePermission.class
)
public class DataDefinitionModelResourcePermission
	implements ModelResourcePermission<DataDefinition> {

	@Override
	public void check(
			PermissionChecker permissionChecker, DataDefinition dataDefinition,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, dataDefinition, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DataDefinitionConstants.MODEL_RESOURCE_NAME,
				dataDefinition.getDataDefinitionId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Collections.emptyList()
		).dataDefinitionId(
			primaryKey
		).build();

		check(permissionChecker, dataDefinition, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, DataDefinition dataDefinition,
			String actionId)
		throws PortalException {

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			dataDefinition.getDataDefinitionId());

		if (permissionChecker.hasOwnerPermission(
				ddmStructure.getCompanyId(), DataDefinition.class.getName(),
				dataDefinition.getDataDefinitionId(), ddmStructure.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddmStructure.getGroupId(), DataDefinition.class.getName(),
			dataDefinition.getDataDefinitionId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DataDefinition dataDefinition = DataDefinition.Builder.newBuilder(
			Collections.emptyList()
		).dataDefinitionId(
			primaryKey
		).build();

		return contains(permissionChecker, dataDefinition, actionId);
	}

	@Override
	public String getModelName() {
		return DataDefinition.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

}