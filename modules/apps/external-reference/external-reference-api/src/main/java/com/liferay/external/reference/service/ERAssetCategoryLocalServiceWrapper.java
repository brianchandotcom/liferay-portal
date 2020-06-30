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

package com.liferay.external.reference.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ERAssetCategoryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERAssetCategoryLocalService
 * @generated
 */
public class ERAssetCategoryLocalServiceWrapper
	implements ERAssetCategoryLocalService,
			   ServiceWrapper<ERAssetCategoryLocalService> {

	public ERAssetCategoryLocalServiceWrapper(
		ERAssetCategoryLocalService erAssetCategoryLocalService) {

		_erAssetCategoryLocalService = erAssetCategoryLocalService;
	}

	@Override
	public com.liferay.asset.kernel.model.AssetCategory addOrUpdateCategory(
			String externalReferenceCode, long userId, long groupId,
			long parentCategoryId,
			java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erAssetCategoryLocalService.addOrUpdateCategory(
			externalReferenceCode, userId, groupId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _erAssetCategoryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public ERAssetCategoryLocalService getWrappedService() {
		return _erAssetCategoryLocalService;
	}

	@Override
	public void setWrappedService(
		ERAssetCategoryLocalService erAssetCategoryLocalService) {

		_erAssetCategoryLocalService = erAssetCategoryLocalService;
	}

	private ERAssetCategoryLocalService _erAssetCategoryLocalService;

}