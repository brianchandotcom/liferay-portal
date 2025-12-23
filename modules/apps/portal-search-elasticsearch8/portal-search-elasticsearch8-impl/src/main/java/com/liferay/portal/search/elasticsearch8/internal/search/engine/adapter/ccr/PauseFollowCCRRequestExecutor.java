/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.ccr;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.ccr.ElasticsearchCcrClient;
import co.elastic.clients.elasticsearch.ccr.PauseFollowRequest;
import co.elastic.clients.elasticsearch.ccr.PauseFollowResponse;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRRequest;
import com.liferay.portal.search.engine.adapter.ccr.PauseFollowCCRResponse;

import java.io.IOException;

/**
 * @author Bryan Engler
 */
public class PauseFollowCCRRequestExecutor {

	public PauseFollowCCRRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public PauseFollowCCRResponse execute(
		PauseFollowCCRRequest pauseFollowCCRRequest) {

		PauseFollowResponse pauseFollowResponse = _getPauseFollowResponse(
			pauseFollowCCRRequest,
			PauseFollowRequest.of(
				builder -> builder.index(
					pauseFollowCCRRequest.getIndexName())));

		return new PauseFollowCCRResponse(pauseFollowResponse.acknowledged());
	}

	private PauseFollowResponse _getPauseFollowResponse(
		PauseFollowCCRRequest pauseFollowCCRRequest,
		PauseFollowRequest pauseFollowRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				pauseFollowCCRRequest.getConnectionId(),
				pauseFollowCCRRequest.isPreferLocalCluster());

		ElasticsearchCcrClient elasticsearchCcrClient =
			elasticsearchClient.ccr();

		try {
			return elasticsearchCcrClient.pauseFollow(pauseFollowRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}