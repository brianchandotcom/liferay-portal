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

package com.liferay.portlet.asset.util.test;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyServiceUtil;
import com.liferay.portlet.asset.util.AssetVocabularySettingsHelper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public class AssetTestUtil {

	public static AssetCategory addCategory(long groupId, long vocabularyId)
		throws Exception {

		return addCategory(
			groupId, vocabularyId,
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
	}

	public static AssetCategory addCategory(
			long groupId, long vocabularyId, long parentCategoryId)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

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

		String[] tagProperties = null;

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return AssetTagLocalServiceUtil.addTag(
			userId, RandomTestUtil.randomString(), tagProperties,
			serviceContext);
	}

	public static AssetVocabulary addVocabulary(long groupId) throws Exception {
		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return AssetVocabularyLocalServiceUtil.addVocabulary(
			userId, RandomTestUtil.randomString(), serviceContext);
	}

	public static AssetVocabulary addVocabulary(
			long groupId, long classNameId, boolean required)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		Locale locale = LocaleUtil.getSiteDefault();

		titleMap.put(locale, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(locale, RandomTestUtil.randomString());

		AssetVocabularySettingsHelper vocabularySettingsHelper =
			new AssetVocabularySettingsHelper();

		vocabularySettingsHelper.setClassNameIds(
			new long[] {classNameId}, new boolean[] {required});
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