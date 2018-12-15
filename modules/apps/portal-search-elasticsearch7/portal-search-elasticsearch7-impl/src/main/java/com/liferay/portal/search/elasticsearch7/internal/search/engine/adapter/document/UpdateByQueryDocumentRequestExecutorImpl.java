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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.query.QueryTranslator;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentResponse;

import java.io.IOException;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	immediate = true, service = UpdateByQueryDocumentRequestExecutor.class
)
public class UpdateByQueryDocumentRequestExecutorImpl
	implements UpdateByQueryDocumentRequestExecutor {

	@Override
	public UpdateByQueryDocumentResponse execute(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		UpdateByQueryRequest updateByQueryRequest = createUpdateByQueryRequest(
			updateByQueryDocumentRequest);

		BulkByScrollResponse bulkByScrollResponse = getBulkByScrollResponse(
			updateByQueryRequest);

		TimeValue timeValue = bulkByScrollResponse.getTook();

		return new UpdateByQueryDocumentResponse(
			bulkByScrollResponse.getUpdated(), timeValue.getMillis());
	}

	protected UpdateByQueryRequest createUpdateByQueryRequest(
		UpdateByQueryDocumentRequest updateByQueryDocumentRequest) {

		UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest();

		QueryBuilder queryBuilder = _queryTranslator.translate(
			updateByQueryDocumentRequest.getQuery(), null);

		updateByQueryRequest.setQuery(queryBuilder);

		updateByQueryRequest.setRefresh(
			updateByQueryDocumentRequest.isRefresh());

		JSONObject jsonObject =
			updateByQueryDocumentRequest.getScriptJSONObject();

		if (jsonObject != null) {
			Script script = new Script(jsonObject.toString());

			updateByQueryRequest.setScript(script);
		}

		updateByQueryRequest.indices(
			updateByQueryDocumentRequest.getIndexNames());

		return updateByQueryRequest;
	}

	protected BulkByScrollResponse getBulkByScrollResponse(
		UpdateByQueryRequest updateByQueryRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		try {
			return restHighLevelClient.updateByQuery(
				updateByQueryRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
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