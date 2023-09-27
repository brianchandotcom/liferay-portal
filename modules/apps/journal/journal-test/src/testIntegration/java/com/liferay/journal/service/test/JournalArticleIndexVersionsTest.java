/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactory;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.util.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		PortalPreferences portalPreferences =
			_portletPreferencesFactory.getPortalPreferences(
				TestPropsValues.getUserId(), true);

		_originalPortalPreferencesXML = _portletPreferencesFactory.toXML(
			portalPreferences);

		portalPreferences.setValue(
			"", "expireAllArticleVersionsEnabled", "true");
		portalPreferences.setValue(
			"", "indexAllArticleVersionsEnabled", "false");

		_portalPreferencesLocalService.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferences));
	}

	@After
	public void tearDown() throws Exception {
		_portalPreferencesLocalService.updatePreferences(
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

		_journalArticleLocalService.deleteArticle(
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

		_journalArticleLocalService.deleteArticle(
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

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	protected void assertSearchArticle(
			long expectedCount, JournalArticle article)
		throws Exception {

		Indexer<JournalArticle> indexer = _indexerRegistry.getIndexer(
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

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		if (!head) {
			searchContext.setAttribute(
				Field.STATUS, WorkflowConstants.STATUS_ANY);
			searchContext.setAttribute("head", Boolean.FALSE);
		}

		searchContext.setGroupIds(new long[] {_group.getGroupId()});

		SearchResponse searchResponse = _searcher.search(
			_searchRequestBuilderFactory.builder(
				searchContext
			).emptySearchEnabled(
				true
			).modelIndexerClasses(
				JournalArticle.class
			).build());

		Assert.assertEquals(
			searchResponse.getRequestString() + "->" +
				searchResponse.getDocuments(),
			expectedCount, searchResponse.getCount());
	}

	@Inject
	private static Searcher _searcher;

	@Inject
	private static SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private IndexerRegistry _indexerRegistry;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalHelper _journalHelper;

	private String _originalPortalPreferencesXML;

	@Inject
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Inject
	private PortletPreferencesFactory _portletPreferencesFactory;

}