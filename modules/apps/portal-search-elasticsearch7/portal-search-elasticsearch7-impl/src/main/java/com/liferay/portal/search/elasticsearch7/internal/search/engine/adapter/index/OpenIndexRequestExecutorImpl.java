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
import com.liferay.portal.search.engine.adapter.index.IndicesOptions;
import com.liferay.portal.search.engine.adapter.index.OpenIndexRequest;
import com.liferay.portal.search.engine.adapter.index.OpenIndexResponse;

import java.io.IOException;

import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = OpenIndexRequestExecutor.class)
public class OpenIndexRequestExecutorImpl implements OpenIndexRequestExecutor {

	@Override
	public OpenIndexResponse execute(OpenIndexRequest openIndexRequest) {
		org.elasticsearch.action.admin.indices.open.OpenIndexRequest
			elasticsearchOpenIndexRequest = createOpenIndexRequest(
				openIndexRequest);

		AcknowledgedResponse acknowledgedResponse = getAcknowledgedResponse(
			elasticsearchOpenIndexRequest, openIndexRequest);

		return new OpenIndexResponse(acknowledgedResponse.isAcknowledged());
	}

	protected org.elasticsearch.action.admin.indices.open.OpenIndexRequest
		createOpenIndexRequest(OpenIndexRequest openIndexRequest) {

		org.elasticsearch.action.admin.indices.open.OpenIndexRequest
			elasticsearchOpenIndexRequest =
				new org.elasticsearch.action.admin.indices.open.
					OpenIndexRequest();

		elasticsearchOpenIndexRequest.indices(openIndexRequest.getIndexNames());

		IndicesOptions indicesOptions = openIndexRequest.getIndicesOptions();

		if (indicesOptions != null) {
			elasticsearchOpenIndexRequest.indicesOptions(
				_indicesOptionsTranslator.translate(indicesOptions));
		}

		if (openIndexRequest.getTimeout() > 0) {
			TimeValue timeValue = TimeValue.timeValueMillis(
				openIndexRequest.getTimeout());

			elasticsearchOpenIndexRequest.masterNodeTimeout(timeValue);
			elasticsearchOpenIndexRequest.timeout(timeValue);
		}

		if (openIndexRequest.getWaitForActiveShards() > 0) {
			elasticsearchOpenIndexRequest.waitForActiveShards(
				openIndexRequest.getWaitForActiveShards());
		}

		return elasticsearchOpenIndexRequest;
	}

	protected AcknowledgedResponse getAcknowledgedResponse(
		org.elasticsearch.action.admin.indices.open.OpenIndexRequest
			elasticsearchOpenIndexRequest,
		OpenIndexRequest openIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				openIndexRequest.getConnectionId(),
				openIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.open(
				elasticsearchOpenIndexRequest, RequestOptions.DEFAULT);
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

	@Reference(unbind = "-")
	protected void setIndicesOptionsTranslator(
		IndicesOptionsTranslator indicesOptionsTranslator) {

		_indicesOptionsTranslator = indicesOptionsTranslator;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndicesOptionsTranslator _indicesOptionsTranslator;

}