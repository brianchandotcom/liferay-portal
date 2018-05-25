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

package com.liferay.asset.category.property.internal.service;

import com.liferay.asset.category.property.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.asset.kernel.service.AssetCategoryPropertyLocalServiceWrapper;
import com.liferay.petra.model.adapter.util.ModelAdapterUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class ModularAssetCategoryPropertyLocalServiceWrapper
	extends AssetCategoryPropertyLocalServiceWrapper {

	public ModularAssetCategoryPropertyLocalServiceWrapper() {
		super(null);
	}

	public ModularAssetCategoryPropertyLocalServiceWrapper(
		com.liferay.asset.kernel.service.AssetCategoryPropertyLocalService
			assetCategoryPropertyLocalService) {

		super(assetCategoryPropertyLocalService);
	}

	@Override
	public AssetCategoryProperty addCategoryProperty(
			long userId, long categoryId, String key, String value)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.addCategoryProperty(
				userId, categoryId, key, value));
	}

	@Override
	public void deleteCategoryProperties(long entryId) {
		_assetCategoryPropertyLocalService.deleteCategoryProperties(entryId);
	}

	@Override
	public void deleteCategoryProperty(AssetCategoryProperty categoryProperty) {
		_assetCategoryPropertyLocalService.deleteCategoryProperty(
			ModelAdapterUtil.adapt(
				com.liferay.asset.category.property.model.AssetCategoryProperty.
					class,
				categoryProperty));
	}

	@Override
	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException {

		_assetCategoryPropertyLocalService.deleteCategoryProperty(
			categoryPropertyId);
	}

	@Override
	public List<AssetCategoryProperty> getCategoryProperties() {
		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.getCategoryProperties());
	}

	@Override
	public List<AssetCategoryProperty> getCategoryProperties(long entryId) {
		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.getCategoryProperties(entryId));
	}

	@Override
	public AssetCategoryProperty getCategoryProperty(long categoryPropertyId)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.getCategoryProperty(
				categoryPropertyId));
	}

	@Override
	public AssetCategoryProperty getCategoryProperty(
			long categoryId, String key)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.getCategoryProperty(
				categoryId, key));
	}

	@Override
	public List<AssetCategoryProperty> getCategoryPropertyValues(
		long groupId, String key) {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.getCategoryPropertyValues(
				groupId, key));
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _assetCategoryPropertyLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long userId, long categoryPropertyId, String key, String value)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.updateCategoryProperty(
				userId, categoryPropertyId, key, value));
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyLocalService.updateCategoryProperty(
				categoryPropertyId, key, value));
	}

	@Reference
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;

}