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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 */
@Sync
public class AssetCategoryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_blogsIndexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);
	}

	@After
	public void tearDown() throws Exception {
		IndexerRegistryUtil.register(_blogsIndexer);
	}

	@Test
	public void testDeleteCategory() throws Exception {
		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			AssetVocabularyLocalServiceUtil.addVocabulary(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				titleMap, null, null, serviceContext);

		AssetCategory assetCategory = AssetCategoryLocalServiceUtil.addCategory(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			assetVocabulary.getVocabularyId(), serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {assetCategory.getCategoryId()});

		BlogsEntry blogsEntry =
			BlogsEntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(), serviceContext);

		AssetTestIndexer assetTestIndexer = new AssetTestIndexer();

		assetTestIndexer.setExpectedValues(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		IndexerRegistryUtil.register(assetTestIndexer);

		AssetCategoryLocalServiceUtil.deleteCategory(assetCategory, true);
	}

	private Indexer _blogsIndexer;

	@DeleteAfterTestRun
	private Group _group;

}