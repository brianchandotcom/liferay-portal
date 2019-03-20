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

import com.liferay.data.engine.constants.DEDataLayoutConstants;
import com.liferay.data.engine.model.DEDataLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.data.engine.model.DEDataLayout",
	service = ModelResourcePermission.class
)
public class DEDataLayoutModelResourcePermission
	implements ModelResourcePermission<DEDataLayout> {

	@Override
	public void check(
			PermissionChecker permissionChecker, DEDataLayout deDataLayout,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, deDataLayout, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DEDataLayoutConstants.MODEL_RESOURCE_NAME,
				deDataLayout.getDEDataLayoutId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DEDataLayout deDataLayout = new DEDataLayout();

		deDataLayout.setDEDataLayoutId(primaryKey);

		check(permissionChecker, deDataLayout, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, DEDataLayout deDataLayout,
			String actionId)
		throws PortalException {

		DDMStructureLayout ddmStructureLayout =
			ddmStructureLayoutLocalService.getStructureLayout(
				deDataLayout.getDEDataLayoutId());

		if (permissionChecker.hasOwnerPermission(
				ddmStructureLayout.getCompanyId(),
				DEDataLayoutConstants.MODEL_RESOURCE_NAME,
				deDataLayout.getDEDataLayoutId(),
				ddmStructureLayout.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddmStructureLayout.getGroupId(),
			DEDataLayoutConstants.MODEL_RESOURCE_NAME,
			deDataLayout.getDEDataLayoutId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DEDataLayout deDataLayout = new DEDataLayout();

		deDataLayout.setDEDataLayoutId(primaryKey);

		return contains(permissionChecker, deDataLayout, actionId);
	}

	@Override
	public String getModelName() {
		return DEDataLayoutConstants.MODEL_RESOURCE_NAME;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDMStructureLayoutLocalService ddmStructureLayoutLocalService;

}