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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.ccr;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRResponse;

import java.io.IOException;

import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.CcrClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.ccr.PutFollowRequest;
import org.elasticsearch.client.ccr.PutFollowResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(service = PutFollowCCRRequestExecutor.class)
public class PutFollowCCRRequestExecutorImpl
	implements PutFollowCCRRequestExecutor {

	@Override
	public PutFollowCCRResponse execute(
		PutFollowCCRRequest putFollowCCRRequest) {

		PutFollowRequest putFollowRequest = _createPutFollowRequest(
			putFollowCCRRequest);

		PutFollowResponse putFollowResponse = _getPutFollowResponse(
			putFollowRequest, putFollowCCRRequest);

		return new PutFollowCCRResponse(
			putFollowResponse.isFollowIndexCreated(),
			putFollowResponse.isIndexFollowingStarted());
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private PutFollowRequest _createPutFollowRequest(
		PutFollowCCRRequest putFollowCCRRequest) {

		if (putFollowCCRRequest.getWaitForActiveShards() != 0) {
			return new PutFollowRequest(
				putFollowCCRRequest.getRemoteClusterAlias(),
				putFollowCCRRequest.getLeaderIndexName(),
				putFollowCCRRequest.getFollowerIndexName(),
				ActiveShardCount.from(
					putFollowCCRRequest.getWaitForActiveShards()));
		}

		return new PutFollowRequest(
			putFollowCCRRequest.getRemoteClusterAlias(),
			putFollowCCRRequest.getLeaderIndexName(),
			putFollowCCRRequest.getFollowerIndexName());
	}

	private PutFollowResponse _getPutFollowResponse(
		PutFollowRequest putFollowRequest,
		PutFollowCCRRequest putFollowCCRRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				putFollowCCRRequest.getConnectionId(),
				putFollowCCRRequest.isPreferLocalCluster());

		CcrClient ccrClient = restHighLevelClient.ccr();

		try {
			return ccrClient.putFollow(
				putFollowRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}