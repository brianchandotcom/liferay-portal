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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.meris.MerisRule;
import com.liferay.meris.MerisRuleType;
import com.liferay.meris.MerisRuleTypeManager;
import com.liferay.meris.MerisSegmentManager;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
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
	public AssetCategoryMerisProfile getMerisProfile(String merisProfileId) {
		long userId = GetterUtil.getLong(merisProfileId);

		User user = _userLocalService.fetchUser(userId);

		if (user != null) {
			long[] assetCategoryIds = _assetCategoryLocalService.getCategoryIds(
				user.getModelClassName(), user.getUserId());

			return new AssetCategoryMerisProfile(user, assetCategoryIds);
		}

		return null;
	}

	@Override
	public List<AssetCategoryMerisProfile> getMerisProfiles(
		String merisSegmentId, Map<String, Object> context, int start, int end,
		Comparator<AssetCategoryMerisProfile> comparator) {

		AssetCategoryMerisSegment assetCategoryMerisSegment = getMerisSegment(
			merisSegmentId);

		if (assetCategoryMerisSegment == null) {
			return Collections.emptyList();
		}

		List<User> users = _userLocalService.getUsers(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		Stream<User> stream = users.stream();

		return stream.map(
			user -> getMerisProfile(String.valueOf(user.getUserId()))
		).filter(
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
			AssetVocabulary assetVocabulary =
				_assetVocabularyLocalService.getAssetVocabulary(
					GetterUtil.getLong(merisSegmentId));

			return new AssetCategoryMerisSegment(assetVocabulary);
		}
		catch (PortalException pe) {
			_log.error(
				String.format(
					"No asset vocabulary found with asset vocabulary ID %s"),
				pe);
		}

		return null;
	}

	@Override
	public List<AssetCategoryMerisSegment> getMerisSegments(
		String scopeId, int start, int end, Comparator comparator) {

		try {
			List<AssetVocabulary> assetVocabularies =
				_assetVocabularyLocalService.getGroupVocabularies(
					GetterUtil.getLong(scopeId));

			Stream<AssetVocabulary> stream = assetVocabularies.stream();

			return stream.map(
				assetVocabulary ->
					new AssetCategoryMerisSegment(assetVocabulary)
			).collect(
				Collectors.collectingAndThen(
					Collectors.toList(),
					list -> {
						list.sort(comparator);

						return ListUtil.subList(list, start, end);
					})
			);
		}
		catch (PortalException pe) {
			_log.error(
				String.format(
					"Unable to get meris segments for scope ID %s", scopeId),
				pe);

			return Collections.emptyList();
		}
	}

	@Override
	public List<AssetCategoryMerisSegment> getMerisSegments(
		String scopeId, String merisProfileId, String merisSegmentId,
		Map<String, Object> context, int start, int end,
		Comparator<AssetCategoryMerisSegment> comparator) {

		AssetCategoryMerisProfile assetCategoryMerisProfile = getMerisProfile(
			merisProfileId);

		if (assetCategoryMerisProfile == null) {
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

		AssetCategoryMerisProfile assetCategoryMerisProfile = getMerisProfile(
			merisProfileId);

		if (assetCategoryMerisProfile == null) {
			return false;
		}

		AssetCategoryMerisSegment assetCategoryMerisSegment = getMerisSegment(
			merisSegmentId);

		if (assetCategoryMerisSegment == null) {
			return false;
		}

		List<MerisRule> merisRules = assetCategoryMerisSegment.getMerisRules(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<MerisRule> stream = merisRules.stream();

		context.put(
			"assetCategoryIds",
			assetCategoryMerisProfile.getAssetCategoryIds());

		return stream.allMatch(
			merisRule -> {
				MerisRuleType merisRuleType =
					_merisRuleTypeManager.getMerisRuleType(
						merisRule.getMerisRuleTypeId());

				if (merisRuleType == null) {
					return false;
				}

				return merisRuleType.matches(
					context, merisRule.getMerisRuleTypeSettings());
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetCategoryMerisSegmentManager.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private MerisRuleTypeManager _merisRuleTypeManager;

	@Reference
	private UserLocalService _userLocalService;

}