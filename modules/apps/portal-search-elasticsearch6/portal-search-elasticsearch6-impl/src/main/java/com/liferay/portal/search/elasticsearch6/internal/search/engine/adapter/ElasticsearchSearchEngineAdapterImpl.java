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

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.engine.adapter.cluster.ClusterResponse;
import com.liferay.portal.search.engine.adapter.document.DocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DocumentRequestExecutor;
import com.liferay.portal.search.engine.adapter.document.DocumentResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndexRequestExecutor;
import com.liferay.portal.search.engine.adapter.index.IndexResponse;
import com.liferay.portal.search.engine.adapter.search2.SearchRequest;
import com.liferay.portal.search.engine.adapter.search2.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search2.SearchResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotResponse;

import org.elasticsearch.index.query.QueryBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, property = "search.engine.impl=Elasticsearch",
	service = SearchEngineAdapter.class
)
public class ElasticsearchSearchEngineAdapterImpl
	implements SearchEngineAdapter {

	@Override
	public
		<V extends
			com.liferay.portal.search.engine.adapter.search.SearchResponse> V
			execute(
				com.liferay.portal.search.engine.adapter.search.SearchRequest<V>
					searchRequest) {

		return searchRequest.accept(_searchRequestExecutor);
	}

	@Override
	public <T extends ClusterResponse> T execute(
		ClusterRequest<T> clusterRequest) {

		return _clusterRequestExecutor.execute(clusterRequest);
	}

	@Override
	public <S extends DocumentResponse> S execute(
		DocumentRequest<S> documentRequest) {

		return documentRequest.accept(_documentRequestExecutor);
	}

	@Override
	public <U extends IndexResponse> U execute(IndexRequest<U> indexRequest) {
		return indexRequest.accept(_indexRequestExecutor);
	}

	@Override
	public <V extends SearchResponse> V execute(
		SearchRequest<V> searchRequest) {

		return searchRequest.accept(_search2SearchRequestExecutor);
	}

	@Override
	public <W extends SnapshotResponse> W execute(
		SnapshotRequest<W> snapshotRequest) {

		return snapshotRequest.accept(_snapshotRequestExecutor);
	}

	@Override
	public String getQueryString(Query query) {
		QueryBuilder queryBuilder = _queryTranslator.translate(query, null);

		return queryBuilder.toString();
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setClusterRequestExecutor(
		ClusterRequestExecutor clusterRequestExecutor) {

		_clusterRequestExecutor = clusterRequestExecutor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setDocumentRequestExecutor(
		DocumentRequestExecutor documentRequestExecutor) {

		_documentRequestExecutor = documentRequestExecutor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setIndexRequestExecutor(
		IndexRequestExecutor indexRequestExecutor) {

		_indexRequestExecutor = indexRequestExecutor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSearch2SearchRequestExecutor(
		SearchRequestExecutor search2SearchRequestExecutor) {

		_search2SearchRequestExecutor = search2SearchRequestExecutor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSearchRequestExecutor(
		com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor
			searchRequestExecutor) {

		_searchRequestExecutor = searchRequestExecutor;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setSnapshotRequestExecutor(
		SnapshotRequestExecutor snapshotRequestExecutor) {

		_snapshotRequestExecutor = snapshotRequestExecutor;
	}

	private ClusterRequestExecutor _clusterRequestExecutor;
	private DocumentRequestExecutor _documentRequestExecutor;
	private IndexRequestExecutor _indexRequestExecutor;
	private QueryTranslator<QueryBuilder> _queryTranslator;
	private SearchRequestExecutor _search2SearchRequestExecutor;
	private com.liferay.portal.search.engine.adapter.search.
		SearchRequestExecutor _searchRequestExecutor;
	private SnapshotRequestExecutor _snapshotRequestExecutor;

}