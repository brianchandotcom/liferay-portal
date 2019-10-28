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

package com.liferay.asset.test.util;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class AssetTestUtil {

	public static AssetEntry addAssetEntry(long groupId) {
		return addAssetEntry(groupId, null);
	}

	public static AssetEntry addAssetEntry(long groupId, Date publishDate) {
		return addAssetEntry(
			groupId, publishDate, RandomTestUtil.randomString());
	}

	public static AssetEntry addAssetEntry(
		long groupId, Date publishDate, String className) {

		long assetEntryId = CounterLocalServiceUtil.increment();

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.createAssetEntry(
			assetEntryId);

		assetEntry.setClassName(className);
		assetEntry.setGroupId(groupId);
		assetEntry.setClassPK(RandomTestUtil.randomLong());
		assetEntry.setVisible(true);
		assetEntry.setPublishDate(publishDate);

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

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = HashMapBuilder.<Locale, String>put(
			locale, RandomTestUtil.randomString()
		).build();

		String[] categoryProperties = null;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), groupId, parentCategoryId, titleMap,
			descriptionMap, vocabularyId, categoryProperties, serviceContext);
	}

	public static AssetTag addTag(long groupId) throws Exception {
		return addTag(groupId, RandomTestUtil.randomString());
	}

	public static AssetTag addTag(long groupId, String assetTagName)
		throws PortalException {

		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return AssetTagLocalServiceUtil.addTag(
			userId, groupId, assetTagName, serviceContext);
	}

	public static AssetVocabulary addVocabulary(long groupId) throws Exception {
		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return AssetVocabularyLocalServiceUtil.addVocabulary(
			userId, groupId, RandomTestUtil.randomString(), serviceContext);
	}

	public static AssetVocabulary addVocabulary(
			long groupId, long classNameId, long classTypePK, boolean required)
		throws Exception {

		Locale locale = LocaleUtil.getSiteDefault();

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = HashMapBuilder.<Locale, String>put(
			locale, RandomTestUtil.randomString()
		).build();

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameIdsAndClassTypePKs(
			new long[] {classNameId}, new long[] {classTypePK},
			new boolean[] {required});
		vocabularySettingsHelper.setMultiValued(true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return AssetVocabularyServiceUtil.addVocabulary(
			groupId, RandomTestUtil.randomString(), titleMap, descriptionMap,
			vocabularySettingsHelper.toString(), serviceContext);
	}

}