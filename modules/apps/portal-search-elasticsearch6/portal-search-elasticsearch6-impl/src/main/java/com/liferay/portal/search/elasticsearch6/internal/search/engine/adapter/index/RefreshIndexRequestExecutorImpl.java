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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.index;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.index.IndexRequestShardFailure;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexRequest;
import com.liferay.portal.search.engine.adapter.index.RefreshIndexResponse;

import org.elasticsearch.action.ShardOperationFailedException;
import org.elasticsearch.action.admin.indices.refresh.RefreshAction;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequestBuilder;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.client.Client;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = RefreshIndexRequestExecutor.class)
public class RefreshIndexRequestExecutorImpl
	implements RefreshIndexRequestExecutor {

	@Override
	public RefreshIndexResponse execute(
		RefreshIndexRequest refreshIndexRequest) {

		RefreshRequestBuilder refreshRequestBuilder =
			createRefreshRequestBuilder(refreshIndexRequest);

		RefreshResponse refreshResponse = refreshRequestBuilder.get();

		RefreshIndexResponse refreshIndexResponse = new RefreshIndexResponse();

		refreshIndexResponse.setFailedShards(refreshResponse.getFailedShards());
		refreshIndexResponse.setSuccessfulShards(
			refreshResponse.getSuccessfulShards());
		refreshIndexResponse.setTotalShards(refreshResponse.getTotalShards());

		ShardOperationFailedException[] shardOperationFailedExceptions =
			refreshResponse.getShardFailures();

		if (ArrayUtil.isNotEmpty(shardOperationFailedExceptions)) {
			for (ShardOperationFailedException shardOperationFailedException :
					shardOperationFailedExceptions) {

				IndexRequestShardFailure indexRequestShardFailure =
					indexRequestShardFailureTranslator.translate(
						shardOperationFailedException);

				refreshIndexResponse.addIndexRequestShardFailure(
					indexRequestShardFailure);
			}
		}

		return refreshIndexResponse;
	}

	protected RefreshRequestBuilder createRefreshRequestBuilder(
		RefreshIndexRequest refreshIndexRequest) {

		Client client = elasticsearchClientResolver.getClient();

		RefreshRequestBuilder refreshRequestBuilder =
			RefreshAction.INSTANCE.newRequestBuilder(client);

		refreshRequestBuilder.setIndices(refreshIndexRequest.getIndexNames());

		return refreshRequestBuilder;
	}

	@Reference
	protected ElasticsearchClientResolver elasticsearchClientResolver;

	@Reference
	protected IndexRequestShardFailureTranslator
		indexRequestShardFailureTranslator;

}