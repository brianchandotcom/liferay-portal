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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.index;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexResponse;

import java.io.IOException;

import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DeleteIndexRequestExecutor.class)
public class DeleteIndexRequestExecutorImpl
	implements DeleteIndexRequestExecutor {

	@Override
	public DeleteIndexResponse execute(DeleteIndexRequest deleteIndexRequest) {
		org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
			elasticsearchDeleteIndexRequest = createDeleteIndexRequest(
				deleteIndexRequest);

		AcknowledgedResponse acknowledgedResponse = getAcknowledgedResponse(
			elasticsearchDeleteIndexRequest);

		return new DeleteIndexResponse(acknowledgedResponse.isAcknowledged());
	}

	protected org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
		createDeleteIndexRequest(DeleteIndexRequest deleteIndexRequest) {

		org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
			elasticsearchDeleteIndexRequest =
				new org.elasticsearch.action.admin.indices.delete.
					DeleteIndexRequest(deleteIndexRequest.getIndexNames());

		IndicesOptions indicesOptions = _indicesOptionsTranslator.translate(
			deleteIndexRequest.getIndicesOptions());

		elasticsearchDeleteIndexRequest.indicesOptions(indicesOptions);

		return elasticsearchDeleteIndexRequest;
	}

	protected AcknowledgedResponse getAcknowledgedResponse(
		org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest
			elasticsearchDeleteIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.delete(
				elasticsearchDeleteIndexRequest, RequestOptions.DEFAULT);
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

	@Reference(unbind = "-")
	protected void setIndicesOptionsTranslator(
		IndicesOptionsTranslator indicesOptionsTranslator) {

		_indicesOptionsTranslator = indicesOptionsTranslator;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndicesOptionsTranslator _indicesOptionsTranslator;

}