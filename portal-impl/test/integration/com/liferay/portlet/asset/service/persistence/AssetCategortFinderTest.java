/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetCategoryServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetCategortFinderTest {

	@Test
	public void testGetVocabularyCategories() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AssetCategory assetCategory = createAssetCategory(
			group.getGroupId(), "lowercasetitle");

		List<AssetCategory> assetCategories =
			AssetCategoryServiceUtil.getVocabularyCategories(
				group.getGroupId(), "lowercasetitle",
				assetCategory.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(assetCategories.size(), 1);

		AssetCategory resultAssetCategory = assetCategories.get(0);

		Assert.assertEquals(assetCategory, resultAssetCategory);
	}

	@Test
	public void testGetVocabularyCategoriesUpperCase() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AssetCategory assetCategory = createAssetCategory(
			group.getGroupId(), "UPPERCASETITLE");

		List<AssetCategory> assetCategories =
			AssetCategoryServiceUtil.getVocabularyCategories(
				group.getGroupId(), "uppercasetitle",
				assetCategory.getVocabularyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(assetCategories.size(), 1);

		AssetCategory resultAssetCategory = assetCategories.get(0);

		Assert.assertEquals(assetCategory, resultAssetCategory);
	}

	@Test
	public void testGetVocabularyCategoriesWithoutName() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AssetCategory assetCategory = createAssetCategory(
			group.getGroupId(), ServiceTestUtil.randomString());

		List<AssetCategory> assetCategories =
			AssetCategoryServiceUtil.getVocabularyCategories(
				group.getGroupId(), null, assetCategory.getVocabularyId(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(assetCategories.size(), 1);

		AssetCategory resultAssetCategory = assetCategories.get(0);

		Assert.assertEquals(assetCategory, resultAssetCategory);
	}

	protected AssetCategory createAssetCategory(long groupId, String name)
		throws Exception {

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), ServiceTestUtil.randomString(),
				ServiceTestUtil.getServiceContext(groupId));

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(LocaleUtil.getDefault(), name);

		return AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(),
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, titleMap, null,
			assetVocabulary.getVocabularyId(), null,
			ServiceTestUtil.getServiceContext(groupId));
	}

}