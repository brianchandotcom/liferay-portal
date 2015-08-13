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
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

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

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() {
		JournalServiceConfigurationValues.JOURNAL_ARTICLE_INDEX_ALL_VERSIONS =
			GetterUtil.getBoolean(
				JournalServiceConfigurationUtil.get(
					JournalServiceConfigurationKeys.
						JOURNAL_ARTICLE_INDEX_ALL_VERSIONS));
	}

	@Test
	public void testDeleteAllArticleVersions() throws Exception {
		long initialSearchCount = 0;

		assertSearchCount(initialSearchCount, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(initialSearchCount + 1, true);

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(initialSearchCount + 1, true);

		JournalArticleLocalServiceUtil.deleteArticle(
			_group.getGroupId(), updateArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(initialSearchCount, true);
	}

	@Test
	public void testDeleteArticleVersion() throws Exception {
		long initialSearchCount = 0;

		assertSearchCount(initialSearchCount, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(initialSearchCount + 1, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			serviceContext);

		assertSearchCount(initialSearchCount + 1, true);

		JournalArticleLocalServiceUtil.deleteArticle(
			updateArticle, updateArticle.getUrlTitle(), serviceContext);

		assertSearchArticle(initialSearchCount + 1, article);
	}

	@Test
	public void testExpireAllArticleVersions() throws Exception {
		long initialSearchCount = 0;

		assertSearchCount(initialSearchCount, true);

		long initialNonheadSearchCount = 0;

		assertSearchCount(initialNonheadSearchCount, false);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(initialSearchCount + 1, true);

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(initialSearchCount + 1, true);

		JournalTestUtil.expireArticle(_group.getGroupId(), updateArticle);

		assertSearchCount(initialSearchCount, true);
		assertSearchCount(initialNonheadSearchCount + 1, false);
	}

	@Test
	public void testExpireArticleVersion() throws Exception {
		long initialSearchCount = 0;

		assertSearchCount(initialSearchCount, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(initialSearchCount + 1, true);

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(initialSearchCount + 1, true);

		JournalTestUtil.expireArticle(
			_group.getGroupId(), updateArticle, updateArticle.getVersion());

		assertSearchArticle(initialSearchCount + 1, article);
	}

	@Test
	public void testIndexableArticle() throws Exception {
		long initialSearchCount = 0;

		assertSearchCount(initialSearchCount, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(initialSearchCount + 1, true);

		article.setIndexable(false);

		article = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(initialSearchCount, true);

		article.setIndexable(true);

		JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(initialSearchCount + 1, true);
	}

	protected void assertSearchArticle(
			final long expectedCount, final JournalArticle article)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					List<JournalArticle> articles = search(true);

					Assert.assertEquals(expectedCount, articles.size());

					JournalArticle searchArticle = articles.get(0);

					Assert.assertEquals(article.getId(), searchArticle.getId());

					return null;
				}

			});
	}

	protected void assertSearchCount(
			final long expectedCount, final boolean head)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					long actualCount = searchCount(head);

					Assert.assertEquals(expectedCount, actualCount);

					return null;
				}

			});
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

}