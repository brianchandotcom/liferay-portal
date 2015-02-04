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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 */
@Sync
public class JournalArticleIndexVersionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS = false;

		_group = GroupTestUtil.addGroup();
	}

	@After
	public void tearDown() {
		PropsValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS));
	}

	@Test
	public void testDeleteAllArticleVersions() throws Exception {
		long initialSearchCount = searchCount();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalArticleLocalServiceUtil.deleteArticle(
			_group.getGroupId(), updateArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount, searchCount());
	}

	@Test
	public void testDeleteArticleVersion() throws Exception {
		long initialSearchCount = searchCount();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			serviceContext);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalArticleLocalServiceUtil.deleteArticle(
			updateArticle, updateArticle.getUrlTitle(), serviceContext);

		List<JournalArticle> articles = search();

		Assert.assertEquals(initialSearchCount + 1, articles.size());

		JournalArticle searchArticle = articles.get(0);

		Assert.assertEquals(article.getId(), searchArticle.getId());
	}

	@Test
	public void testExpireAllArticleVersions() throws Exception {
		long initialSearchCount = searchCount();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalTestUtil.expireArticle(_group.getGroupId(), updateArticle);

		Assert.assertEquals(initialSearchCount, searchCount());
	}

	@Test
	public void testExpireArticleVersion() throws Exception {
		long initialSearchCount = searchCount();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		JournalTestUtil.expireArticle(
			_group.getGroupId(), updateArticle, updateArticle.getVersion());

		List<JournalArticle> articles = search();

		Assert.assertEquals(initialSearchCount + 1, articles.size());

		JournalArticle searchArticle = articles.get(0);

		Assert.assertEquals(article.getId(), searchArticle.getId());
	}

	@Test
	public void testIndexableArticle() throws Exception {
		long initialSearchCount = searchCount();

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount());

		article.setIndexable(false);

		article = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount, searchCount());

		article.setIndexable(true);

		JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount());
	}

	protected List<JournalArticle> search() throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return JournalUtil.getArticles(results);
	}

	protected long searchCount() throws Exception {
		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	@DeleteAfterTestRun
	private Group _group;

}