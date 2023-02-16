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

package com.liferay.portal.search.internal.permission;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.RelatedEntryIndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.FacetPostProcessor;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.search.configuration.DefaultSearchResultPermissionFilterConfiguration;
import com.liferay.portal.search.legacy.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchRequestBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.function.Function;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class DefaultSearchResultPermissionFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_mockSearchResultPermissionFilterConfiguration(0, 100);

		SearchRequestBuilderFactory searchRequestBuilderFactory =
			_getSearchRequestBuilderFactory();

		_defaultSearchResultPermissionFilter =
			new DefaultSearchResultPermissionFilter(
				Mockito.mock(FacetPostProcessor.class),
				Mockito.mock(IndexerRegistry.class), _permissionChecker,
				Mockito.mock(Props.class),
				Mockito.mock(RelatedEntryIndexerRegistry.class),
				_searchFunction, searchRequestBuilderFactory,
				_searchResultPermissionFilterConfiguration);
	}

	@Test
	public void testSearchAsGroupAdmin() {
		Hits hits = new HitsImpl();

		Hits spyHits = Mockito.spy(hits);

		SearchContext searchContext = Mockito.mock(SearchContext.class);

		_mockFilterHits(searchContext);

		_mockGetDocuments(0, 4, spyHits);
		_mockGetHits(spyHits, searchContext);
		_mockIsGroupAdmin(true, searchContext);
		_mockQueryConfig(true, searchContext);
		_mockSearchRequest(4, 10);
		_mockStartAndEnd(20, searchContext, 0);

		Hits resultHits = _defaultSearchResultPermissionFilter.search(
			searchContext);

		Document[] docs = resultHits.getDocs();

		Assert.assertEquals(Arrays.toString(docs), 4, docs.length);
	}

	@Test
	public void testSearchWithoutPermission() {
		Hits hits = new HitsImpl();

		Hits spyHits = Mockito.spy(hits);

		SearchContext searchContext = Mockito.mock(SearchContext.class);

		_mockFilterHits(searchContext);

		_mockGetDocuments(2, 2, spyHits);
		_mockGetHits(spyHits, searchContext);
		_mockIsGroupAdmin(false, searchContext);
		_mockQueryConfig(true, searchContext);
		_mockSearchRequest(4, 10);
		_mockStartAndEnd(20, searchContext, 0);

		Hits resultHits = _defaultSearchResultPermissionFilter.search(
			searchContext);

		Document[] docs = resultHits.getDocs();

		Assert.assertEquals(Arrays.toString(docs), 2, docs.length);
	}

	private SearchRequestBuilderFactory _getSearchRequestBuilderFactory() {
		SearchRequestBuilderFactory searchRequestBuilderFactory = Mockito.mock(
			SearchRequestBuilderFactory.class);

		SearchRequestBuilder searchRequestBuilder = Mockito.mock(
			SearchRequestBuilder.class);

		Mockito.when(
			searchRequestBuilderFactory.builder(Mockito.any())
		).thenReturn(
			searchRequestBuilder
		);

		Mockito.when(
			searchRequestBuilder.build()
		).thenReturn(
			_searchRequest
		);

		return searchRequestBuilderFactory;
	}

	private void _mockFilterHits(SearchContext searchContext) {
		Mockito.when(
			_permissionChecker.getCompanyId()
		).thenReturn(
			2L
		);

		Mockito.when(
			_permissionChecker.isGroupAdmin(2L)
		).thenReturn(
			true
		);

		Mockito.when(
			searchContext.getAttribute(Field.STATUS)
		).thenReturn(
			1
		);
	}

	private void _mockGetDocuments(
		int countWithoutPermission, int countWithPermission, Hits hits) {

		Document[] documents =
			new Document[countWithPermission + countWithoutPermission];

		for (int i = 0; i < countWithoutPermission; i++) {
			Document document = _setUpDocument("0", i);

			documents[i] = document;
		}

		for (int i = countWithoutPermission; i < documents.length; i++) {
			Document document = _setUpDocument("2", i);

			documents[i] = document;
		}

		hits.setScores(new float[] {1F, 2F, 3F, 4F});

		hits.setDocs(documents);
	}

	private void _mockGetHits(Hits hits, SearchContext searchContext) {
		Mockito.when(
			_searchFunction.apply(searchContext)
		).thenReturn(
			hits
		);
	}

	private void _mockIsGroupAdmin(
		boolean groupAdmin, SearchContext searchContext) {

		Mockito.when(
			searchContext.getAttribute(Field.GROUP_ID)
		).thenReturn(
			1L
		);

		Mockito.when(
			_permissionChecker.isGroupAdmin(1L)
		).thenReturn(
			groupAdmin
		);
	}

	private void _mockQueryConfig(
		boolean allFieldsSelected, SearchContext searchContext) {

		QueryConfig queryConfig = Mockito.mock(QueryConfig.class);

		Mockito.when(
			searchContext.getQueryConfig()
		).thenReturn(
			queryConfig
		);

		Mockito.when(
			queryConfig.isAllFieldsSelected()
		).thenReturn(
			allFieldsSelected
		);
	}

	private void _mockSearchRequest(int from, int size) {
		Mockito.when(
			_searchRequest.getFrom()
		).thenReturn(
			from
		);

		Mockito.when(
			_searchRequest.getSize()
		).thenReturn(
			size
		);
	}

	private void _mockSearchResultPermissionFilterConfiguration(
		int permissionFilteredSearchResultAccurateCountThreshold,
		int searchQueryResultWindowLimit) {

		Mockito.when(
			_searchResultPermissionFilterConfiguration.
				permissionFilteredSearchResultAccurateCountThreshold()
		).thenReturn(
			permissionFilteredSearchResultAccurateCountThreshold
		);

		Mockito.when(
			_searchResultPermissionFilterConfiguration.
				searchQueryResultWindowLimit()
		).thenReturn(
			searchQueryResultWindowLimit
		);
	}

	private void _mockStartAndEnd(
		int end, SearchContext searchContext, int start) {

		Mockito.when(
			searchContext.getStart()
		).thenReturn(
			start
		);

		Mockito.when(
			searchContext.getEnd()
		).thenReturn(
			end
		);
	}

	private Document _setUpDocument(String companyId, int index) {
		Document document = Mockito.mock(Document.class);

		Mockito.when(
			document.get(Field.COMPANY_ID)
		).thenReturn(
			companyId
		);

		Mockito.when(
			document.get(Field.ENTRY_CLASS_NAME)
		).thenReturn(
			"com.liferay.journal.model.JournalArticle"
		);

		Mockito.when(
			document.get(Field.ENTRY_CLASS_PK)
		).thenReturn(
			String.valueOf(index)
		);

		return document;
	}

	private static final SearchRequest _searchRequest = Mockito.mock(
		SearchRequest.class);

	private DefaultSearchResultPermissionFilter
		_defaultSearchResultPermissionFilter;
	private final PermissionChecker _permissionChecker = Mockito.mock(
		PermissionChecker.class);
	private final Function<SearchContext, Hits> _searchFunction = Mockito.mock(
		Function.class);
	private final DefaultSearchResultPermissionFilterConfiguration
		_searchResultPermissionFilterConfiguration = Mockito.mock(
			DefaultSearchResultPermissionFilterConfiguration.class);

}