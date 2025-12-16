/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.cluster;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.cluster.ElasticsearchClusterClient;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.JsonpUtil;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterResponse;

import java.io.IOException;

/**
 * @author Dylan Rebelak
 */
public class StateClusterRequestExecutor {

	public StateClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public StateClusterResponse execute(
		StateClusterRequest stateClusterRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				stateClusterRequest.getConnectionId(),
				stateClusterRequest.isPreferLocalCluster());

		ElasticsearchClusterClient elasticsearchClusterClient =
			elasticsearchClient.cluster();

		try {
			return new StateClusterResponse(
				JsonpUtil.toString(elasticsearchClusterClient.state()));
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}