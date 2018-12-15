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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.ClusterClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.unit.TimeValue;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(immediate = true, service = HealthClusterRequestExecutor.class)
public class HealthClusterRequestExecutorImpl
	implements HealthClusterRequestExecutor {

	@Override
	public HealthClusterResponse execute(
		HealthClusterRequest healthClusterRequest) {

		ClusterHealthRequest clusterHealthRequest = createClusterHealthRequest(
			healthClusterRequest);

		ClusterHealthResponse clusterHealthResponse = getClusterHealthResponse(
			clusterHealthRequest);

		ClusterHealthStatus clusterHealthStatus =
			clusterHealthResponse.getStatus();

		return new HealthClusterResponse(
			_clusterHealthStatusTranslator.translate(clusterHealthStatus),
			clusterHealthResponse.toString());
	}

	protected ClusterHealthRequest createClusterHealthRequest(
		HealthClusterRequest healthClusterRequest) {

		ClusterHealthRequest clusterHealthRequest = new ClusterHealthRequest(
			healthClusterRequest.getIndexNames());

		long timeout = healthClusterRequest.getTimeout();

		if (timeout > 0) {
			clusterHealthRequest.masterNodeTimeout(
				TimeValue.timeValueMillis(timeout));
			clusterHealthRequest.timeout(TimeValue.timeValueMillis(timeout));
		}

		if (healthClusterRequest.getWaitForClusterHealthStatus() != null) {
			clusterHealthRequest.waitForStatus(
				_clusterHealthStatusTranslator.translate(
					healthClusterRequest.getWaitForClusterHealthStatus()));
		}

		return clusterHealthRequest;
	}

	protected ClusterHealthResponse getClusterHealthResponse(
		ClusterHealthRequest clusterHealthRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		ClusterClient clusterClient = restHighLevelClient.cluster();

		try {
			return clusterClient.health(
				clusterHealthRequest, RequestOptions.DEFAULT);
		}
		catch (Exception ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Reference(unbind = "-")
	protected void setClusterHealthStatusTranslator(
		ClusterHealthStatusTranslator clusterHealthStatusTranslator) {

		_clusterHealthStatusTranslator = clusterHealthStatusTranslator;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ClusterHealthStatusTranslator _clusterHealthStatusTranslator;
	private ElasticsearchClientResolver _elasticsearchClientResolver;

}