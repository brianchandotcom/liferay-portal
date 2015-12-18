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
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Michael C. Han
 * @author Manuel de la Peña
 */
@Sync
public class AssetTagLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_blogsEntryIndexer = IndexerRegistryUtil.getIndexer(BlogsEntry.class);
	}

	@After
	public void tearDown() throws Exception {
		IndexerRegistryUtil.register(_blogsEntryIndexer);
	}

	@Test
	public void testDeleteTag() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetTag assetTag = AssetTagLocalServiceUtil.addTag(
			TestPropsValues.getUserId(), _group.getGroupId(), "Tag",
			serviceContext);

		serviceContext.setAssetTagNames(new String[] {assetTag.getName()});

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), "Test", RandomTestUtil.randomString(),
			serviceContext);

		TestAssetIndexer testAssetIndexer = new TestAssetIndexer();

		testAssetIndexer.setExpectedValues(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		IndexerRegistryUtil.register(testAssetIndexer);

		AssetTagLocalServiceUtil.deleteTag(assetTag);

		Assert.assertNull(
			AssetTagLocalServiceUtil.fetchAssetTag(assetTag.getTagId()));

		long classNameId = PortalUtil.getClassNameId(BlogsEntry.class);

		AssetTagStats assetTagStats = AssetTagStatsLocalServiceUtil.getTagStats(
			assetTag.getTagId(), classNameId);

		Assert.assertEquals(0, assetTagStats.getAssetCount());
	}

	private Indexer<BlogsEntry> _blogsEntryIndexer;

	@DeleteAfterTestRun
	private Group _group;

}