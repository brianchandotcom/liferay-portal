/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public ERAssetVocabularyLocalServiceWrapper() {
		this(null);
	}

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