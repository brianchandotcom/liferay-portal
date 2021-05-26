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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import java.io.IOException;

import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.client.ClusterClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;

/**
 * @author André de Oliveira
 */
public class ClusterHealthResponseUtil {

	public static ClusterHealthResponse getClusterHealthResponse(
		ElasticsearchClientResolver elasticsearchClientResolver,
		HealthExpectations healthExpectations) {

		RestHighLevelClient restHighLevelClient =
			elasticsearchClientResolver.getRestHighLevelClient();

		ClusterClient clusterClient = restHighLevelClient.cluster();

		ClusterHealthRequest clusterHealthRequest = new ClusterHealthRequest();

		clusterHealthRequest.masterNodeTimeout(
			new TimeValue(10, TimeUnit.MINUTES));
		clusterHealthRequest.timeout(new TimeValue(10, TimeUnit.MINUTES));
		clusterHealthRequest.waitForActiveShards(
			healthExpectations.getActiveShards());
		clusterHealthRequest.waitForNodes(
			String.valueOf(healthExpectations.getNumberOfNodes()));
		clusterHealthRequest.waitForNoRelocatingShards(true);
		clusterHealthRequest.waitForStatus(healthExpectations.getStatus());

		try {
			return clusterClient.health(
				clusterHealthRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

}