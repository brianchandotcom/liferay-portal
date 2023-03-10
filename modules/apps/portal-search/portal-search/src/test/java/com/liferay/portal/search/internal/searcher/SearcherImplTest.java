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

package com.liferay.portal.search.internal.searcher;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
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
 * @author Joshua Cords
 */
public class SearcherImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Document[] documents = new Document[1];

		documents[0] = _createDocument(_MODEL_CLASS_NAME, 1);

		_hits.setDocs(documents);

		Mockito.when(
			_indexer.search(Mockito.any())
		).thenReturn(
			_hits
		);

		Mockito.when(
			_indexerRegistry.getIndexer(_MODEL_CLASS_NAME)
		).thenReturn(
			_indexer
		);

		_searcherImpl = new SearcherImpl();

		_searcherImpl.setIndexerRegistry(_indexerRegistry);
		_searcherImpl.setSearchResponseBuilderFactory(
			new SearchResponseBuilderFactoryImpl());
	}

	@Test
	public void testEmptySearchWithAttribute() throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH, Boolean.TRUE);

		SearchResponse searchResponse = _searcherImpl.doSearch(
			_createSearchRequestImpl(searchContext));

		_assertDocumentReturned(searchResponse, 1);
	}

	@Test
	public void testEmptySearchWithoutAttribute() throws Exception {
		SearchResponse searchResponse = _searcherImpl.doSearch(
			_createSearchRequestImpl(new SearchContext()));

		_assertDocumentReturned(searchResponse, 0);
	}

	@Test
	public void testSearchWithoutAttribute() throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setKeywords("keyword");

		SearchResponse searchResponse = _searcherImpl.doSearch(
			_createSearchRequestImpl(searchContext));

		_assertDocumentReturned(searchResponse, 1);
	}

	private void _assertDocumentReturned(
		SearchResponse searchResponse, int expected) {

		List<Document> searchDocuments = searchResponse.getDocuments71();

		Assert.assertEquals(
			"Search ignored keywords and/or " +
				"SearchContextAttributes.ATTRIBUTE_KEY_EMPTY_SEARCH attribute",
			expected, searchDocuments.size());
	}

	private Document _createDocument(String entryClassName, long entryClassPK) {
		Document document = new DocumentImpl();

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(entryClassName, entryClassPK);

		return document;
	}

	private SearchRequestImpl _createSearchRequestImpl(
		SearchContext searchContext) {

		SearchRequestImpl searchRequestImpl = new SearchRequestImpl(
			searchContext);

		searchRequestImpl.setModelIndexerClassNames(_MODEL_CLASS_NAME);

		return searchRequestImpl;
	}

	private static final String _MODEL_CLASS_NAME = "modelClassName";

	private final Hits _hits = new HitsImpl();
	private final Indexer<Object> _indexer = Mockito.mock(Indexer.class);
	private final IndexerRegistry _indexerRegistry = Mockito.mock(
		IndexerRegistry.class);
	private SearcherImpl _searcherImpl;

}