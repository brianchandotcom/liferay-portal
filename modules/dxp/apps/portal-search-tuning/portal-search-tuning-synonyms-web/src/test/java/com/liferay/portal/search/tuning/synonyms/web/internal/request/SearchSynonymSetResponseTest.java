/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.tuning.synonyms.web.internal.request;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

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
public class SearchSynonymSetResponseTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_searchSynonymSetResponse = new SearchSynonymSetResponse();
	}

	@Test
	public void testGetterSetter() {
		_searchSynonymSetResponse.setDocuments(_documents);
		_searchSynonymSetResponse.setKeywords("keywords");
		_searchSynonymSetResponse.setPaginationDelta(1);
		_searchSynonymSetResponse.setPaginationStart(0);
		_searchSynonymSetResponse.setSearchContainer(_searchContainer);
		_searchSynonymSetResponse.setSearchHits(_searchHits);
		_searchSynonymSetResponse.setSearchResponse(_searchResponse);
		_searchSynonymSetResponse.setTotalHits(10);

		Assert.assertEquals(
			_documents, _searchSynonymSetResponse.getDocuments());
		Assert.assertEquals(
			"keywords", _searchSynonymSetResponse.getKeywords());
		Assert.assertEquals(1, _searchSynonymSetResponse.getPaginationDelta());
		Assert.assertEquals(0, _searchSynonymSetResponse.getPaginationStart());
		Assert.assertEquals(
			_searchContainer, _searchSynonymSetResponse.getSearchContainer());
		Assert.assertEquals(
			_searchHits, _searchSynonymSetResponse.getSearchHits());
		Assert.assertEquals(
			_searchResponse, _searchSynonymSetResponse.getSearchResponse());
		Assert.assertEquals(10, _searchSynonymSetResponse.getTotalHits());
	}

	private final List<Document> _documents = Mockito.mock(List.class);
	private final SearchContainer<Document> _searchContainer = Mockito.mock(
		SearchContainer.class);
	private final SearchHits _searchHits = Mockito.mock(SearchHits.class);
	private final SearchResponse _searchResponse = Mockito.mock(
		SearchResponse.class);
	private SearchSynonymSetResponse _searchSynonymSetResponse;

}