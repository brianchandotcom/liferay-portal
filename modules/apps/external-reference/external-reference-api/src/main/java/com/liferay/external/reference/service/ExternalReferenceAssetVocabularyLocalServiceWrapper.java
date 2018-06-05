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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExternalReferenceAssetVocabularyLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExternalReferenceAssetVocabularyLocalService
 * @generated
 */
@ProviderType
public class ExternalReferenceAssetVocabularyLocalServiceWrapper
	implements ExternalReferenceAssetVocabularyLocalService,
		ServiceWrapper<ExternalReferenceAssetVocabularyLocalService> {
	public ExternalReferenceAssetVocabularyLocalServiceWrapper(
		ExternalReferenceAssetVocabularyLocalService externalReferenceAssetVocabularyLocalService) {
		_externalReferenceAssetVocabularyLocalService = externalReferenceAssetVocabularyLocalService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _externalReferenceAssetVocabularyLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.asset.kernel.model.AssetVocabulary upsertVocabulary(
		long userId, long groupId, String title,
		java.util.Map<java.util.Locale, String> titleMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		String settings, String externalReferenceId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _externalReferenceAssetVocabularyLocalService.upsertVocabulary(userId,
			groupId, title, titleMap, descriptionMap, settings,
			externalReferenceId, serviceContext);
	}

	@Override
	public ExternalReferenceAssetVocabularyLocalService getWrappedService() {
		return _externalReferenceAssetVocabularyLocalService;
	}

	@Override
	public void setWrappedService(
		ExternalReferenceAssetVocabularyLocalService externalReferenceAssetVocabularyLocalService) {
		_externalReferenceAssetVocabularyLocalService = externalReferenceAssetVocabularyLocalService;
	}

	private ExternalReferenceAssetVocabularyLocalService _externalReferenceAssetVocabularyLocalService;
}