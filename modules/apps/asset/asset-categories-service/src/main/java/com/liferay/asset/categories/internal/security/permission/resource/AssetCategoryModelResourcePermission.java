/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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