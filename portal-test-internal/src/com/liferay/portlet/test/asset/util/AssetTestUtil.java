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

package com.liferay.portlet.test.asset.util;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class AssetTestUtil {

	public static AssetEntry addAssetEntry(long groupId) throws Exception {
		return addAssetEntry(groupId, null);
	}

	public static AssetEntry addAssetEntry(long groupId, Date publishDate)
		throws Exception {

		long assetEntryId = CounterLocalServiceUtil.increment();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.createAssetEntry(
			assetEntryId);

		assetEntry.setClassName(RandomTestUtil.randomString());
		assetEntry.setClassPK(RandomTestUtil.randomLong());
		assetEntry.setGroupId(groupId);
		assetEntry.setPublishDate(publishDate);
		assetEntry.setVisible(true);

		return AssetEntryLocalServiceUtil.updateAssetEntry(assetEntry);
	}

	public static AssetCategory addCategory(long groupId, long vocabularyId)
		throws Exception {

		return addCategory(
			groupId, vocabularyId,
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static AssetCategory addCategory(
			long groupId, long vocabularyId, long parentCategoryId)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, RandomTestUtil.randomString());

		String[] categoryProperties = null;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	public static AssetTag addTag(long groupId) throws Exception {
		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return AssetTagLocalServiceUtil.addTag(
			userId, RandomTestUtil.randomString(), serviceContext);
	}

	public static AssetVocabulary addVocabulary(long groupId) throws Exception {
		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return AssetVocabularyLocalServiceUtil.addVocabulary(
			userId, RandomTestUtil.randomString(), serviceContext);
	}

	public static AssetVocabulary addVocabulary(
			long groupId, long classNameId, long classTypePK, boolean required)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(locale, RandomTestUtil.randomString());

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			new long[] {classNameId}, new long[] {classTypePK},
			new boolean[] {required});
		vocabularySettingsHelper.setMultiValued(true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		AssetVocabulary vocabulary = AssetVocabularyServiceUtil.addVocabulary(
			RandomTestUtil.randomString(), titleMap, descriptionMap,
			vocabularySettingsHelper.toString(), serviceContext);

		return vocabulary;
	}

}