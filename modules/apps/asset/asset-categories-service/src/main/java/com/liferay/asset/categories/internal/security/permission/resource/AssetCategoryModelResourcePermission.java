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

package com.liferay.asset.categories.internal.security.permission.resource;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = ModelResourcePermission.class
)
public class AssetCategoryModelResourcePermission
	implements ModelResourcePermission<AssetCategory> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AssetCategory assetCategory,
			String actionId)
		throws PortalException {

		AssetCategoryPermission.check(
			permissionChecker, assetCategory, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		AssetCategoryPermission.check(permissionChecker, categoryId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, AssetCategory assetCategory,
			String actionId)
		throws PortalException {

		return AssetCategoryPermission.contains(
			permissionChecker, assetCategory, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long categoryId,
			String actionId)
		throws PortalException {

		return AssetCategoryPermission.contains(
			permissionChecker, categoryId, actionId);
	}

	@Override
	public String getModelName() {
		return AssetCategory.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

}