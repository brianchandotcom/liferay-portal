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

package com.liferay.asset.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetEntryServiceUtil;
import com.liferay.asset.kernel.service.AssetLinkLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.asset.kernel.service.AssetVocabularyLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCache;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class AssetEntryQueryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		_assetVocabularyId = vocabulary.getVocabularyId();

		AssetCategory fashionCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				"Fashion", _assetVocabularyId, serviceContext);

		_fashionAssetCategoryId = fashionCategory.getCategoryId();

		AssetCategory foodCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			"Food", _assetVocabularyId, serviceContext);

		_foodAssetCategoryId = foodCategory.getCategoryId();

		AssetCategory healthCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				"Health", _assetVocabularyId, serviceContext);

		_healthAssetCategoryId = healthCategory.getCategoryId();

		AssetCategory sportCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			"Sport", _assetVocabularyId, serviceContext);

		_sportAssetCategoryId = sportCategory.getCategoryId();

		AssetCategory travelCategory =
			AssetCategoryLocalServiceUtil.addCategory(
				TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
				"Travel", _assetVocabularyId, serviceContext);

		_travelAssetCategoryId = travelCategory.getCategoryId();

		_assetCategoryIds1 = new long[] {
			_healthAssetCategoryId, _sportAssetCategoryId,
			_travelAssetCategoryId
		};

		_assetCategoryIds2 = new long[] {
			_fashionAssetCategoryId, _foodAssetCategoryId,
			_healthAssetCategoryId, _sportAssetCategoryId
		};
	}

	@Test
	public void testAdvancedSearchAndOperator() throws Exception {
		testAdvancedSearch(
			"Test", "Cabina14", "Cabina14", true, "Cabina14 in Madrid",
			"Cabina14 blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchAndOperatorDescription1() throws Exception {
		testAdvancedSearch(
			"Julio", "Cabina14", "Cabina14", true, "Concert in Madrid",
			"Cabina14 blah blah blah", 0);
	}

	@Test
	public void testAdvancedSearchAndOperatorDescription2() throws Exception {
		testAdvancedSearch(
			"Test", null, "Cabina14", true, "Concert in Madrid",
			"Cabina14 blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchAndOperatorDescription3() throws Exception {
		testAdvancedSearch(
			null, null, "Cabina14", true, "Concert in Madrid",
			"Cabina14 blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchAndOperatorTitle1() throws Exception {
		testAdvancedSearch(
			"Julio", "Cabina14", "Cabina14", true, "Cabina14 in Madrid",
			"Cabina14 blah blah blah", 0);
	}

	@Test
	public void testAdvancedSearchAndOperatorTitle2() throws Exception {
		testAdvancedSearch(
			"Test", "Cabina14", null, true, "Cabina14 in Madrid",
			"blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchAndOperatorTitle3() throws Exception {
		testAdvancedSearch(
			null, "Cabina14", null, true, "Cabina14 in Madrid",
			"blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchAndOperatorUserName1() throws Exception {
		testAdvancedSearch(
			"Julio", "Cabina14", "Cabina14", true, "Cabina14 in Madrid",
			"Cabina14 blah blah blah", 0);
	}

	@Test
	public void testAdvancedSearchAndOperatorUserName2() throws Exception {
		testAdvancedSearch(
			"Test", null, "Cabina14", true, "Concert in Madrid",
			"Cabina14 blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchAndOperatorUserName3() throws Exception {
		testAdvancedSearch(
			"Test", null, null, true, "Concert in Madrid", "blah blah blah", 2);
	}

	@Test
	public void testAdvancedSearchOrOperator1() throws Exception {
		testAdvancedSearch(
			"Julio", "Cabina14", "Cabina14", false, "Concert in Madrid",
			"blah blah blah", 0);
	}

	@Test
	public void testAdvancedSearchOrOperator2() throws Exception {
		testAdvancedSearch(
			"Julio", "Cabina14", "Cabina14", false, "Cabina14 in Madrid",
			"blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchOrOperator3() throws Exception {
		testAdvancedSearch(
			"Julio", "Cabina14", "Cabina14", false, "Concert in Madrid",
			"Cabina14 blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchOrOperator4() throws Exception {
		testAdvancedSearch(
			"Test", "Cabina14", "Cabina14", false, "Concert in Madrid",
			"blah blah blah", 2);
	}

	@Test
	public void testAdvancedSearchOrOperator5() throws Exception {
		testAdvancedSearch(
			null, "Cabina14", null, false, "Cabina14 in Madrid",
			"blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchOrOperator6() throws Exception {
		testAdvancedSearch(
			null, "Cabina14", null, false, "Concert in Madrid",
			"blah blah blah", 0);
	}

	@Test
	public void testAdvancedSearchOrOperator7() throws Exception {
		testAdvancedSearch(
			null, null, "Cabina14", false, "Concert in Madrid",
			"Cabina14 blah blah blah", 1);
	}

	@Test
	public void testAdvancedSearchOrOperator8() throws Exception {
		testAdvancedSearch(
			null, null, "Cabina14", false, "Concert in Madrid",
			"blah blah blah", 0);
	}

	@Test
	public void testAdvancedSearchOrOperator9() throws Exception {
		testAdvancedSearch(
			"Test", null, null, false, "Concert in Madrid", "blah blah blah",
			2);
	}

	@Test
	public void testAdvancedSearchOrOperator10() throws Exception {
		testAdvancedSearch(
			"Julio", null, null, false, "Concert in Madrid", "blah blah blah",
			0);
	}

	@Test
	public void testAllAssetCategories1() throws Exception {
		testAssetCategories(
			new long[] {_healthAssetCategoryId}, false, false, 2);
	}

	@Test
	public void testAllAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthAssetCategoryId, _sportAssetCategoryId}, false,
			false, 2);
	}

	@Test
	public void testAllAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {
				_healthAssetCategoryId, _sportAssetCategoryId,
				_foodAssetCategoryId
			},
			false, false, 1);
	}

	@Test
	public void testAllAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {
				_healthAssetCategoryId, _sportAssetCategoryId,
				_foodAssetCategoryId, _travelAssetCategoryId
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
		testAssetCategories(
			new long[] {_healthAssetCategoryId}, true, false, 2);
	}

	@Test
	public void testAnyAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthAssetCategoryId, _sportAssetCategoryId}, true,
			false, 2);
	}

	@Test
	public void testAnyAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {
				_healthAssetCategoryId, _sportAssetCategoryId,
				_foodAssetCategoryId
			},
			true, false, 2);
	}

	@Test
	public void testAnyAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {_fashionAssetCategoryId, _foodAssetCategoryId}, true,
			false, 1);
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
	public void testKeywordsDescription() throws Exception {
		testAssetKeywords(
			"Cabina14", "Concert in Madrid", "Cabina14 Blah blah blah", 1);
	}

	@Test
	public void testKeywordsNotFound() throws Exception {
		testAssetKeywords("Cabina14", "Concert in Madrid", "Blah blah blah", 0);
	}

	@Test
	public void testKeywordsTitle() throws Exception {
		testAssetKeywords(
			"Cabina14", "Cabina14 in Madrid", "Blah blah blah", 1);
	}

	@Test
	public void testKeywordsUserName() throws Exception {
		testAssetKeywords("Test", "Concert in Madrid", "Blah blah blah", 2);
	}

	@Test
	public void testLinkedAssetMultipleLinked() throws Exception {
		testLinkedAsset(3);
	}

	@Test
	public void testLinkedAssetNoLinked() throws Exception {
		testLinkedAsset(0);
	}

	@Test
	public void testLinkedAssetOneLinked() throws Exception {
		testLinkedAsset(1);
	}

	@Test
	public void testNotAllAssetCategories1() throws Exception {
		testAssetCategories(
			new long[] {_healthAssetCategoryId}, false, true, 0);
	}

	@Test
	public void testNotAllAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthAssetCategoryId, _sportAssetCategoryId}, false,
			true, 0);
	}

	@Test
	public void testNotAllAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {_fashionAssetCategoryId, _foodAssetCategoryId}, false,
			true, 1);
	}

	@Test
	public void testNotAllAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {
				_fashionAssetCategoryId, _foodAssetCategoryId,
				_travelAssetCategoryId
			},
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
		testAssetCategories(new long[] {_healthAssetCategoryId}, true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories2() throws Exception {
		testAssetCategories(
			new long[] {_healthAssetCategoryId, _sportAssetCategoryId}, true,
			true, 0);
	}

	@Test
	public void testNotAnyAssetCategories3() throws Exception {
		testAssetCategories(
			new long[] {
				_fashionAssetCategoryId, _foodAssetCategoryId,
				_travelAssetCategoryId
			},
			true, true, 0);
	}

	@Test
	public void testNotAnyAssetCategories4() throws Exception {
		testAssetCategories(
			new long[] {_fashionAssetCategoryId, _foodAssetCategoryId}, true,
			true, 1);
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

	protected AssetEntryQuery buildAssetEntryQuery(
		long groupId, long[] assetCategoryIds, String[] assetTagNames,
		boolean any, boolean not) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		if (assetCategoryIds != null) {
			assetEntryQuery = buildAssetEntryQueryWithAssetCategoryIds(
				assetEntryQuery, assetCategoryIds, any, not);
		}

		if (assetTagNames != null) {
			long[] assetTagIds = AssetTagLocalServiceUtil.getTagIds(
				groupId, assetTagNames);

			assetEntryQuery = buildAssetEntryQueryWithAssetTagIds(
				assetEntryQuery, assetTagIds, any, not);
		}

		assetEntryQuery.setGroupIds(new long[] {groupId});

		return assetEntryQuery;
	}

	protected AssetEntryQuery buildAssetEntryQueryWithAdvancedSearch(
		long groupId, boolean andOperator, String title, String description,
		String userName) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setAndOperator(andOperator);
		assetEntryQuery.setDescription(description);
		assetEntryQuery.setGroupIds(new long[] {groupId});
		assetEntryQuery.setTitle(title);
		assetEntryQuery.setUserName(userName);

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

	protected AssetEntryQuery buildAssetEntryQueryWithKeywords(
		long groupId, String keywords) {

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {groupId});
		assetEntryQuery.setKeywords(keywords);

		return assetEntryQuery;
	}

	protected void testAdvancedSearch(
			String searchUserName, String searchTitle, String searchDescription,
			boolean andOperator, String title, String description,
			int expectedAssetEntriesCount)
		throws Exception {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery =
			buildAssetEntryQueryWithAdvancedSearch(
				_group.getGroupId(), andOperator, searchTitle,
				searchDescription, searchUserName);

		int initialAssetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry1 = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringPool.BLANK, StringUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true, null,
			StringPool.BLANK, null, null, serviceContext);

		BlogsEntry blogsEntry2 = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title, StringPool.BLANK, description,
			RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true, null,
			StringPool.BLANK, null, null, serviceContext);

		threadLocalCache.removeAll();

		int assetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		Assert.assertEquals(
			initialAssetEntriesCount + expectedAssetEntriesCount,
			assetEntriesCount);

		BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry1);
		BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry2);
	}

	protected void testAssetCategories(
			long[] assetCategoryIds, boolean any, boolean not,
			int expectedAssetEntriesCount)
		throws Exception {

		testAssetCategorization(
			assetCategoryIds, null, "Skiing in the Alps", _assetCategoryIds1,
			null, "Keep your body in a good shape!", _assetCategoryIds2, null,
			any, not, expectedAssetEntriesCount);
	}

	protected void testAssetCategorization(
			long[] assetCategoryIds, String[] assetTagNames, String title1,
			long[] assetCategoryIds1, String[] assetTagNames1, String title2,
			long[] assetCategoryIds2, String[] assetTagNames2, boolean any,
			boolean not, int expectedAssetEntriesCount)
		throws Exception {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery = buildAssetEntryQuery(
			_group.getGroupId(), assetCategoryIds, assetTagNames, any, not);

		int initialAssetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
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
			StringPool.BLANK, RandomTestUtil.randomString(), 1, 1, 1965, 0, 0,
			true, true, null, StringPool.BLANK, null, null, serviceContext);

		if (assetCategoryIds2 != null) {
			serviceContext.setAssetCategoryIds(assetCategoryIds2);
		}

		if (assetTagNames2 != null) {
			serviceContext.setAssetTagNames(assetTagNames2);
		}

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title2, StringPool.BLANK,
			StringPool.BLANK, RandomTestUtil.randomString(), 1, 1, 1965, 0, 0,
			true, true, null, StringPool.BLANK, null, null, serviceContext);

		threadLocalCache.removeAll();

		assetEntryQuery = buildAssetEntryQuery(
			_group.getGroupId(), assetCategoryIds, assetTagNames, any, not);

		int assetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		Assert.assertEquals(
			initialAssetEntriesCount + expectedAssetEntriesCount,
			assetEntriesCount);
	}

	protected void testAssetKeywords(
			String keywords, String title, String description,
			int expectedAssetEntriesCount)
		throws Exception {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery = buildAssetEntryQueryWithKeywords(
			_group.getGroupId(), keywords);

		int initialAssetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringPool.BLANK, StringUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true, null,
			StringPool.BLANK, null, null, serviceContext);

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), title, StringPool.BLANK, description,
			RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true, null,
			StringPool.BLANK, null, null, serviceContext);

		threadLocalCache.removeAll();

		int assetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		Assert.assertEquals(
			initialAssetEntriesCount + expectedAssetEntriesCount,
			assetEntriesCount);
	}

	protected void testAssetTags(
			String[] assetTagNames, boolean any, boolean not,
			int expectedAssetEntriesCount)
		throws Exception {

		testAssetCategorization(
			null, assetTagNames, "Liferay Architectural Approach", null,
			new String[] {"liferay", "architecture", "services"},
			"Modularity with OSGI", null,
			new String[] {"liferay", "architecture", "modularity", "osgi"}, any,
			not, expectedAssetEntriesCount);
	}

	protected void testLinkedAsset(int expectedAssetEntriesCount)
		throws Exception {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setAndOperator(true);
		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		int initialAssetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry1 = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringPool.BLANK, StringUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true, null,
			StringPool.BLANK, null, null, serviceContext);

		AssetEntry assetEntry1 = AssetEntryLocalServiceUtil.getEntry(
			BlogsEntry.class.getName(), blogsEntry1.getEntryId());

		List<BlogsEntry> blogsEntries = new ArrayList<>();

		blogsEntries.add(blogsEntry1);

		List<AssetLink> assetLinks = new ArrayList<>();

		Set<Long> linkedAssetEntryIds = new HashSet<>();

		for (int i = 0; i < expectedAssetEntriesCount; i++) {
			BlogsEntry linkedBlogsEntry = BlogsEntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), StringUtil.randomString(),
				StringPool.BLANK, StringUtil.randomString(),
				RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true,
				null, StringPool.BLANK, null, null, serviceContext);

			AssetEntry linkedAssetEntry = AssetEntryLocalServiceUtil.getEntry(
				BlogsEntry.class.getName(), linkedBlogsEntry.getEntryId());

			AssetLink assetLink = AssetLinkLocalServiceUtil.addLink(
				TestPropsValues.getUserId(), assetEntry1.getEntryId(),
				linkedAssetEntry.getEntryId(), 0, 0);

			blogsEntries.add(linkedBlogsEntry);

			assetLinks.add(assetLink);

			linkedAssetEntryIds.add(linkedAssetEntry.getEntryId());
		}

		BlogsEntry notLinkedBlogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringPool.BLANK, StringUtil.randomString(),
			RandomTestUtil.randomString(), 1, 1, 1965, 0, 0, true, true, null,
			StringPool.BLANK, null, null, serviceContext);

		blogsEntries.add(notLinkedBlogsEntry);

		assetEntryQuery.setLinkedAssetEntryId(assetEntry1.getEntryId());

		threadLocalCache.removeAll();

		int assetEntriesCount = AssetEntryServiceUtil.getEntriesCount(
			assetEntryQuery);

		Assert.assertEquals(
			initialAssetEntriesCount + expectedAssetEntriesCount,
			assetEntriesCount);

		List<AssetEntry> assetEntries = AssetEntryServiceUtil.getEntries(
			assetEntryQuery);

		Assert.assertEquals(
			assetEntries.toString(),
			initialAssetEntriesCount + expectedAssetEntriesCount,
			assetEntries.size());

		for (AssetEntry assetEntry : assetEntries) {
			Assert.assertTrue(
				linkedAssetEntryIds.contains(assetEntry.getEntryId()));
		}

		for (AssetLink assetLink : assetLinks) {
			AssetLinkLocalServiceUtil.deleteAssetLink(assetLink);
		}

		for (BlogsEntry blogsEntry : blogsEntries) {
			BlogsEntryLocalServiceUtil.deleteEntry(blogsEntry);
		}
	}

	protected void testOrderByRatings(
			double[] scores, double[] orderedScores, String orderByType)
		throws Exception {

		ThreadLocalCache<Object[]> threadLocalCache =
			ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, AssetEntryServiceImpl.class.getName());

		threadLocalCache.removeAll();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Calendar displayDateCalendar = CalendarFactoryUtil.getCalendar(
			2012, 1, 1);

		for (double score : scores) {
			BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), displayDateCalendar.getTime(),
				serviceContext);

			RatingsEntryServiceUtil.updateEntry(
				BlogsEntry.class.getName(), blogsEntry.getEntryId(), score);
		}

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

	private long[] _assetCategoryIds1;
	private long[] _assetCategoryIds2;
	private long _assetVocabularyId;
	private long _fashionAssetCategoryId;
	private long _foodAssetCategoryId;

	@DeleteAfterTestRun
	private Group _group;

	private long _healthAssetCategoryId;
	private long _sportAssetCategoryId;
	private long _travelAssetCategoryId;

}