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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.external.reference.service.base.ExternalReferenceAssetCategoryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Map;

/**
 * @author Dylan Rebelak
 */
public class ExternalReferenceAssetCategoryLocalServiceImpl
	extends ExternalReferenceAssetCategoryLocalServiceBaseImpl {

	@Override
	public AssetCategory upsertCategory(
			long userId, long groupId, long parentCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			long vocabularyId, String[] categoryProperties,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		AssetCategory assetCategory =
			assetCategoryLocalService.fetchAssetCategoryByReferenceCode(
				user.getCompanyId(), externalReferenceCode);

		if (Validator.isNull(assetCategory)) {
			assetCategory = assetCategoryLocalService.addCategory(
				userId, groupId, parentCategoryId, titleMap, descriptionMap,
				vocabularyId, categoryProperties, serviceContext);

			assetCategory.setExternalReferenceCode(externalReferenceCode);

			assetCategory = assetCategoryLocalService.updateAssetCategory(
				assetCategory);
		}
		else {
			assetCategory = assetCategoryLocalService.updateCategory(
				userId, assetCategory.getCategoryId(), parentCategoryId,
				titleMap, descriptionMap, vocabularyId, categoryProperties,
				serviceContext);
		}

		return assetCategory;
	}

}