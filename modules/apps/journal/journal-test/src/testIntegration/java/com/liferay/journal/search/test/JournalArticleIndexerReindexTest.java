/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class JournalArticleIndexerReindexTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testReindex() throws Exception {
		SearchContext searchContext = _getSearchContext("Architectural");

		_assertSearchCount(0, searchContext);

		JournalTestUtil.addArticle(
			_group.getGroupId(), "Liferay Architectural Approach",
			RandomTestUtil.randomString());

		_assertSearchCount(1, searchContext);

		_reindex(true);

		_assertSearchCount(1, searchContext);

		_reindex(false);

		_assertSearchCount(1, searchContext);
	}

	@Test
	public void testReindexWithDefaultValues() throws Exception {
		SearchContext searchContext = _getSearchContext("Architectural");

		_assertSearchCount(0, searchContext);

		JournalTestUtil.addArticleDefaultValues(
			TestPropsValues.getUserId(), _group.getGroupId(),
			"Liferay Architectural Approach");

		_assertSearchCount(0, searchContext);

		_reindex(true);

		_assertSearchCount(0, searchContext);

		_reindex(false);

		_assertSearchCount(0, searchContext);
	}

	private void _assertSearchCount(
			int expectedCount, SearchContext searchContext)
		throws Exception {

		Hits hits = _indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), expectedCount, hits.getLength());
	}

	private SearchContext _getSearchContext(String keywords) throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setAttribute("status", WorkflowConstants.STATUS_APPROVED);
		searchContext.setKeywords(keywords);

		return searchContext;
	}

	private void _reindex(boolean indexAllArticleVersionsEnabled)
		throws Exception {

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						JournalServiceConfiguration.class.getName(),
						HashMapDictionaryBuilder.<String, Object>put(
							"indexAllArticleVersionsEnabled",
							indexAllArticleVersionsEnabled
						).build())) {

			_indexer.reindex(
				new String[] {String.valueOf(TestPropsValues.getCompanyId())});
		}
	}

	@DeleteAfterTestRun
	private Group _group;

	private Indexer<JournalArticle> _indexer;

}