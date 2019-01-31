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
			_elasticsearchClientResolver, getElasticsearchDocumentFactory(),
			_facetProcessor);
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver,
		ElasticsearchDocumentFactory elasticsearchDocumentFactory,
		FacetProcessor<?> facetProcessor) {

		ClusterRequestExecutorFixture clusterRequestExecutorFixture =
			new ClusterRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		DocumentRequestExecutorFixture documentRequestExecutorFixture =
			new DocumentRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
					setElasticsearchDocumentFactory(
						elasticsearchDocumentFactory);
				}
			};

		IndexRequestExecutorFixture indexRequestExecutorFixture =
			new IndexRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		SearchRequestExecutorFixture searchRequestExecutorFixture =
			new SearchRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
					setFacetProcessor(facetProcessor);
				}
			};

		com.liferay.portal.search.elasticsearch6.internal.search.engine.
			adapter.search2.SearchRequestExecutorFixture
			search2SearchRequestExecutorFixture =
				new com.liferay.portal.search.elasticsearch6.
					internal.search.engine.adapter.search2.
						SearchRequestExecutorFixture(
							elasticsearchClientResolver);

		SnapshotRequestExecutorFixture snapshotRequestExecutorFixture =
			new SnapshotRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		clusterRequestExecutorFixture.setUp();
		documentRequestExecutorFixture.setUp();
		indexRequestExecutorFixture.setUp();
		searchRequestExecutorFixture.setUp();
		snapshotRequestExecutorFixture.setUp();

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setClusterRequestExecutor(
					clusterRequestExecutorFixture.getClusterRequestExecutor());

				setDocumentRequestExecutor(
					documentRequestExecutorFixture.
						getDocumentRequestExecutor());

				setIndexRequestExecutor(
					indexRequestExecutorFixture.getIndexRequestExecutor());

				setSearchRequestExecutor(
					searchRequestExecutorFixture.getSearchRequestExecutor());

				setSearch2SearchRequestExecutor(
					search2SearchRequestExecutorFixture.createExecutor());

				setSnapshotRequestExecutor(
					snapshotRequestExecutorFixture.
						getSnapshotRequestExecutor());
			}
		};
	}

	protected ElasticsearchDocumentFactory getElasticsearchDocumentFactory() {
		if (_elasticsearchDocumentFactory != null) {
			return _elasticsearchDocumentFactory;
		}

		return new DefaultElasticsearchDocumentFactory();
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	protected void setElasticsearchDocumentFactory(
		ElasticsearchDocumentFactory elasticsearchDocumentFactory) {

		_elasticsearchDocumentFactory = elasticsearchDocumentFactory;
	}

	protected void setFacetProcessor(
		FacetProcessor<SearchRequestBuilder> facetProcessor) {

		_facetProcessor = facetProcessor;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private ElasticsearchDocumentFactory _elasticsearchDocumentFactory;
	private FacetProcessor<SearchRequestBuilder> _facetProcessor;
	private SearchEngineAdapter _searchEngineAdapter;

}