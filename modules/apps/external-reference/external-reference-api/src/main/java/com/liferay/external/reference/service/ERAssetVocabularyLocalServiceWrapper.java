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
 * Provides a wrapper for {@link ERAssetVocabularyLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ERAssetVocabularyLocalService
 * @generated
 */
public class ERAssetVocabularyLocalServiceWrapper
	implements ERAssetVocabularyLocalService,
			   ServiceWrapper<ERAssetVocabularyLocalService> {

	public ERAssetVocabularyLocalServiceWrapper(
		ERAssetVocabularyLocalService erAssetVocabularyLocalService) {

		_erAssetVocabularyLocalService = erAssetVocabularyLocalService;
	}

	@Override
	public com.liferay.asset.kernel.model.AssetVocabulary addOrUpdateVocabulary(
			String externalReferenceCode, long userId, long groupId,
			String title, java.util.Map<java.util.Locale, String> titleMap,
			java.util.Map<java.util.Locale, String> descriptionMap,
			String settings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _erAssetVocabularyLocalService.addOrUpdateVocabulary(
			externalReferenceCode, userId, groupId, title, titleMap,
			descriptionMap, settings, serviceContext);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _erAssetVocabularyLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public ERAssetVocabularyLocalService getWrappedService() {
		return _erAssetVocabularyLocalService;
	}

	@Override
	public void setWrappedService(
		ERAssetVocabularyLocalService erAssetVocabularyLocalService) {

		_erAssetVocabularyLocalService = erAssetVocabularyLocalService;
	}

	private ERAssetVocabularyLocalService _erAssetVocabularyLocalService;

}