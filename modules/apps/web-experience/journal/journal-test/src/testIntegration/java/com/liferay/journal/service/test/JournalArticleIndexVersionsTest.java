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
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
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
public class JournalArticleIndexVersionsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		User user = TestPropsValues.getUser();

		user.setCompanyId(TestPropsValues.getCompanyId());

		ServiceTestUtil.setUser(user);

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				TestPropsValues.getUserId(), true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferences);

		portalPreferences.setValue(
			"", "expireAllArticleVersionsEnabled", "true");
		portalPreferences.setValue(
			"", "indexAllArticleVersionsEnabled", "false");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferences));
	}

	@After
	public void tearDown() throws Exception {
		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
	}

	@Test
	public void testDeleteAllArticleVersions() throws Exception {
		assertSearchCount(0, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(1, true);

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(1, true);

		JournalArticleLocalServiceUtil.deleteArticle(
			_group.getGroupId(), updateArticle.getArticleId(),
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(0, true);
	}

	@Test
	public void testDeleteArticleVersion() throws Exception {
		assertSearchCount(0, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(1, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			serviceContext);

		assertSearchCount(1, true);

		JournalArticleLocalServiceUtil.deleteArticle(
			updateArticle, updateArticle.getUrlTitle(), serviceContext);

		assertSearchArticle(1, article);
	}

	@Test
	public void testExpireAllArticleVersions() throws Exception {
		assertSearchCount(0, true);

		assertSearchCount(0, false);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(1, true);

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(1, true);

		JournalTestUtil.expireArticle(_group.getGroupId(), updateArticle);

		assertSearchCount(0, true);
		assertSearchCount(1, false);
	}

	@Test
	public void testExpireArticleVersion() throws Exception {
		assertSearchCount(0, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(1, true);

		JournalArticle updateArticle = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(1, true);

		JournalTestUtil.expireArticle(
			_group.getGroupId(), updateArticle, updateArticle.getVersion());

		assertSearchArticle(1, article);
	}

	@Test
	public void testIndexableArticle() throws Exception {
		assertSearchCount(0, true);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		assertSearchCount(1, true);

		article.setIndexable(false);

		article = JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(0, true);

		article.setIndexable(true);

		JournalTestUtil.updateArticle(
			article, article.getTitleMap(), article.getContent(), true, true,
			ServiceContextTestUtil.getServiceContext());

		assertSearchCount(1, true);
	}

	protected void assertSearchArticle(
			long expectedCount, JournalArticle article)
		throws Exception {

		Indexer<JournalArticle> indexer = IndexerRegistryUtil.getIndexer(
			JournalArticle.class);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		Hits results = indexer.search(searchContext);

		List<JournalArticle> articles = _journalHelper.getArticles(results);

		Assert.assertEquals(
			articles.toString(), expectedCount, articles.size());

		JournalArticle searchArticle = articles.get(0);

		Assert.assertEquals(
			searchArticle.toString(), article.getId(), searchArticle.getId());
	}

	protected void assertSearchCount(long expectedCount, boolean head)
		throws Exception {

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

		Assert.assertEquals(
			results.toString(), expectedCount, results.getLength());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalHelper _journalHelper;

	private String _originalPortalPreferencesXML;

}