/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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