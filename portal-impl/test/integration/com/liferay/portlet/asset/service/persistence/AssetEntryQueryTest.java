/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl;
import com.liferay.portlet.asset.util.AssetEntryTestUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class AssetEntryQueryTest {

	public void setUpAssetCategories() throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), "Test Vocabulary", serviceContext);

		_vocabularyId = vocabulary.getVocabularyId();

		AssetCategory fashionCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), "Fashion", _vocabularyId,
				serviceContext);

		_fashionCategoryId = fashionCategory.getCategoryId();

		AssetCategory foodCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), "Food", _vocabularyId, serviceContext);

		_foodCategoryId = foodCategory.getCategoryId();

		AssetCategory healthCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), "Health", _vocabularyId,
				serviceContext);

		_healthCategoryId = healthCategory.getCategoryId();

		AssetCategory sportCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), "Sport", _vocabularyId,
			serviceContext);

		_sportCategoryId = sportCategory.getCategoryId();

		AssetCategory travelCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), "Travel", _vocabularyId,
				serviceContext);

		_travelCategoryId = travelCategory.getCategoryId();

		_assetCategoryIds1 =
			new long[] {_healthCategoryId, _sportCategoryId, _travelCategoryId};
		_assetCategoryIds2 = new long[] {
			_fashionCategoryId, _foodCategoryId, _healthCategoryId,
			_sportCategoryId
		};
	}

	@Test
	public void testAllAssetCategories1() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds = new long[] {_healthCategoryId};

		testAssetCategories(assetCategoryIds, false, false, 2);
	}

	@Test
	public void testAllAssetCategories2() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_healthCategoryId, _sportCategoryId};

		testAssetCategories(assetCategoryIds, false, false, 2);
	}

	@Test
	public void testAllAssetCategories3() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_healthCategoryId, _sportCategoryId, _foodCategoryId};

		testAssetCategories(assetCategoryIds, false, false, 1);
	}

	@Test
	public void testAllAssetCategories4() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds = new long[] {
			_healthCategoryId, _sportCategoryId, _foodCategoryId,
			_travelCategoryId
		};

		testAssetCategories(assetCategoryIds, false, false, 0);
	}

	@Test
	public void testAllAssetTags1() throws Exception {
		String[] assetTagNames = new String[] {"liferay"};

		testAssetTags(assetTagNames, false, false, 2);
	}

	@Test
	public void testAllAssetTags2() throws Exception {
		String[] assetTagNames = new String[] {"liferay", "architecture"};

		testAssetTags(assetTagNames, false, false, 2);
	}

	@Test
	public void testAllAssetTags3() throws Exception {
		String[] assetTagNames =
			new String[] {"liferay", "architecture", "services"};

		testAssetTags(assetTagNames, false, false, 1);
	}

	@Test
	public void testAllAssetTags4() throws Exception {
		String[] assetTagNames =
			new String[] {"liferay", "architecture", "services", "osgi"};

		testAssetTags(assetTagNames, false, false, 0);
	}

	@Test
	public void testAnyAssetCategories1() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds = new long[] {_healthCategoryId};

		testAssetCategories(assetCategoryIds, true, false, 2);
	}

	@Test
	public void testAnyAssetCategories2() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_healthCategoryId, _sportCategoryId};

		testAssetCategories(assetCategoryIds, true, false, 2);
	}

	@Test
	public void testAnyAssetCategories3() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_healthCategoryId, _sportCategoryId, _foodCategoryId};

		testAssetCategories(assetCategoryIds, true, false, 2);
	}

	@Test
	public void testAnyAssetCategories4() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_fashionCategoryId, _foodCategoryId};

		testAssetCategories(assetCategoryIds, true, false, 1);
	}

	@Test
	public void testAnyAssetTags1() throws Exception {
		String[] assetTagNames = new String[] {"liferay"};

		testAssetTags(assetTagNames, true, false, 2);
	}

	@Test
	public void testAnyAssetTags2() throws Exception {
		String[] assetTagNames = new String[] {"liferay", "architecture"};

		testAssetTags(assetTagNames, true, false, 2);
	}

	@Test
	public void testAnyAssetTags3() throws Exception {
		String[] assetTagNames =
			new String[] {"liferay", "architecture", "services"};

		testAssetTags(assetTagNames, true, false, 2);
	}

	@Test
	public void testAnyAssetTags4() throws Exception {
		String[] assetTagNames = new String[] {"modularity", "osgi"};

		testAssetTags(assetTagNames, true, false, 1);
	}

	@Test
	public void testNotAllAssetCategories1() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds = new long[] {_healthCategoryId};

		testAssetCategories(assetCategoryIds, false, true, 0);
	}

	@Test
	public void testNotAllAssetCategories2() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_healthCategoryId, _sportCategoryId};

		testAssetCategories(assetCategoryIds, false, true, 0);
	}

	@Test
	public void testNotAllAssetCategories3() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_fashionCategoryId, _foodCategoryId};

		testAssetCategories(assetCategoryIds, false, true, 1);
	}

	@Test
	public void testNotAllAssetCategories4() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_fashionCategoryId, _foodCategoryId, _travelCategoryId};

		testAssetCategories(assetCategoryIds, false, true, 2);
	}

	@Test
	public void testNotAllAssetTags1() throws Exception {
		String[] assetTagNames = new String[] {"liferay"};

		testAssetTags(assetTagNames, false, true, 0);
	}

	@Test
	public void testNotAllAssetTags2() throws Exception {
		String[] assetTagNames = new String[] {"liferay", "architecture"};

		testAssetTags(assetTagNames, false, true, 0);
	}

	@Test
	public void testNotAllAssetTags3() throws Exception {
		String[] assetTagNames =
			new String[] {"liferay", "architecture", "services"};

		testAssetTags(assetTagNames, false, true, 1);
	}

	@Test
	public void testNotAllAssetTags4() throws Exception {
		String[] assetTagNames =
			new String[] {"liferay", "architecture", "services", "osgi"};

		testAssetTags(assetTagNames, false, true, 2);
	}

	@Test
	public void testNotAnyAssetCategories1() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds = new long[] {_healthCategoryId};

		testAssetCategories(assetCategoryIds, true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories2() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_healthCategoryId, _sportCategoryId};

		testAssetCategories(assetCategoryIds, true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories3() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_fashionCategoryId, _foodCategoryId, _travelCategoryId};

		testAssetCategories(assetCategoryIds, true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories4() throws Exception {
		setUpAssetCategories();

		long[] assetCategoryIds =
			new long[] {_fashionCategoryId, _foodCategoryId};

		testAssetCategories(assetCategoryIds, true, true, 1);
	}

	@Test
	public void testNotAnyAssetTags1() throws Exception {
		String[] assetTagNames = new String[] {"liferay"};

		testAssetTags(assetTagNames, true, true, 0);
	}

	@Test
	public void testNotAnyAssetTags2() throws Exception {
		String[] assetTagNames = new String[] {"liferay", "architecture"};

		testAssetTags(assetTagNames, true, true, 0);
	}

	@Test
	public void testNotAnyAssetTags3() throws Exception {
		String[] assetTagNames =
			new String[] {"liferay", "architecture", "services"};

		testAssetTags(assetTagNames, true, true, 0);
	}

	@Test
	public void testNotAnyAssetTags4() throws Exception {
		String[] assetTagNames = new String[] {"modularity", "osgi"};

		testAssetTags(assetTagNames, true, true, 1);
	}

	protected void testAssetCategories(
			long[] assetCategoryIds, boolean any, boolean not,
			int expectedResults)
		throws Exception {

		testAssetCategorization(
			assetCategoryIds, null, "Skiing in Alps", _assetCategoryIds1, null,
			"Keep your body in a good shape!", _assetCategoryIds2, null, any,
			not, expectedResults);
	}

	protected void testAssetTags(
			String[] assetTagNames, boolean any, boolean not,
			int expectedResults)
		throws Exception {

		testAssetCategorization(
			null, assetTagNames, "Liferay Architectural Approach", null,
			_assetTagNames1, "Modularity with OSGI", null, _assetTagNames2, any,
			not, expectedResults);
	}

	private void testAssetCategorization(
			long[] assetCategoryIds, String[] assetTagNames, String title1,
			long[] assetCategoryIds1, String[] assetTagNames1, String title2,
			long[] assetCategoryIds2, String[] assetTagNames2, boolean any,
			boolean not, int expectedResults)
		throws Exception {

		// Clear the thread local cache which is populated in AssetPublisherUtil

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		if (assetCategoryIds != null) {
			assetEntryQuery = AssetEntryTestUtil.buildAssetEntryQueryCategories(
				assetEntryQuery, assetCategoryIds, any, not);
		}

		Group group = ServiceTestUtil.addGroup();

		long[] assetTagIds = null;

		if (assetTagNames != null) {
			assetTagIds = AssetTagLocalServiceUtil.getTagIds(
				group.getGroupId(), assetTagNames);

			assetEntryQuery = AssetEntryTestUtil.buildAssetEntryQueryTags(
				assetEntryQuery, assetTagIds, any, not);
		}

		assetEntryQuery.setGroupIds(new long[] {group.getGroupId()});

		int initialEntries = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		if (assetCategoryIds1 != null) {
			serviceContext.setAssetCategoryIds(assetCategoryIds1);
		}

		if (assetTagNames1 != null) {
			serviceContext.setAssetTagNames(assetTagNames1);
		}

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title1, StringPool.BLANK,
			"This is a blog entry for testing purposes", 1, 1, 1965, 0, 0, true,
			true, null, false, null, null, null, serviceContext);

		if (assetCategoryIds2 != null) {
			serviceContext.setAssetCategoryIds(assetCategoryIds2);
		}

		if (assetTagNames2 != null) {
			serviceContext.setAssetTagNames(assetTagNames2);
		}

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title2, StringPool.BLANK,
			"This is a blog entry for testing purposes", 1, 1, 1965, 0, 0, true,
			true, null, false, null, null, null, serviceContext);

		// Clear the thread local cache which is populated in AssetPublisherUtil

		threadLocalCache.removeAll();

		assetEntryQuery = new AssetEntryQuery();

		if (assetCategoryIds != null) {
			assetEntryQuery = AssetEntryTestUtil.buildAssetEntryQueryCategories(
				assetEntryQuery, assetCategoryIds, any, not);
		}

		if (assetTagNames != null) {
			assetTagIds = AssetTagLocalServiceUtil.getTagIds(
				group.getGroupId(), assetTagNames);

			assetEntryQuery = AssetEntryTestUtil.buildAssetEntryQueryTags(
				assetEntryQuery, assetTagIds, any, not);
		}

		int allTagsEntries = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		Assert.assertEquals(initialEntries + expectedResults, allTagsEntries);
	}

	private long[] _assetCategoryIds1 = null;
	private long[] _assetCategoryIds2 = null;

	private String[] _assetTagNames1 =
		new String[] {"liferay", "architecture", "services"};
	private String[] _assetTagNames2 =
		new String[] {"liferay", "architecture", "modularity", "osgi"};

	private long _fashionCategoryId = 0;
	private long _foodCategoryId = 0;
	private long _healthCategoryId = 0;
	private long _sportCategoryId = 0;
	private long _travelCategoryId = 0;

	private long _vocabularyId = 0;

}