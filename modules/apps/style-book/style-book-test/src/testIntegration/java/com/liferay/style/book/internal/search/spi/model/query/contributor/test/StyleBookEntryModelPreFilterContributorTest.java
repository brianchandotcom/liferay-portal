/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.internal.search.spi.model.query.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.search.test.rule.SearchTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.service.StyleBookEntryLocalService;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Luis Ortiz
 */
@RunWith(Arquillian.class)
public class StyleBookEntryModelPreFilterContributorTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testDraftIsFilteredWhenHeadAttributeNotDefined()
		throws Exception {

		StyleBookEntry styleBookEntry = _addStyleBookEntry();

		_indexDraft(styleBookEntry);

		SearchResponse searchResponse = _search(
			null, styleBookEntry.getStyleBookEntryKey());

		List<Document> documents = searchResponse.getDocuments();

		Assert.assertEquals(documents.toString(), 1, documents.size());

		Document document = documents.get(0);

		Assert.assertEquals(
			Boolean.TRUE.toString(), document.getString("head"));
	}

	@Test
	public void testDraftIsReturnedWhenHeadAttributeFalse() throws Exception {
		StyleBookEntry styleBookEntry = _addStyleBookEntry();

		_indexDraft(styleBookEntry);

		SearchResponse searchResponse = _search(
			Boolean.FALSE, styleBookEntry.getStyleBookEntryKey());

		List<Document> documents = searchResponse.getDocuments();

		Assert.assertEquals(documents.toString(), 2, documents.size());
	}

	@Rule
	public SearchTestRule searchTestRule = new SearchTestRule();

	private StyleBookEntry _addStyleBookEntry() throws Exception {
		return _styleBookEntryLocalService.addStyleBookEntry(
			RandomTestUtil.randomString(), TestPropsValues.getUserId(),
			_group.getGroupId(), false, null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	private void _indexDraft(StyleBookEntry headStyleBookEntry)
		throws Exception {

		StyleBookEntry draftStyleBookEntry =
			_styleBookEntryLocalService.getDraft(headStyleBookEntry);

		Indexer<StyleBookEntry> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(StyleBookEntry.class);

		indexer.reindex(draftStyleBookEntry);
	}

	private SearchResponse _search(Boolean head, String searchTerm)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

		searchContext.setKeywords(searchTerm);

		if (head != null) {
			searchContext.setAttribute("head", head);
		}

		return _searcher.search(
			_searchRequestBuilderFactory.builder(
				searchContext
			).fields(
				StringPool.STAR
			).modelIndexerClasses(
				StyleBookEntry.class
			).build());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Searcher _searcher;

	@Inject
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

	@Inject
	private StyleBookEntryLocalService _styleBookEntryLocalService;

}