/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseSearcher;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;
import com.liferay.wiki.test.util.WikiTestUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class WikiPageTitleSearcherTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = UserTestUtil.addUser();

		_node = WikiTestUtil.addNode(_group.getGroupId());

		_searchContext = getSearchContext(_group);

		_searchContext.setNodeIds(new long[] {_node.getNodeId()});
	}

	@Test
	public void testBasicSearchWithOneTerm() throws Exception {
		addPage("Barcelona", RandomTestUtil.randomString());
		addPage("Gijon", RandomTestUtil.randomString());
		addPage("Gijon (Asturias)", RandomTestUtil.randomString());
		addPage("Madrid", RandomTestUtil.randomString());

		assertSearch("Asturias", Arrays.asList("Gijon (Asturias)"));
		assertSearch("Gijon", Arrays.asList("Gijon", "Gijon (Asturias)"));
		assertSearch("Madrid", Arrays.asList("Madrid"));
	}

	@Test
	public void testBasicSearchWithOneTermOnlyByTitle() throws Exception {
		addPage("Barcelona", "Spanish city");
		addPage("Madrid", "Spanish city");

		assertSearch("city", Arrays.asList());
	}

	@Test
	public void testBasicSearchWithOneTermOnlyInCurrentNode() throws Exception {
		addPage("Barcelona", RandomTestUtil.randomString());
		addPage("Madrid", RandomTestUtil.randomString());

		WikiNode node = WikiTestUtil.addNode(_group.getGroupId());

		addPage(node.getNodeId(), "Barcelona", RandomTestUtil.randomString());

		assertSearch("Barcelona", Arrays.asList("Barcelona"));
		assertSearchNode("Barcelona", _node.getNodeId());
	}

	@Test
	public void testLikeSearchWithOneTerm() throws Exception {
		addPage("Gejon", RandomTestUtil.randomString());
		addPage("Gijom", RandomTestUtil.randomString());
		addPage("Gijon", RandomTestUtil.randomString());
		addPage("Gijun", RandomTestUtil.randomString());
		addPage("Gikon", RandomTestUtil.randomString());

		assertSearch(
			"G", Arrays.asList("Gejon", "Gijom", "Gijon", "Gijun", "Gikon"));
		assertSearch("Gi", Arrays.asList("Gijom", "Gijon", "Gijun", "Gikon"));
		assertSearch("Gij", Arrays.asList("Gijom", "Gijon", "Gijun"));
		assertSearch("Gijo", Arrays.asList("Gijom", "Gijon"));
		assertSearch("Gijon", Arrays.asList("Gijon"));
	}

	protected void addPage(long nodeId, String title, String content)
		throws Exception {

		WikiTestUtil.addPage(
			_user.getUserId(), nodeId, title, content, true,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), _user.getUserId()));
	}

	protected void addPage(String title, String content) throws Exception {
		addPage(_node.getNodeId(), title, content);
	}

	protected void assertSearch(String keywords, List<String> expectedValues)
		throws Exception {

		Hits hits = search(keywords);

		DocumentsAssert.assertValuesIgnoreRelevance(
			getRequestString(), hits.getDocs(), Field.TITLE, expectedValues);
	}

	protected void assertSearchNode(String keywords, long nodeId)
		throws Exception {

		Hits hits = search(keywords);

		List<SearchResult> searchResults = SearchResultUtil.getSearchResults(
			hits, LocaleUtil.getDefault());

		for (SearchResult searchResult : searchResults) {
			WikiPage page = wikiPageLocalService.getPage(
				searchResult.getClassPK());

			Assert.assertEquals(nodeId, page.getNodeId());
		}
	}

	protected String getRequestString() {
		return searchResponseBuilderFactory.builder(
			_searchContext
		).build(
		).getRequestString();
	}

	protected SearchContext getSearchContext(Group group) throws Exception {
		return SearchContextTestUtil.getSearchContext(group.getGroupId());
	}

	protected Hits search(String keywords) throws Exception {
		_searchContext.setKeywords(StringUtil.toLowerCase(keywords));

		return _wikiPageSearcher.search(_searchContext);
	}

	@Inject
	protected static SearchResponseBuilderFactory searchResponseBuilderFactory;

	@Inject
	protected static WikiPageLocalService wikiPageLocalService;

	@Inject(filter = "model.class.name=com.liferay.wiki.model.WikiPage")
	private static BaseSearcher _wikiPageSearcher;

	@DeleteAfterTestRun
	private Group _group;

	private WikiNode _node;
	private SearchContext _searchContext;

	@DeleteAfterTestRun
	private User _user;

}