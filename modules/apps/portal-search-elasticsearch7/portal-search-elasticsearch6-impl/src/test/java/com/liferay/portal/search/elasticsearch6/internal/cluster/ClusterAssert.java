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

package com.liferay.portal.search.elasticsearch6.internal.cluster;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.HealthExpectations;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

import org.junit.Assert;

/**
 * @author André de Oliveira
 */
public class ClusterAssert {

	public static void assert1PrimaryAnd1UnassignedShard(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(1);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setStatus(ClusterHealthStatus.YELLOW);
					setUnassignedShards(1);
				}
			});
	}

	public static void assert1PrimaryShardAnd2Nodes(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(1);
					setNumberOfDataNodes(2);
					setNumberOfNodes(2);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	public static void assert1PrimaryShardOnly(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(1);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	public static void assert1ReplicaAnd1UnassignedShard(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(2);
					setNumberOfDataNodes(2);
					setNumberOfNodes(2);
					setStatus(ClusterHealthStatus.YELLOW);
					setUnassignedShards(1);
				}
			});
	}

	public static void assert1ReplicaShard(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(2);
					setNumberOfDataNodes(2);
					setNumberOfNodes(2);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	public static void assert2Primary2UnassignedShardsAnd1Node(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(2);
					setActiveShards(2);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setStatus(ClusterHealthStatus.YELLOW);
					setUnassignedShards(2);
				}
			});
	}

	public static void assert2PrimaryShards1ReplicaAnd2Nodes(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(2);
					setActiveShards(4);
					setNumberOfDataNodes(2);
					setNumberOfNodes(2);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	public static void assert2PrimaryShardsAnd2Nodes(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(2);
					setActiveShards(2);
					setNumberOfDataNodes(2);
					setNumberOfNodes(2);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	public static void assert2ReplicaShards(
			ElasticsearchFixture elasticsearchFixture)
		throws Exception {

		assertHealth(
			elasticsearchFixture,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(3);
					setNumberOfDataNodes(3);
					setNumberOfNodes(3);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	public static void assertHealth(
			final ElasticsearchFixture elasticsearchFixture,
			final HealthExpectations healthExpectations)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			10, TimeUnit.MINUTES,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					_assertHealth(
						elasticsearchFixture.getClusterHealthResponse(
							healthExpectations),
						healthExpectations);

					return null;
				}

			});
	}

	private static void _assertHealth(
		ClusterHealthResponse clusterHealthResponse,
		HealthExpectations expectedHealthExpectations) {

		HealthExpectations actualHealthExpectations = new HealthExpectations(
			clusterHealthResponse);

		Assert.assertEquals(
			expectedHealthExpectations.toString(),
			actualHealthExpectations.toString());
	}

}