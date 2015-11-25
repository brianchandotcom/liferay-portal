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

package com.liferay.portlet.asset.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.test.AssetTestUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

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

/**
 * @author Eduardo Garcia
 */
@Sync
public class AssetUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_vocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		_category = AssetTestUtil.addCategory(
			_group.getGroupId(), _vocabulary.getVocabularyId());

		_tag = AssetTestUtil.addTag(_group.getGroupId());
	}

	@Test
	public void testSearchAssetEntries() throws Exception {
		final long[] categoryIds = new long[] {_category.getCategoryId()};
		final String[] tagNames = new String[] {_tag.getName()};
		final AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		assertCount(
			0, categoryIds, tagNames, StringPool.BLANK, null, assetEntryQuery,
			_group.getCompanyId(), _group.getGroupId(), null, null,
			_group.getCreatorUserId(), null);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAssetCategoryIds(categoryIds);
		serviceContext.setAssetTagNames(tagNames);

		BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, RandomTestUtil.randomString(),
			1, 1, 1965, 0, 0, true, true, null, StringPool.BLANK, null, null,
			serviceContext);

		assertCount(
			1, categoryIds, tagNames, StringPool.BLANK, null, assetEntryQuery,
			_group.getCompanyId(), _group.getGroupId(), null, null,
			_group.getCreatorUserId(), null);
	}

	protected void assertCount(
			final int expectedCount, final long[] assetCategoryIds,
			final String[] assetTagNames, final String keywords,
			final Locale locale, final AssetEntryQuery assetEntryQuery,
			final long companyId, final long scopeGroupId, final Layout layout,
			final TimeZone timezone, final long userId,
			final Map<String, Serializable> attributes)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS, 1, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					BaseModelSearchResult<AssetEntry> searchResult =
						AssetUtil.searchAssetEntries(
							assetCategoryIds, assetTagNames, keywords, locale,
							assetEntryQuery, companyId, scopeGroupId, layout,
							timezone, userId, QueryUtil.ALL_POS,
							QueryUtil.ALL_POS, attributes);

					Assert.assertEquals(
						expectedCount, searchResult.getLength());

					return null;
				}

			});
	}

	private AssetCategory _category;

	@DeleteAfterTestRun
	private Group _group;

	private AssetTag _tag;
	private AssetVocabulary _vocabulary;

}