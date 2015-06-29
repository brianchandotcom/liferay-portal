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

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.configuration.JournalServiceConfigurationKeys;
import com.liferay.journal.configuration.JournalServiceConfigurationUtil;
import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.impl.JournalUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class JournalArticleIndexVersionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		JournalServiceConfigurationValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS =
			false;

		_group = GroupTestUtil.addGroup();

		_testMode = PortalRunMode.isTestMode();

		PortalRunMode.setTestMode(true);

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		JournalServiceConfigurationValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS =
			GetterUtil.getBoolean(
				JournalServiceConfigurationUtil.get(
					JournalServiceConfigurationKeys.
						JOURNAL_ARTICLE_INDEX_ALL_VERSIONS));

		PortalRunMode.setTestMode(_testMode);
	}

	@Test
	public void testDeleteAllArticleVersions() throws Exception {
		long initialSearchCount = searchCount(true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalArticleLocalServiceUtil.deleteArticle(
			_group.getGroupId(), updateArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount, searchCount(true));
	}

	@Test
	public void testDeleteArticleVersion() throws Exception {
		long initialSearchCount = searchCount(true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			serviceContext);

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalArticleLocalServiceUtil.deleteArticle(
			updateArticle, updateArticle.getUrlTitle(), serviceContext);

		List<JournalArticle> articles = search(true);

		Assert.assertEquals(initialSearchCount + 1, articles.size());

		JournalArticle searchArticle = articles.get(0);

		Assert.assertEquals(article.getId(), searchArticle.getId());
	}

	@Test
	public void testExpireAllArticleVersions() throws Exception {
		long initialSearchCount = searchCount(true);
		long initialNonheadSearchCount = searchCount(false);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalTestUtil.expireArticle(_group.getGroupId(), updateArticle);

		Assert.assertEquals(initialSearchCount, searchCount(true));
		Assert.assertEquals(initialNonheadSearchCount + 1, searchCount(false));
	}

	@Test
	public void testExpireArticleVersion() throws Exception {
		long initialSearchCount = searchCount(true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		JournalTestUtil.expireArticle(
			_group.getGroupId(), updateArticle, updateArticle.getVersion());

		List<JournalArticle> articles = search(true);

		Assert.assertEquals(initialSearchCount + 1, articles.size());

		JournalArticle searchArticle = articles.get(0);

		Assert.assertEquals(article.getId(), searchArticle.getId());
	}

	@Test
	public void testIndexableArticle() throws Exception {
		long initialSearchCount = searchCount(true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));

		article.setIndexable(false);

		article = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount, searchCount(true));

		article.setIndexable(true);

		JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(initialSearchCount + 1, searchCount(true));
	}

	protected List<JournalArticle> search(boolean head) throws Exception {
		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		if (!head) {
			searchContext.setAttribute(
				Field.STATUS, WorkflowConstants.STATUS_ANY);
			searchContext.setAttribute("head", Boolean.FALSE);
		}

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return JournalUtil.getArticles(results);
	}

	protected long searchCount(boolean head) throws Exception {
		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		if (!head) {
			searchContext.setAttribute("head", Boolean.FALSE);
			searchContext.setAttribute(
				Field.STATUS, WorkflowConstants.STATUS_ANY);
		}

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	@DeleteAfterTestRun
	private Group _group;

	private boolean _testMode;

}