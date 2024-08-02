/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
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
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Gustavo Lima
 */
@RunWith(Arquillian.class)
public class WikiPageIndexerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_wikiNode = WikiTestUtil.addNode(_group.getGroupId());
	}

	@Test
	public void testWikiHeadVersionIsTheOnlySearchable() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());
		String title = RandomTestUtil.randomString();

		searchContext.setKeywords(title);

		assertSearchCount(0, _group.getGroupId(), searchContext);

		WikiPage wikiPage = WikiTestUtil.addPage(
			TestPropsValues.getUserId(), _group.getGroupId(),
			_wikiNode.getNodeId(), title, true);

		assertSearchCount(1, _group.getGroupId(), searchContext);

		wikiPage = WikiTestUtil.updatePage(
			wikiPage, TestPropsValues.getUserId(), title,
			RandomTestUtil.randomString(), true,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		assertSearchCount(1, _group.getGroupId(), searchContext);

		WikiPageLocalServiceUtil.deletePage(wikiPage);

		assertSearchCount(0, _group.getGroupId(), searchContext);
	}

	protected void assertSearchCount(
			int expectedCount, long groupId, SearchContext searchContext)
		throws Exception {

		Indexer<WikiPage> indexer = IndexerRegistryUtil.getIndexer(
			WikiPage.class);

		searchContext.setAttribute("head", Boolean.FALSE);
		searchContext.setAttribute("status", WorkflowConstants.STATUS_ANY);
		searchContext.setGroupIds(new long[] {groupId});

		Hits hits = indexer.search(searchContext);

		Assert.assertEquals(hits.toString(), expectedCount, hits.getLength());
	}

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _wikiNode;

}