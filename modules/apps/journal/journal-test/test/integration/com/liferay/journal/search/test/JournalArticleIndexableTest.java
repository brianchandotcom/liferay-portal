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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.service.persistence.test.AssetEntryQueryTestUtil;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra
 */
@RunWith(Arquillian.class)
public class JournalArticleIndexableTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testJournalArticleIsIndexableByDefault() throws Exception {
		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group.getGroupId(), JournalArticle.class.getName(), null, null,
				new long[] {}, null);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		int initialEntries = 0;

		assertCount(initialEntries, assetEntryQuery, searchContext);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		Assert.assertTrue(article.isIndexable());

		assertCount(initialEntries + 1, assetEntryQuery, searchContext);
	}

	@Test
	public void testJournalArticleWithClassNameDDMStructureIsUnindexable()
		throws Exception {

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				_group.getGroupId(), JournalArticle.class.getName(), null, null,
				new long[0], null);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		int initialEntries = 0;

		assertCount(initialEntries, assetEntryQuery, searchContext);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortalUtil.getClassNameId(DDMStructure.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(), false,
			true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		assertCount(initialEntries, assetEntryQuery, searchContext);
	}

	protected void assertCount(
			final int expectedCount, final AssetEntryQuery assetEntryQuery,
			final SearchContext searchContext)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					int actualCount = searchCount(
						assetEntryQuery, searchContext, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

					Assert.assertEquals(expectedCount, actualCount);

					return null;
				}

			});
	}

	protected int searchCount(
			AssetEntryQuery assetEntryQuery, SearchContext searchContext,
			int start, int end)
		throws Exception {

		Hits hits = AssetUtil.search(
			searchContext, assetEntryQuery, start, end);

		return hits.getLength();
	}

	@DeleteAfterTestRun
	private Group _group;

}