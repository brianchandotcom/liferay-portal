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

import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.legacy.searcher.SearchResponseBuilderFactory;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.SearchResponseBuilder;

/**
 * @author André de Oliveira
 */
public class IndexerSearch {

	public IndexerSearch(
		Class<?> clazz, SearchContext searchContext,
		SearchRequest searchRequest, IndexerRegistry indexerRegistry,
		SearchResponseBuilderFactory searchResponseBuilderFactory) {

		_clazz = clazz;
		_searchContext = searchContext;
		_searchRequest = searchRequest;
		_indexerRegistry = indexerRegistry;
		_searchResponseBuilderFactory = searchResponseBuilderFactory;
	}

	public SearchResponse search() {
		Indexer indexer = _indexerRegistry.getIndexer(_clazz);

		Hits hits = search(indexer);

		SearchResponseBuilder searchResponseBuilder =
			_searchResponseBuilderFactory.getSearchResponseBuilder(
				_searchContext);

		return searchResponseBuilder.hits(
			hits
		).request(
			_searchRequest
		).build();
	}

	protected Hits search(Indexer indexer) {
		try {
			return indexer.search(_searchContext);
		}
		catch (SearchException se) {
			Throwable t = se.getCause();

			if (t instanceof RuntimeException) {
				throw (RuntimeException)t;
			}

			throw new RuntimeException(t);
		}
	}

	private final Class<?> _clazz;
	private final IndexerRegistry _indexerRegistry;
	private final SearchContext _searchContext;
	private final SearchRequest _searchRequest;
	private final SearchResponseBuilderFactory _searchResponseBuilderFactory;

}