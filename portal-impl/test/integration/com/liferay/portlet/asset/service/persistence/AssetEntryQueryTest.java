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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetEntryServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class AssetEntryQueryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				serviceContext);

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
		testAssetCategories(new long[] {_healthCategoryId}, false, false, 2);
	}

	@Test
	public void testAllAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthCategoryId, _sportCategoryId}, false, false, 2);
	}

	@Test
	public void testAllAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {_healthCategoryId, _sportCategoryId, _foodCategoryId},
			false, false, 1);
	}

	@Test
	public void testAllAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {
				_healthCategoryId, _sportCategoryId, _foodCategoryId,
				_travelCategoryId
			},
			false, false, 0);
	}

	@Test
	public void testAllAssetTags1() throws Exception {
		testAssetTags(new String[] {"liferay"}, false, false, 2);
	}

	@Test
	public void testAllAssetTags2() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture"}, false, false, 2);
	}

	@Test
	public void testAllAssetTags3() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture", "services"}, false, false,
			1);
	}

	@Test
	public void testAllAssetTags4() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture", "services", "osgi"}, false,
			false, 0);
	}

	@Test
	public void testAnyAssetCategories1() throws Exception {
		testAssetCategories(new long[] {_healthCategoryId}, true, false, 2);
	}

	@Test
	public void testAnyAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthCategoryId, _sportCategoryId}, true, false, 2);
	}

	@Test
	public void testAnyAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {_healthCategoryId, _sportCategoryId, _foodCategoryId},
			true, false, 2);
	}

	@Test
	public void testAnyAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {_fashionCategoryId, _foodCategoryId}, true, false, 1);
	}

	@Test
	public void testAnyAssetTags1() throws Exception {
		testAssetTags(new String[] {"liferay"}, true, false, 2);
	}

	@Test
	public void testAnyAssetTags2() throws Exception {
		testAssetTags(new String[] {"liferay", "architecture"}, true, false, 2);
	}

	@Test
	public void testAnyAssetTags3() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture", "services"}, true, false,
			2);
	}

	@Test
	public void testAnyAssetTags4() throws Exception {
		testAssetTags(new String[] {"modularity", "osgi"}, true, false, 1);
	}

	@Test
	public void testNotAllAssetCategories1() throws Exception {
		testAssetCategories(new long[] {_healthCategoryId}, false, true, 0);
	}

	@Test
	public void testNotAllAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthCategoryId, _sportCategoryId}, false, true, 0);
	}

	@Test
	public void testNotAllAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {_fashionCategoryId, _foodCategoryId}, false, true, 1);
	}

	@Test
	public void testNotAllAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {_fashionCategoryId, _foodCategoryId, _travelCategoryId},
			false, true, 2);
	}

	@Test
	public void testNotAllAssetTags1() throws Exception {
		testAssetTags(new String[] {"liferay"}, false, true, 0);
	}

	@Test
	public void testNotAllAssetTags2() throws Exception {
		testAssetTags(new String[] {"liferay", "architecture"}, false, true, 0);
	}

	@Test
	public void testNotAllAssetTags3() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture", "services"}, false, true,
			1);
	}

	@Test
	public void testNotAllAssetTags4() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture", "services", "osgi"}, false,
			true, 2);
	}

	@Test
	public void testNotAnyAssetCategories1() throws Exception {
		testAssetCategories(new long[] {_healthCategoryId}, true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthCategoryId, _sportCategoryId}, true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {_fashionCategoryId, _foodCategoryId, _travelCategoryId},
			true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {_fashionCategoryId, _foodCategoryId}, true, true, 1);
	}

	@Test
	public void testNotAnyAssetTags1() throws Exception {
		testAssetTags(new String[] {"liferay"}, true, true, 0);
	}

	@Test
	public void testNotAnyAssetTags2() throws Exception {
		testAssetTags(new String[] {"liferay", "architecture"}, true, true, 0);
	}

	@Test
	public void testNotAnyAssetTags3() throws Exception {
		testAssetTags(
			new String[] {"liferay", "architecture", "services"}, true, true,
			0);
	}

	@Test
	public void testNotAnyAssetTags4() throws Exception {
		testAssetTags(new String[] {"modularity", "osgi"}, true, true, 1);
	}

	@Test
	public void testOrderByRatingsAsc() throws Exception {
		double[] scores = {0.44, 0.2, 0.6, 0.22, 0.86};
		double[] orderedScores = {0.2, 0.22, 0.44, 0.6, 0.86};

		testOrderByRatings(scores, orderedScores, "ASC");
	}

	@Test
	public void testOrderByRatingsDesc() throws Exception {
		double[] scores = {0.44, 0.2, 0.6, 0.22, 0.86};
		double[] orderedScores = {0.86, 0.6, 0.44, 0.22, 0.2};

		testOrderByRatings(scores, orderedScores, "DESC");
	}

	@Test
	public void testOrderByViewCountsAsc() throws Exception {
		int[] viewCounts = new int[10];
		int[] orderedViewCounts = new int[10];

		for (int i = 0; i < viewCounts.length; i++) {
			int randomInt = RandomTestUtil.randomInt();

			viewCounts[i] = randomInt;
			orderedViewCounts[i] = randomInt;
		}

		Arrays.sort(orderedViewCounts);

		testOrderByViewCount(viewCounts, orderedViewCounts, "ASC");
	}

	@Test
	public void testOrderByViewCountsDesc() throws Exception {
		int[] viewCounts = new int[10];
		int[] orderedViewCounts = new int[10];

		for (int i = 0; i < viewCounts.length; i++) {
			int randomInt = RandomTestUtil.randomInt();

			viewCounts[i] = randomInt;
			orderedViewCounts[i] = randomInt;
		}

		Arrays.sort(orderedViewCounts);

		ArrayUtil.reverse(orderedViewCounts);

		testOrderByViewCount(viewCounts, orderedViewCounts, "DESC");
	}

	protected AssetEntryQuery buildAssetEntryQuery(
			long groupId, long[] assetCategoryIds, String[] assetTagNames,
			boolean any, boolean not)
		throws PortalException {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		if (assetCategoryIds != null) {
			assetEntryQuery = buildAssetEntryQueryWithAssetCategoryIds(
				assetEntryQuery, assetCategoryIds, any, not);
		}

		if (assetTagNames != null) {
			long[] assetTagIds = null;

			assetTagIds = AssetTagLocalServiceUtil.getTagIds(
				groupId, assetTagNames);

			assetEntryQuery = buildAssetEntryQueryWithAssetTagIds(
				assetEntryQuery, assetTagIds, any, not);
		}

		assetEntryQuery.setGroupIds(new long[] {groupId});

		return assetEntryQuery;
	}

	protected AssetEntryQuery buildAssetEntryQueryWithAssetCategoryIds(
		AssetEntryQuery assetEntryQuery, long[] assetCategoryIds, boolean any,
		boolean not) {

		if (any && not) {
			assetEntryQuery.setNotAnyCategoryIds(assetCategoryIds);
		}
		else if (!any && not) {
			assetEntryQuery.setNotAllCategoryIds(assetCategoryIds);
		}
		else if (any && !not) {
			assetEntryQuery.setAnyCategoryIds(assetCategoryIds);
		}
		else {
			assetEntryQuery.setAllCategoryIds(assetCategoryIds);
		}

		return assetEntryQuery;
	}

	protected AssetEntryQuery buildAssetEntryQueryWithAssetTagIds(
		AssetEntryQuery assetEntryQuery, long[] assetTagIds, boolean any,
		boolean not) {

		if (any && not) {
			assetEntryQuery.setNotAnyTagIds(assetTagIds);
		}
		else if (!any && not) {
			assetEntryQuery.setNotAllTagIds(assetTagIds);
		}
		else if (any && !not) {
			assetEntryQuery.setAnyTagIds(assetTagIds);
		}
		else {
			assetEntryQuery.setAllTagIds(assetTagIds);
		}

		return assetEntryQuery;
	}

	protected void testAssetCategories(
			long[] assetCategoryIds, boolean any, boolean not,
			int expectedResults)
		throws Exception {

		testAssetCategorization(
			assetCategoryIds, null, "Skiing in the Alps", _assetCategoryIds1,
			null, "Keep your body in a good shape!", _assetCategoryIds2, null,
			any, not, expectedResults);
	}

	protected void testAssetCategorization(
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

		AssetEntryQuery assetEntryQuery = buildAssetEntryQuery(
			_group.getGroupId(), assetCategoryIds, assetTagNames, any, not);

		int initialEntries = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		if (assetCategoryIds1 != null) {
			serviceContext.setAssetCategoryIds(assetCategoryIds1);
		}

		if (assetTagNames1 != null) {
			serviceContext.setAssetTagNames(assetTagNames1);
		}

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title1, StringPool.BLANK,
			StringPool.BLANK, "This is a blog entry for testing purposes", 1, 1,
			1965, 0, 0, true, true, null, null, null, serviceContext);

		if (assetCategoryIds2 != null) {
			serviceContext.setAssetCategoryIds(assetCategoryIds2);
		}

		if (assetTagNames2 != null) {
			serviceContext.setAssetTagNames(assetTagNames2);
		}

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title2, StringPool.BLANK,
			StringPool.BLANK, "This is a blog entry for testing purposes", 1, 1,
			1965, 0, 0, true, true, null, null, null, serviceContext);

		// Clear the thread local cache which is populated in AssetPublisherUtil

		threadLocalCache.removeAll();

		assetEntryQuery = buildAssetEntryQuery(
			_group.getGroupId(), assetCategoryIds, assetTagNames, any, not);

		int allTagsEntries = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		Assert.assertEquals(initialEntries + expectedResults, allTagsEntries);
	}

	protected void testAssetTags(
			String[] assetTagNames, boolean any, boolean not,
			int expectedResults)
		throws Exception {

		testAssetCategorization(
			null, assetTagNames, "Liferay Architectural Approach", null,
			new String[] {"liferay", "architecture", "services"},
			"Modularity with OSGI", null,
			new String[] {"liferay", "architecture", "modularity", "osgi"}, any,
			not, expectedResults);
	}

	protected void testOrderByRatings(
			double[] scores, double[] orderedScores, String orderByType)
		throws Exception {

		// Clear the thread local cache which is populated in AssetPublisherUtil

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		for (int i = 0; i < scores.length; i++) {
			BlogsEntry entry = BlogsTestUtil.addEntry(_group, true);

			RatingsEntryServiceUtil.updateEntry(
				BlogsEntry.class.getName(), entry.getEntryId(), scores[i]);
		}

		// Clear the thread local cache which is populated in AssetPublisherUtil

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery = buildAssetEntryQuery(
			_group.getGroupId(), null, null, false, false);

		assetEntryQuery.setOrderByCol1("ratings");
		assetEntryQuery.setOrderByType1(orderByType);

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
				assetEntry.getClassName(), assetEntry.getClassPK());

			Assert.assertEquals(
				ratingsStats.getAverageScore(), orderedScores[i], 0);
		}
	}

	protected void testOrderByViewCount(
			int[] viewCounts, int[] orderedViewCounts, String orderByType)
		throws Exception {

		// Clear the thread local cache which is populated in AssetPublisherUtil

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		for (int i = 0; i < viewCounts.length; i++) {
			BlogsEntry entry = BlogsTestUtil.addEntry(_group, true);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				BlogsEntry.class.getName(), entry.getEntryId());

			assetEntry.setViewCount(viewCounts[i]);

			AssetEntryLocalServiceUtil.updateAssetEntry(assetEntry);
		}

		// Clear the thread local cache which is populated in AssetPublisherUtil

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery = buildAssetEntryQuery(
			_group.getGroupId(), null, null, false, false);

		assetEntryQuery.setOrderByCol1("viewCount");
		assetEntryQuery.setOrderByType1(orderByType);

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		for (int i = 0; i < assetEntries.size(); i++) {
			AssetEntry assetEntry = assetEntries.get(i);

			Assert.assertEquals(
				assetEntry.getViewCount(), orderedViewCounts[i]);
		}
	}

	private long[] _assetCategoryIds1;
	private long[] _assetCategoryIds2;
	private long _fashionCategoryId;
	private long _foodCategoryId;

	@DeleteAfterTestRun
	private Group _group;

	private long _healthCategoryId;
	private long _sportCategoryId;
	private long _travelCategoryId;
	private long _vocabularyId;

}