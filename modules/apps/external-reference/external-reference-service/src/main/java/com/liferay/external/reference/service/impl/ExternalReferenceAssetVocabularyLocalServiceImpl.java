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

package com.liferay.external.reference.service.impl;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.external.reference.service.base.ExternalReferenceAssetVocabularyLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.Locale;
import java.util.Map;

/**
 * @author Dylan Rebelak
 */
public class ExternalReferenceAssetVocabularyLocalServiceImpl
	extends ExternalReferenceAssetVocabularyLocalServiceBaseImpl {

	@Override
	public AssetVocabulary upsertVocabulary(
			long userId, long groupId, String title,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String settings, String externalReferenceId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AssetVocabulary assetVocabulary =
			assetVocabularyLocalService.fetchAssetVocabularyByReferenceCode(
				user.getCompanyId(), externalReferenceId);

		if (assetVocabulary == null) {
			assetVocabulary = assetVocabularyLocalService.addVocabulary(
				userId, groupId, title, titleMap, descriptionMap, settings,
				serviceContext);

			assetVocabulary.setExternalReferenceCode(externalReferenceId);

			assetVocabularyLocalService.updateAssetVocabulary(assetVocabulary);
		}
		else {
			assetVocabulary = assetVocabularyLocalService.updateVocabulary(
				assetVocabulary.getVocabularyId(), title, titleMap,
				descriptionMap, settings, serviceContext);
		}

		return assetVocabulary;
	}

}