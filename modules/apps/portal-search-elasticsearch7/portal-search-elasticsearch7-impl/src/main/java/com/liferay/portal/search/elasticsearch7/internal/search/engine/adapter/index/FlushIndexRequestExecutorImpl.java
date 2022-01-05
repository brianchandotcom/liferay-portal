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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.FlushIndexRequest;
import com.liferay.portal.search.engine.adapter.index.FlushIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndexRequestShardFailure;

import java.io.IOException;

import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = FlushIndexRequestExecutor.class)
public class FlushIndexRequestExecutorImpl
	implements FlushIndexRequestExecutor {

	@Override
	public FlushIndexResponse execute(FlushIndexRequest flushIndexRequest) {
		FlushRequest flushRequest = createFlushRequest(flushIndexRequest);

		FlushResponse flushResponse = _getFlushResponse(
			flushRequest, flushIndexRequest);

		FlushIndexResponse flushIndexResponse = new FlushIndexResponse();

		flushIndexResponse.setFailedShards(flushResponse.getFailedShards());
		flushIndexResponse.setSuccessfulShards(
			flushResponse.getSuccessfulShards());
		flushIndexResponse.setTotalShards(flushResponse.getTotalShards());

		RestStatus restStatus = flushResponse.getStatus();

		flushIndexResponse.setRestStatus(restStatus.getStatus());

		ShardOperationFailedException[] shardOperationFailedExceptions =
			flushResponse.getShardFailures();

		if (ArrayUtil.isNotEmpty(shardOperationFailedExceptions)) {
			for (ShardOperationFailedException shardOperationFailedException :
					shardOperationFailedExceptions) {

				IndexRequestShardFailure indexRequestShardFailure =
					_indexRequestShardFailureTranslator.translate(
						shardOperationFailedException);

				flushIndexResponse.addIndexRequestShardFailure(
					indexRequestShardFailure);
			}
		}

		return flushIndexResponse;
	}

	protected FlushRequest createFlushRequest(
		FlushIndexRequest flushIndexRequest) {

		FlushRequest flushRequest = new FlushRequest();

		flushRequest.force(flushIndexRequest.isForce());
		flushRequest.indices(flushIndexRequest.getIndexNames());
		flushRequest.waitIfOngoing(flushIndexRequest.isWaitIfOngoing());

		return flushRequest;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	@Reference(unbind = "-")
	protected void setIndexRequestShardFailureTranslator(
		IndexRequestShardFailureTranslator indexRequestShardFailureTranslator) {

		_indexRequestShardFailureTranslator =
			indexRequestShardFailureTranslator;
	}

	private FlushResponse _getFlushResponse(
		FlushRequest flushRequest, FlushIndexRequest flushIndexRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				flushIndexRequest.getConnectionId(),
				flushIndexRequest.isPreferLocalCluster());

		IndicesClient indicesClient = restHighLevelClient.indices();

		try {
			return indicesClient.flush(flushRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private IndexRequestShardFailureTranslator
		_indexRequestShardFailureTranslator;

}