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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.test.portal.util.GroupTestUtil;
import com.liferay.test.portal.util.RandomTestUtil;
import com.liferay.test.portal.util.SearchContextTestUtil;
import com.liferay.test.portal.util.ServiceContextTestUtil;
import com.liferay.test.portlet.asset.service.persistence.AssetEntryQueryTestUtil;
import com.liferay.test.portlet.journal.util.JournalTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra
 */
@ExecutionTestListeners(listeners = {
	MainServletExecutionTestListener.class,
	SynchronousDestinationExecutionTestListener.class,
	TransactionalExecutionTestListener.class
})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class JournalArticleIndexableTest {

	@Test
	public void testJournalArticleIsIndexableByDefault() throws Exception {
		Group group = GroupTestUtil.addGroup();

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				group.getGroupId(), JournalArticle.class.getName(), null, null,
				new long[] {}, null);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		Hits hits = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		int total = hits.getLength();

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		Assert.assertTrue(article.isIndexable());

		hits = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			"Regular articles should be indexed", total + 1, hits.getLength());
	}

	@Test
	public void testJournalArticleWithClassNameDDMStructureIsUnindexable()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				group.getGroupId(), JournalArticle.class.getName(), null, null,
				new long[0], null);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		Hits hits = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		int total = hits.getLength();

		JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortalUtil.getClassNameId(DDMStructure.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LocaleUtil.getSiteDefault(), false,
			true, ServiceContextTestUtil.getServiceContext(group.getGroupId()));

		hits = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			"Unindexable articles should not be indexed", total,
			hits.getLength());
	}

}