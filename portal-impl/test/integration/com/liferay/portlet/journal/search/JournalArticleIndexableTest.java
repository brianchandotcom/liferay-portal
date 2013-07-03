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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.service.persistence.AssetEntryQuery;
import com.liferay.portlet.asset.service.persistence.AssetEntryQueryTestUtil;
import com.liferay.portlet.asset.util.AssetUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Specific test for ensuring that a JournalArticle claims to be not indexable
 * when className is set to DDMStructure. This currently means that this article
 * is a default values holder for a structure.
 *
 * @author Carlos Sierra
 */
@ExecutionTestListeners(listeners = {
	EnvironmentExecutionTestListener.class,
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

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		Hits results = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		int initialCount = results.getLength();

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());

		Assert.assertTrue(article.isIndexable());

		results = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			"Regular articles should get indexed", initialCount + 1,
			results.getLength());
	}

	@Test
	public void testJournalArticleWithClassNameIdDefaultNotIndexable()
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		AssetEntryQuery assetEntryQuery =
			AssetEntryQueryTestUtil.createAssetEntryQuery(
				group.getGroupId(), JournalArticle.class.getName(), null, null,
				new long[] {}, null);

		SearchContext searchContext = ServiceTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setGroupIds(assetEntryQuery.getGroupIds());

		Hits results = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		int initial = results.getLength();

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortalUtil.getClassNameId(DDMStructure.class),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), LocaleUtil.getDefault(), false,
			true, ServiceTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertFalse(
			"Default values holder articles should claim to be NOT indexable",
			article.isIndexable());

		results = AssetUtil.search(
			searchContext, assetEntryQuery, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

		Assert.assertEquals(
			"Default values holder articles should NOT get indexed", initial,
			results.getLength());
	}

}