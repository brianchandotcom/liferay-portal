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

import com.liferay.data.engine.rest.internal.constants.DataLayoutConstants;
import com.liferay.data.engine.rest.internal.model.InternalDataLayout;
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
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "model.class.name=" + DataLayoutConstants.RESOURCE_NAME,
	service = ModelResourcePermission.class
)
public class InternalDataLayoutModelResourcePermission
	implements ModelResourcePermission<InternalDataLayout> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			InternalDataLayout internalDataLayout, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, internalDataLayout, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, DataLayoutConstants.RESOURCE_NAME,
				(long)internalDataLayout.getPrimaryKeyObj(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		InternalDataLayout internalDataLayout = new InternalDataLayout();

		internalDataLayout.setPrimaryKeyObj(primaryKey);

		check(permissionChecker, internalDataLayout, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			InternalDataLayout internalDataLayout, String actionId)
		throws PortalException {

		DDMStructureLayout ddmStructureLayout =
			ddmStructureLayoutLocalService.getStructureLayout(
				(long)internalDataLayout.getPrimaryKeyObj());

		if (permissionChecker.hasOwnerPermission(
				ddmStructureLayout.getCompanyId(),
				InternalDataLayout.class.getName(),
				(long)internalDataLayout.getPrimaryKeyObj(),
				ddmStructureLayout.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddmStructureLayout.getGroupId(), InternalDataLayout.class.getName(),
			(long)internalDataLayout.getPrimaryKeyObj(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		InternalDataLayout internalDataLayout = new InternalDataLayout();

		internalDataLayout.setPrimaryKeyObj(primaryKey);

		return contains(permissionChecker, internalDataLayout, actionId);
	}

	@Override
	public String getModelName() {
		return InternalDataLayout.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDMStructureLayoutLocalService ddmStructureLayoutLocalService;

}