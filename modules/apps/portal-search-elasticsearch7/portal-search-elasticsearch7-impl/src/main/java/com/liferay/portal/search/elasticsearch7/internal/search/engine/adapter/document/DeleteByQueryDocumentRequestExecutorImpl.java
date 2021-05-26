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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.document;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;
import com.liferay.portal.search.query.QueryTranslator;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, service = DeleteByQueryDocumentRequestExecutor.class
)
public class DeleteByQueryDocumentRequestExecutorImpl
	implements DeleteByQueryDocumentRequestExecutor {

	@Override
	public DeleteByQueryDocumentResponse execute(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		DeleteByQueryRequest deleteByQueryRequest = createDeleteByQueryRequest(
			deleteByQueryDocumentRequest);

		BulkByScrollResponse bulkByScrollResponse = getBulkByScrollResponse(
			deleteByQueryRequest, deleteByQueryDocumentRequest);

		TimeValue timeValue = bulkByScrollResponse.getTook();

		return new DeleteByQueryDocumentResponse(
			bulkByScrollResponse.getDeleted(), timeValue.getMillis());
	}

	protected DeleteByQueryRequest createDeleteByQueryRequest(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest();

		deleteByQueryRequest.indices(
			deleteByQueryDocumentRequest.getIndexNames());

		if (deleteByQueryDocumentRequest.getPortalSearchQuery() != null) {
			QueryBuilder queryBuilder = _queryTranslator.translate(
				deleteByQueryDocumentRequest.getPortalSearchQuery());

			deleteByQueryRequest.setQuery(queryBuilder);
		}
		else {
			@SuppressWarnings("deprecation")
			QueryBuilder queryBuilder = _legacyQueryTranslator.translate(
				deleteByQueryDocumentRequest.getQuery(), null);

			deleteByQueryRequest.setQuery(queryBuilder);
		}

		deleteByQueryRequest.setRefresh(
			deleteByQueryDocumentRequest.isRefresh());

		return deleteByQueryRequest;
	}

	protected BulkByScrollResponse getBulkByScrollResponse(
		DeleteByQueryRequest deleteByQueryRequest,
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				deleteByQueryDocumentRequest.getConnectionId(),
				deleteByQueryDocumentRequest.isPreferLocalCluster());

		try {
			return restHighLevelClient.deleteByQuery(
				deleteByQueryRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setLegacyQueryTranslator(
		com.liferay.portal.kernel.search.query.QueryTranslator<QueryBuilder>
			legacyQueryTranslator) {

		_legacyQueryTranslator = legacyQueryTranslator;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private com.liferay.portal.kernel.search.query.QueryTranslator<QueryBuilder>
		_legacyQueryTranslator;
	private QueryTranslator<QueryBuilder> _queryTranslator;

}