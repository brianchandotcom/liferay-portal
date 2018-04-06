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

package com.liferay.segments.asset.categories.demo.internal.service;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.segments.asset.categories.demo.internal.model.AssetCategorySegment;
import com.liferay.segments.service.SegmentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = SegmentService.class)
public class AssetCategorySegmentService
	implements SegmentService<AssetCategorySegment> {

	@Override
	public Collection<AssetCategorySegment> getSegments(long groupId) {
		try {
			AssetVocabulary vocabulary =
				_assetVocabularyLocalService.getGroupVocabulary(
					groupId, _ASSET_VOCABULARY_NAME);

			List<AssetCategory> categories = vocabulary.getCategories();

			Stream<AssetCategory> categoriesStream = categories.stream();

			return categoriesStream.map(
				category -> new AssetCategorySegment(category)
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException pe) {
			_log.error(
				String.format(
					"Vocabulary %s not found in group %s",
					_ASSET_VOCABULARY_NAME, groupId),
				pe);

			return Collections.emptyList();
		}
	}

	@Override
	public boolean matches(
		long userId, AssetCategorySegment segment,
		Map<String, Object> context) {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			User.class.getName(), userId);

		if (assetEntry == null) {
			return false;
		}

		return _assetCategoryLocalService.hasAssetEntryAssetCategory(
			assetEntry.getEntryId(), segment.getAssetCategoryId());
	}

	private static final String _ASSET_VOCABULARY_NAME = "Segments";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategorySegmentService.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

}