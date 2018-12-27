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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch6.internal.facet.FacetProcessor;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.cluster.ClusterRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.document.DocumentRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index.IndexRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.search.SearchRequestExecutorFixture;
import com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.snapshot.SnapshotRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;

import org.elasticsearch.action.search.SearchRequestBuilder;

/**
 * @author Michael C. Han
 */
public class ElasticsearchEngineAdapterFixture {

	public ElasticsearchEngineAdapterFixture(
		ElasticsearchClientResolver elasticsearchClientResolver,
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_searchEngineAdapter = createSearchEngineAdapter(
			elasticsearchClientResolver, facetProcessor);
	}

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	protected SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver,
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				ClusterRequestExecutorFixture clusterRequestExecutorFixture =
					new ClusterRequestExecutorFixture(
						elasticsearchClientResolver);

				clusterRequestExecutor =
					clusterRequestExecutorFixture.createExecutor();

				DocumentRequestExecutorFixture documentRequestExecutorFixture =
					new DocumentRequestExecutorFixture(
						elasticsearchClientResolver);

				documentRequestExecutor =
					documentRequestExecutorFixture.createExecutor();

				IndexRequestExecutorFixture indexRequestExecutorFixture =
					new IndexRequestExecutorFixture(
						elasticsearchClientResolver);

				indexRequestExecutor =
					indexRequestExecutorFixture.createExecutor();

				SearchRequestExecutorFixture searchRequestExecutorFixture =
					new SearchRequestExecutorFixture(
						elasticsearchClientResolver, facetProcessor);

				searchRequestExecutor =
					searchRequestExecutorFixture.createExecutor();

				SnapshotRequestExecutorFixture snapshotRequestExecutorFixture =
					new SnapshotRequestExecutorFixture(
						elasticsearchClientResolver);

				snapshotRequestExecutor =
					snapshotRequestExecutorFixture.createExecutor();
			}
		};
	}

	private final SearchEngineAdapter _searchEngineAdapter;

}