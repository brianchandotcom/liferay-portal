/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.ccr;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.ccr.ElasticsearchCcrClient;
import co.elastic.clients.elasticsearch.ccr.UnfollowRequest;
import co.elastic.clients.elasticsearch.ccr.UnfollowResponse;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.UnfollowCCRResponse;

import java.io.IOException;

/**
 * @author Bryan Engler
 */
public class UnfollowCCRRequestExecutor {

	public UnfollowCCRRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public UnfollowCCRResponse execute(UnfollowCCRRequest unfollowCCRRequest) {
		UnfollowResponse unfollowResponse = _getUnfollowResponse(
			unfollowCCRRequest,
			UnfollowRequest.of(
				builder -> builder.index(unfollowCCRRequest.getIndexName())));

		return new UnfollowCCRResponse(unfollowResponse.acknowledged());
	}

	private UnfollowResponse _getUnfollowResponse(
		UnfollowCCRRequest unfollowCCRRequest,
		UnfollowRequest unfollowRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				unfollowCCRRequest.getConnectionId(),
				unfollowCCRRequest.isPreferLocalCluster());

		ElasticsearchCcrClient elasticsearchCcrClient =
			elasticsearchClient.ccr();

		try {
			return elasticsearchCcrClient.unfollow(unfollowRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}