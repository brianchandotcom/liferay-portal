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

package com.liferay.portal.search.elasticsearch.internal.cluster;

import static org.elasticsearch.cluster.health.ClusterHealthStatus.GREEN;
import static org.elasticsearch.cluster.health.ClusterHealthStatus.YELLOW;

import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.HealthExpectations;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;

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
					activePrimaryShards = 1;
					activeShards = 1;
					numberOfDataNodes = 1;
					numberOfNodes = 1;
					status = YELLOW;
					unassignedShards = 1;
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
					activePrimaryShards = 1;
					activeShards = 1;
					numberOfDataNodes = 2;
					numberOfNodes = 2;
					status = GREEN;
					unassignedShards = 0;
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
					activePrimaryShards = 1;
					activeShards = 1;
					numberOfDataNodes = 1;
					numberOfNodes = 1;
					status = GREEN;
					unassignedShards = 0;
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
					activePrimaryShards = 1;
					activeShards = 2;
					numberOfDataNodes = 2;
					numberOfNodes = 2;
					status = YELLOW;
					unassignedShards = 1;
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
					activePrimaryShards = 1;
					activeShards = 2;
					numberOfDataNodes = 2;
					numberOfNodes = 2;
					status = GREEN;
					unassignedShards = 0;
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
					activePrimaryShards = 2;
					activeShards = 2;
					numberOfDataNodes = 1;
					numberOfNodes = 1;
					status = YELLOW;
					unassignedShards = 2;
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
					activePrimaryShards = 2;
					activeShards = 4;
					numberOfDataNodes = 2;
					numberOfNodes = 2;
					status = GREEN;
					unassignedShards = 0;
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
					activePrimaryShards = 2;
					activeShards = 2;
					numberOfDataNodes = 2;
					numberOfNodes = 2;
					status = GREEN;
					unassignedShards = 0;
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
					activePrimaryShards = 1;
					activeShards = 3;
					numberOfDataNodes = 3;
					numberOfNodes = 3;
					status = GREEN;
					unassignedShards = 0;
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
					ClusterHealthResponse clusterHealthResponse =
						elasticsearchFixture.getClusterHealthResponse(
							healthExpectations);

					_assertHealth(clusterHealthResponse, healthExpectations);

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