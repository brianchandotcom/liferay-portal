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

package com.liferay.portal.search.elasticsearch6.internal;

import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.constants.SearchContextAttributes;
import com.liferay.portal.search.elasticsearch6.constants.ElasticsearchSearchContextAttributes;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.ClusterResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.engine.adapter.search.SearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotResponse;
import com.liferay.portal.search.internal.legacy.searcher.SearchRequestBuilderFactoryImpl;
import com.liferay.portal.search.internal.legacy.searcher.SearchResponseBuilderFactoryImpl;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;

import com.vividsolutions.jts.util.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Michael C. Han
 */
public class ElasticsearchIndexSearcherTest {

	@Before
	public void setUp() {
		_documentFixture.setUp();

		_testSearchEngineAdapter = new TestSearchEngineAdapter();

		_elasticsearchIndexSearcher = createElasticsearchIndexSearcher();
	}

	@After
	public void tearDown() {
		_documentFixture.tearDown();
	}

	@Test
	public void testSearchContextAttributes() throws SearchException {
		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_BASIC_FACET_SELECTION,
			Boolean.TRUE);
		searchContext.setAttribute(
			SearchContextAttributes.ATTRIBUTE_KEY_LUCENE_SYNTAX, Boolean.TRUE);
		searchContext.setAttribute(
			ElasticsearchSearchContextAttributes.
				ATTRIBUTE_KEY_SEARCH_REQUEST_PREFERENCE,
			"testValue");

		_elasticsearchIndexSearcher.search(searchContext, new MatchAllQuery());

		SearchSearchRequest searchSearchRequest =
			(SearchSearchRequest)_testSearchEngineAdapter.getSearchRequest();

		Assert.isTrue(searchSearchRequest.isBasicFacetSelection());
		Assert.isTrue(searchSearchRequest.isLuceneSyntax());

		String preference = searchSearchRequest.getPreference();

		Assert.equals("testValue", preference);
	}

	protected ElasticsearchIndexSearcher createElasticsearchIndexSearcher() {
		return new ElasticsearchIndexSearcher() {
			{
				indexNameBuilder = String::valueOf;
				props = createProps();

				searchEngineAdapter = _testSearchEngineAdapter;
				searchRequestBuilderFactory =
					new SearchRequestBuilderFactoryImpl();
				searchResponseBuilderFactory =
					new SearchResponseBuilderFactoryImpl();
			}
		};
	}

	protected Props createProps() {
		return PropsTestUtil.setProps(PropsKeys.INDEX_SEARCH_LIMIT, "20");
	}

	private final DocumentFixture _documentFixture = new DocumentFixture();
	private ElasticsearchIndexSearcher _elasticsearchIndexSearcher;
	private TestSearchEngineAdapter _testSearchEngineAdapter;

	private class TestSearchEngineAdapter implements SearchEngineAdapter {

		@Override
		public <T extends ClusterResponse> T execute(
			ClusterRequest<T> clusterRequest) {

			throw new UnsupportedOperationException();
		}

		@Override
		public <S extends DocumentResponse> S execute(
			DocumentRequest<S> documentRequest) {

			throw new UnsupportedOperationException();
		}

		@Override
		public <U extends IndexResponse> U execute(
			IndexRequest<U> indexRequest) {

			throw new UnsupportedOperationException();
		}

		@Override
		public <V extends SearchResponse> V execute(
			SearchRequest<V> searchRequest) {

			SearchRequestExecutor searchRequestExecutor = Mockito.mock(
				SearchRequestExecutor.class);

			SearchSearchResponse searchSearchResponse =
				new SearchSearchResponse();

			searchSearchResponse.setHits(new HitsImpl());

			Mockito.when(
				searchRequestExecutor.executeSearchRequest(
					Matchers.any(SearchSearchRequest.class))
			).thenReturn(
				searchSearchResponse
			);

			_searchRequest = searchRequest;

			return searchRequest.accept(searchRequestExecutor);
		}

		@Override
		public <W extends SnapshotResponse> W execute(
			SnapshotRequest<W> snapshotRequest) {

			throw new UnsupportedOperationException();
		}

		@Override
		public String getQueryString(Query query) {
			throw new UnsupportedOperationException();
		}

		public SearchRequest<?> getSearchRequest() {
			return _searchRequest;
		}

		private SearchRequest<?> _searchRequest;

	}

}