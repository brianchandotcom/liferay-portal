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

package com.liferay.meris.asset.category.demo.internal;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.meris.MerisProfile;
import com.liferay.meris.MerisProfileManager;
import com.liferay.meris.MerisSegmentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Garcia
 */
@Component(immediate = true, service = MerisSegmentManager.class)
public class AssetCategoryMerisSegmentManager
	implements MerisSegmentManager
		<AssetCategoryMerisSegment, AssetCategoryMerisProfile> {

	@Override
	public List<AssetCategoryMerisProfile> getMerisProfiles(
		String merisSegmentId, Map<String, Object> context, int start, int end,
		Comparator<AssetCategoryMerisProfile> comparator) {

		AssetCategoryMerisSegment assetCategoryMerisSegment = getMerisSegment(
			merisSegmentId);

		if (assetCategoryMerisSegment == null) {
			return Collections.emptyList();
		}

		List<AssetCategoryMerisProfile> assetCategoryMerisProfiles =
			_merisProfileManager.getMerisProfiles(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<AssetCategoryMerisProfile> stream =
			assetCategoryMerisProfiles.stream();

		return stream.filter(
			merisProfile -> matches(
				merisProfile.getMerisProfileId(), merisSegmentId, context)
		).collect(
			Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					list.sort(comparator);

					return ListUtil.subList(list, start, end);
				})
		);
	}

	@Override
	public AssetCategoryMerisSegment getMerisSegment(String merisSegmentId) {
		try {
			AssetCategory assetCategory =
				_assetCategoryLocalService.getAssetCategory(
					GetterUtil.getLong(merisSegmentId));

			return new AssetCategoryMerisSegment(assetCategory);
		}
		catch (PortalException pe) {
			_log.error(
				String.format(
					"No asset category found with asset category ID %s",
					merisSegmentId),
				pe);
		}

		return null;
	}

	@Override
	public List<AssetCategoryMerisSegment> getMerisSegments(
		String scopeId, int start, int end, Comparator comparator) {

		try {
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.getGroupVocabulary(
					GetterUtil.getLong(scopeId), _ASSET_VOCABULARY_NAME);

			List<AssetCategory> assetCategories =
				assetVocabulary.getCategories();

			Stream<AssetCategory> stream = assetCategories.stream();

			return stream.map(
				assetCategory -> new AssetCategoryMerisSegment(assetCategory)
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException pe) {
			_log.error(
				String.format(
					"Asset vocabulary %s not found in groupId %s",
					_ASSET_VOCABULARY_NAME, scopeId),
				pe);

			return Collections.emptyList();
		}
	}

	@Override
	public List<AssetCategoryMerisSegment> getMerisSegments(
		String scopeId, String merisProfileId, Map<String, Object> context,
		int start, int end, Comparator<AssetCategoryMerisSegment> comparator) {

		MerisProfile merisProfile = _merisProfileManager.getMerisProfile(
			merisProfileId);

		if (merisProfile == null) {
			return Collections.emptyList();
		}

		Collection<AssetCategoryMerisSegment> assetCategoryMerisSegments =
			getMerisSegments(
				scopeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<AssetCategoryMerisSegment> stream =
			assetCategoryMerisSegments.stream();

		return stream.filter(
			merisSegment -> matches(
				merisProfileId, merisSegment.getMerisSegmentId(), context)
		).collect(
			Collectors.collectingAndThen(
				Collectors.toList(),
				list -> {
					list.sort(comparator);

					return ListUtil.subList(list, start, end);
				})
		);
	}

	@Override
	public boolean matches(
		String merisProfileId, String merisSegmentId,
		Map<String, Object> context) {

		MerisProfile merisProfile = _merisProfileManager.getMerisProfile(
			merisProfileId);

		if (merisProfile == null) {
			return false;
		}

		AssetCategoryMerisSegment assetCategoryMerisSegment = getMerisSegment(
			merisSegmentId);

		if (assetCategoryMerisSegment == null) {
			return false;
		}

		long[] assetCategoryIds = GetterUtil.getLongValues(
			merisProfile.getAttribute("assetCategoryIds"));

		return ArrayUtil.contains(
			assetCategoryIds, assetCategoryMerisSegment.getAssetCategoryId());
	}

	private static final String _ASSET_VOCABULARY_NAME = "Segments";

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryMerisSegmentManager.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private MerisProfileManager _merisProfileManager;

}