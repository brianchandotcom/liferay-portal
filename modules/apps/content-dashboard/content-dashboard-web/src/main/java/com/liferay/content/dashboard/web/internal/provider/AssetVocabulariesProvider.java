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

package com.liferay.content.dashboard.web.internal.provider;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.content.dashboard.web.internal.constants.ContentDashboardAssetVocabularyNamesConstants;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(service = AssetVocabulariesProvider.class)
public class AssetVocabulariesProvider {

	public List<AssetVocabulary> getAssetVocabularies(long companyId) {
		Group group = _groupLocalService.fetchCompanyGroup(companyId);

		if (group == null) {
			return Collections.emptyList();
		}

		return Stream.of(
			ContentDashboardAssetVocabularyNamesConstants.
				DEFAULT_ASSET_VOCABULARY_NAMES
		).map(
			assetVocabularyName ->
				_assetVocabularyLocalService.fetchGroupVocabulary(
					group.getGroupId(), assetVocabularyName)
		).filter(
			Objects::nonNull
		).filter(
			assetVocabulary -> assetVocabulary.getCategoriesCount() > 0
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}