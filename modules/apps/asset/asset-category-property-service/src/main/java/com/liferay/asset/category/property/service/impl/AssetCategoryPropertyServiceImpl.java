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

package com.liferay.asset.category.property.service.impl;

import com.liferay.asset.category.property.model.AssetCategoryProperty;
import com.liferay.asset.category.property.service.base.AssetCategoryPropertyServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
@Component(
	property = {
		"json.web.service.context.name=asset",
		"json.web.service.context.path=AssetCategoryProperty"
	},
	service = AopService.class
)
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
		try {
			if (AssetCategoryPermission.contains(
					getPermissionChecker(), entryId, ActionKeys.VIEW)) {

				return assetCategoryPropertyLocalService.getCategoryProperties(
					entryId);
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Unable to get asset category property for asset entry " +
						entryId,
					pe);
			}
		}

		return new ArrayList<>();
	}

	@Override
	public List<AssetCategoryProperty> getCategoryPropertyValues(
		long companyId, String key) {

		return filterAssetCategoryProperties(
			assetCategoryPropertyLocalService.getCategoryPropertyValues(
				companyId, key));
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

	protected List<AssetCategoryProperty> filterAssetCategoryProperties(
		List<AssetCategoryProperty> assetCategoryProperties) {

		List<AssetCategoryProperty> filteredAssetCategoryProperties =
			new ArrayList<>(assetCategoryProperties.size());

		for (AssetCategoryProperty assetCategoryProperty :
				assetCategoryProperties) {

			try {
				if (AssetCategoryPermission.contains(
						getPermissionChecker(),
						assetCategoryProperty.getCategoryId(),
						ActionKeys.VIEW)) {

					filteredAssetCategoryProperties.add(assetCategoryProperty);
				}
			}
			catch (PortalException pe) {

				// LPS-52675

				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return filteredAssetCategoryProperties;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryPropertyServiceImpl.class);

}