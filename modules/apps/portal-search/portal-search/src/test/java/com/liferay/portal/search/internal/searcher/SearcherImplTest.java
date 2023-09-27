/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
		_setUpHits();

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

		ReflectionTestUtil.setFieldValue(
			_searcherImpl, "_indexerRegistry", _indexerRegistry);
		ReflectionTestUtil.setFieldValue(
			_searcherImpl, "_searchResponseBuilderFactory",
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
			searchDocuments.toString(), expected, searchDocuments.size());
	}

	private SearchRequestImpl _createSearchRequestImpl(
		SearchContext searchContext) {

		SearchRequestImpl searchRequestImpl = new SearchRequestImpl(
			searchContext);

		searchRequestImpl.setModelIndexerClassNames(_MODEL_CLASS_NAME);

		return searchRequestImpl;
	}

	private void _setUpHits() {
		Document document = new DocumentImpl();

		DocumentHelper documentHelper = new DocumentHelper(document);

		documentHelper.setEntryKey(_MODEL_CLASS_NAME, 1);

		_hits.setDocs(new Document[] {document});
	}

	private static final String _MODEL_CLASS_NAME = "modelClassName";

	private final Hits _hits = new HitsImpl();
	private final Indexer<Object> _indexer = Mockito.mock(Indexer.class);
	private final IndexerRegistry _indexerRegistry = Mockito.mock(
		IndexerRegistry.class);
	private SearcherImpl _searcherImpl;

}