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
import com.liferay.portal.search.elasticsearch6.internal.document.DefaultElasticsearchDocumentFactory;
import com.liferay.portal.search.elasticsearch6.internal.document.ElasticsearchDocumentFactory;
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

	public SearchEngineAdapter getSearchEngineAdapter() {
		return _searchEngineAdapter;
	}

	public void setUp() {
		_searchEngineAdapter = createSearchEngineAdapter(
			elasticsearchClientResolver, getElasticsearchDocumentFactory(),
			facetProcessor);
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver1,
		ElasticsearchDocumentFactory elasticsearchDocumentFactory1,
		FacetProcessor facetProcessor1) {

		ClusterRequestExecutorFixture clusterRequestExecutorFixture =
			new ClusterRequestExecutorFixture() {
				{
					elasticsearchClientResolver = elasticsearchClientResolver1;
				}
			};

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					elasticsearchClientResolver = elasticsearchClientResolver1;
					elasticsearchDocumentFactory =
						elasticsearchDocumentFactory1;
				}
			};

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture() {
				{
					elasticsearchClientResolver = elasticsearchClientResolver1;
				}
			};

		SearchRequestExecutorFixture searchRequestExecutorFixture =
			new SearchRequestExecutorFixture() {
				{
					elasticsearchClientResolver = elasticsearchClientResolver1;
					facetProcessor = facetProcessor1;
				}
			};

		SnapshotRequestExecutorFixture snapshotRequestExecutorFixture =
			new SnapshotRequestExecutorFixture() {
				{
					elasticsearchClientResolver = elasticsearchClientResolver1;
				}
			};

		clusterRequestExecutorFixture.setUp();
		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
		searchRequestExecutorFixture.setUp();
		snapshotRequestExecutorFixture.setUp();

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				clusterRequestExecutor =
					clusterRequestExecutorFixture.getClusterRequestExecutor();

				documentRequestExecutor =
					documentRequestExecutorFixture.getDocumentRequestExecutor();

				indexRequestExecutor =
					indexRequestExecutorFixture.getIndexRequestExecutor();

				searchRequestExecutor =
					searchRequestExecutorFixture.getSearchRequestExecutor();

				snapshotRequestExecutor =
					snapshotRequestExecutorFixture.getSnapshotRequestExecutor();
			}
		};
	}

	protected ElasticsearchDocumentFactory getElasticsearchDocumentFactory() {
		if (elasticsearchDocumentFactory != null) {
			return elasticsearchDocumentFactory;
		}

		return new DefaultElasticsearchDocumentFactory();
	}

	protected ElasticsearchClientResolver elasticsearchClientResolver;
	protected ElasticsearchDocumentFactory elasticsearchDocumentFactory;
	protected FacetProcessor<SearchRequestBuilder> facetProcessor;

	private SearchEngineAdapter _searchEngineAdapter;

}