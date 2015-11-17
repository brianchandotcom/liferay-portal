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

package com.liferay.asset.categories.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {
		"model.class.name=com.liferay.portlet.asset.model.AssetCategory"
	},
	service = PermissionUpdateHandler.class
)
public class AssetCategoryPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		AssetCategory assetCategory =
			_assetCategoryLocalService.fetchAssetCategory(
				GetterUtil.getLong(primKey));

		if (assetCategory == null) {
			return;
		}

		assetCategory.setModifiedDate(new Date());

		_assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;

}