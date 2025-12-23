/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.ccr;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.ccr.ElasticsearchCcrClient;
import co.elastic.clients.elasticsearch.ccr.FollowInfoRequest;
import co.elastic.clients.elasticsearch.ccr.FollowInfoResponse;
import co.elastic.clients.elasticsearch.ccr.follow_info.FollowerIndex;
import co.elastic.clients.elasticsearch.ccr.follow_info.FollowerIndexStatus;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoCCRResponse;
import com.liferay.portal.search.engine.adapter.ccr.FollowInfoStatus;

import java.io.IOException;

import java.util.List;

/**
 * @author Bryan Engler
 */
public class FollowInfoCCRRequestExecutor {

	public FollowInfoCCRRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public FollowInfoCCRResponse execute(
		FollowInfoCCRRequest followInfoCCRRequest) {

		FollowInfoResponse followInfoResponse = _getFollowInfoResponse(
			followInfoCCRRequest,
			FollowInfoRequest.of(
				builder -> builder.index(followInfoCCRRequest.getIndexName())));

		List<FollowerIndex> followerIndices =
			followInfoResponse.followerIndices();

		FollowerIndex followerIndex = followerIndices.get(0);

		FollowerIndexStatus followerIndexStatus = followerIndex.status();

		if (followerIndexStatus == FollowerIndexStatus.Active) {
			return new FollowInfoCCRResponse(FollowInfoStatus.ACTIVE);
		}

		return new FollowInfoCCRResponse(FollowInfoStatus.PAUSED);
	}

	private FollowInfoResponse _getFollowInfoResponse(
		FollowInfoCCRRequest followInfoCCRRequest,
		FollowInfoRequest followInfoRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				followInfoCCRRequest.getConnectionId(),
				followInfoCCRRequest.isPreferLocalCluster());

		ElasticsearchCcrClient elasticsearchCcrClient =
			elasticsearchClient.ccr();

		try {
			return elasticsearchCcrClient.followInfo(followInfoRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}