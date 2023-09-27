/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.rankings.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Wade Cao
 */
public class SearchRankingResponseTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_searchRankingResponse = new SearchRankingResponse();

		_setUpSearchRankingResponse();
	}

	@Test
	public void testSearchRankingResponseGetter() {
		Assert.assertEquals(_documents, _searchRankingResponse.getDocuments());
		Assert.assertEquals(_KEYWORDS, _searchRankingResponse.getKeywords());
		Assert.assertEquals(
			_PAGINATION_DELTA, _searchRankingResponse.getPaginationDelta());
		Assert.assertEquals(
			_PAGINATION_START, _searchRankingResponse.getPaginationStart());
		Assert.assertEquals(
			_QUERY_STRING, _searchRankingResponse.getQueryString());
		Assert.assertEquals(
			_searchContainer, _searchRankingResponse.getSearchContainer());
		Assert.assertEquals(
			_searchHits, _searchRankingResponse.getSearchHits());
		Assert.assertEquals(
			_searchResponse, _searchRankingResponse.getSearchResponse());
		Assert.assertEquals(_TOTAL_HITS, _searchRankingResponse.getTotalHits());
	}

	private void _setUpSearchRankingResponse() {
		_documents = Arrays.asList(Mockito.mock(Document.class));

		_searchRankingResponse.setDocuments(_documents);

		_searchRankingResponse.setKeywords(_KEYWORDS);
		_searchRankingResponse.setPaginationDelta(_PAGINATION_DELTA);
		_searchRankingResponse.setPaginationStart(_PAGINATION_START);
		_searchRankingResponse.setSearchContainer(_searchContainer);
		_searchRankingResponse.setSearchHits(_searchHits);

		Mockito.doReturn(
			_QUERY_STRING
		).when(
			_searchResponse
		).getRequestString();

		_searchRankingResponse.setSearchResponse(_searchResponse);
		_searchRankingResponse.setTotalHits(_TOTAL_HITS);
	}

	private static final String _KEYWORDS = "Keywords";

	private static final int _PAGINATION_DELTA = 5;

	private static final int _PAGINATION_START = 0;

	private static final String _QUERY_STRING = "queryString";

	private static final int _TOTAL_HITS = 10;

	private List<Document> _documents;
	private final SearchContainer<Document> _searchContainer = Mockito.mock(
		SearchContainer.class);
	private final SearchHits _searchHits = Mockito.mock(SearchHits.class);
	private SearchRankingResponse _searchRankingResponse;
	private final SearchResponse _searchResponse = Mockito.mock(
		SearchResponse.class);

}