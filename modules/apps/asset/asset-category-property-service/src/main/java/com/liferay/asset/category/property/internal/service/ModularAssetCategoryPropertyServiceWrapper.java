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

import com.liferay.asset.category.property.service.AssetCategoryPropertyService;
import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.asset.kernel.service.AssetCategoryPropertyServiceWrapper;
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
public class ModularAssetCategoryPropertyServiceWrapper
	extends AssetCategoryPropertyServiceWrapper {

	public ModularAssetCategoryPropertyServiceWrapper() {
		super(null);
	}

	public ModularAssetCategoryPropertyServiceWrapper(
		com.liferay.asset.kernel.service.AssetCategoryPropertyService
			assetCategoryPropertyService) {

		super(assetCategoryPropertyService);
	}

	@Override
	public AssetCategoryProperty addCategoryProperty(
			long entryId, String key, String value)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyService.addCategoryProperty(
				entryId, key, value));
	}

	@Override
	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException {

		_assetCategoryPropertyService.deleteCategoryProperty(
			categoryPropertyId);
	}

	@Override
	public List<AssetCategoryProperty> getCategoryProperties(long entryId) {
		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyService.getCategoryProperties(entryId));
	}

	@Override
	public List<AssetCategoryProperty> getCategoryPropertyValues(
		long companyId, String key) {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyService.getCategoryPropertyValues(
				companyId, key));
	}

	@Override
	public String getOSGiServiceIdentifier() {
		return _assetCategoryPropertyService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long userId, long categoryPropertyId, String key, String value)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyService.updateCategoryProperty(
				userId, categoryPropertyId, key, value));
	}

	@Override
	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException {

		return ModelAdapterUtil.adapt(
			AssetCategoryProperty.class,
			_assetCategoryPropertyService.updateCategoryProperty(
				categoryPropertyId, key, value));
	}

	@Reference
	private AssetCategoryPropertyService _assetCategoryPropertyService;

}