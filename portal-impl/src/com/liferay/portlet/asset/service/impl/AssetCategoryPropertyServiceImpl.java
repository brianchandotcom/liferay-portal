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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.base.AssetCategoryPropertyServiceBaseImpl;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetCategoryPropertyServiceImpl
	extends AssetCategoryPropertyServiceBaseImpl {

	@Override
	public AssetCategoryProperty addCategoryProperty(
			long entryId, String key, String value)
		throws PortalException {

		AssetCategoryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return assetCategoryPropertyLocalService.addCategoryProperty(
			getUserId(), entryId, key, value);
	}

	@Override
	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException {

		AssetCategoryProperty assetCategoryProperty =
			assetCategoryPropertyLocalService.getAssetCategoryProperty(
				categoryPropertyId);

		AssetCategoryPermission.check(
			getPermissionChecker(), assetCategoryProperty.getCategoryId(),
			ActionKeys.UPDATE);

		assetCategoryPropertyLocalService.deleteCategoryProperty(
			categoryPropertyId);
	}

	@Override
	public List<AssetCategoryProperty> getCategoryProperties(long entryId) {
		return assetCategoryPropertyLocalService.getCategoryProperties(entryId);
	}

	@Override
	public List<AssetCategoryProperty> getCategoryPropertyValues(
		long companyId, String key) {

		return assetCategoryPropertyLocalService.getCategoryPropertyValues(
			companyId, key);
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long userId, long categoryPropertyId, String key, String value)
		throws PortalException {

		AssetCategoryProperty assetCategoryProperty =
			assetCategoryPropertyLocalService.getAssetCategoryProperty(
				categoryPropertyId);

		AssetCategoryPermission.check(
			getPermissionChecker(), assetCategoryProperty.getCategoryId(),
			ActionKeys.UPDATE);

		return assetCategoryPropertyLocalService.updateCategoryProperty(
			userId, categoryPropertyId, key, value);
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException {

		return updateCategoryProperty(0, categoryPropertyId, key, value);
	}

}