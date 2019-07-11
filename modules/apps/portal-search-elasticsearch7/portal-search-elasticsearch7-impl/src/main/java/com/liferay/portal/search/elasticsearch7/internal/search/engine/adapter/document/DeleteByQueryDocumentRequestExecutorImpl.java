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

import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteByQueryDocumentResponse;

import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;

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

		DeleteByQueryRequestBuilder deleteByQueryRequestBuilder =
			createDeleteByQueryRequestBuilder(deleteByQueryDocumentRequest);

		BulkByScrollResponse bulkByScrollResponse =
			deleteByQueryRequestBuilder.get();

		TimeValue timeValue = bulkByScrollResponse.getTook();

		return new DeleteByQueryDocumentResponse(
			bulkByScrollResponse.getDeleted(), timeValue.getMillis());
	}

	protected DeleteByQueryRequestBuilder createDeleteByQueryRequestBuilder(
		DeleteByQueryDocumentRequest deleteByQueryDocumentRequest) {

		DeleteByQueryRequestBuilder deleteByQueryRequestBuilder =
			new DeleteByQueryRequestBuilder(
				_elasticsearchClientResolver.getClient(),
				DeleteByQueryAction.INSTANCE);

		QueryBuilder queryBuilder = _queryTranslator.translate(
			deleteByQueryDocumentRequest.getQuery(), null);

		deleteByQueryRequestBuilder.filter(queryBuilder);

		deleteByQueryRequestBuilder.refresh(
			deleteByQueryDocumentRequest.isRefresh());
		deleteByQueryRequestBuilder.source(
			deleteByQueryDocumentRequest.getIndexNames());

		return deleteByQueryRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(target = "(search.engine.impl=Elasticsearch)", unbind = "-")
	protected void setQueryTranslator(
		QueryTranslator<QueryBuilder> queryTranslator) {

		_queryTranslator = queryTranslator;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private QueryTranslator<QueryBuilder> _queryTranslator;

}