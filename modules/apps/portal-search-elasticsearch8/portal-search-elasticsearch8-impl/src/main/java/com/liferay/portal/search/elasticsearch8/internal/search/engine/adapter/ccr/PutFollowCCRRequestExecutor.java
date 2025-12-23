/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.ccr;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.ccr.ElasticsearchCcrClient;
import co.elastic.clients.elasticsearch.ccr.FollowRequest;
import co.elastic.clients.elasticsearch.ccr.FollowResponse;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PutFollowCCRResponse;

import java.io.IOException;

/**
 * @author Bryan Engler
 */
public class PutFollowCCRRequestExecutor {

	public PutFollowCCRRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public PutFollowCCRResponse execute(
		PutFollowCCRRequest putFollowCCRRequest) {

		FollowResponse followResponse = _getFollowResponse(
			_createFollowRequest(putFollowCCRRequest), putFollowCCRRequest);

		return new PutFollowCCRResponse(
			followResponse.followIndexCreated(),
			followResponse.indexFollowingStarted());
	}

	private FollowRequest _createFollowRequest(
		PutFollowCCRRequest putFollowCCRRequest) {

		FollowRequest.Builder builder = new FollowRequest.Builder();

		builder.remoteCluster(putFollowCCRRequest.getRemoteClusterAlias());
		builder.leaderIndex(putFollowCCRRequest.getLeaderIndexName());
		builder.index(putFollowCCRRequest.getFollowerIndexName());

		if (putFollowCCRRequest.getWaitForActiveShards() != 0) {
			builder.waitForActiveShards(
				waitForActiveShards -> waitForActiveShards.count(
					putFollowCCRRequest.getWaitForActiveShards()));
		}

		return builder.build();
	}

	private FollowResponse _getFollowResponse(
		FollowRequest followRequest, PutFollowCCRRequest putFollowCCRRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				putFollowCCRRequest.getConnectionId(),
				putFollowCCRRequest.isPreferLocalCluster());

		ElasticsearchCcrClient elasticsearchCcrClient =
			elasticsearchClient.ccr();

		try {
			return elasticsearchCcrClient.follow(followRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}