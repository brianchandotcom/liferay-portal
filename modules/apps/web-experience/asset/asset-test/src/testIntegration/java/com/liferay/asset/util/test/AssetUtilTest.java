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

package com.liferay.asset.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.asset.util.test.AssetTestUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
@Sync
public class AssetUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_group = GroupTestUtil.addGroup();

		_assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), _assetVocabulary.getVocabularyId());

		_assetTag = AssetTestUtil.addTag(_group.getGroupId());
	}

	@Test
	public void testSearchAssetEntries() throws Exception {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		long[] assetCategoryIds = new long[] {_assetCategory.getCategoryId()};
		String[] assetTagNames = new String[] {_assetTag.getName()};

		assertCount(
			0, assetEntryQuery, assetCategoryIds, assetTagNames, null,
			_group.getCompanyId(), StringPool.BLANK, null, null,
			_group.getGroupId(), null, _group.getCreatorUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, RandomTestUtil.randomString(),
			1, 1, 1965, 0, 0, true, true, null, StringPool.BLANK, null, null,
			serviceContext);

		assertCount(
			1, assetEntryQuery, assetCategoryIds, assetTagNames, null,
			_group.getCompanyId(), StringPool.BLANK, null, null,
			_group.getGroupId(), null, _group.getCreatorUserId());
	}

	protected void assertCount(
			final int expectedCount, final AssetEntryQuery assetEntryQuery,
			final long[] assetCategoryIds, final String[] assetTagNames,
			final Map<String, Serializable> attributes, final long companyId,
			final String keywords, final Layout layout, final Locale locale,
			final long scopeGroupId, final TimeZone timezone, final long userId)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					BaseModelSearchResult<AssetEntry> baseModelSearchResult =
						AssetUtil.searchAssetEntries(
							assetEntryQuery, assetCategoryIds, assetTagNames,
							attributes, companyId, keywords, layout, locale,
							scopeGroupId, timezone, userId, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS);

					Assert.assertEquals(
						expectedCount, baseModelSearchResult.getLength());

					return null;
				}

			});
	}

	private AssetCategory _assetCategory;
	private AssetTag _assetTag;
	private AssetVocabulary _assetVocabulary;

	@DeleteAfterTestRun
	private Group _group;

}