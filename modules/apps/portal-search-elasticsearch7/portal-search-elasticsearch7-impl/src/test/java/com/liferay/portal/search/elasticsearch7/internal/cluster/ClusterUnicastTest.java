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

package com.liferay.portal.search.elasticsearch7.internal.cluster;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.HealthExpectations;
import com.liferay.portal.search.elasticsearch7.internal.connection.Index;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

import org.junit.After;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class ClusterUnicastTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	public static void assert2ReplicaShards(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
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

	@After
	public void tearDown() throws Exception {
		_testCluster.destroyNodes();
	}

	@Test
	public void testSplitBrainPreventedEvenIfMasterLeaves() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture1 =
			_testCluster.createNode(1);

		Index index1 = createIndex(elasticsearchConnectionFixture1);

		ElasticsearchConnectionFixture elasticsearchConnectionFixture2 =
			_testCluster.createNode(2);

		Index index2 = createIndex(elasticsearchConnectionFixture2);

		ElasticsearchConnectionFixture elasticsearchConnectionFixture3 =
			_testCluster.createNode(3);

		Index index3 = createIndex(elasticsearchConnectionFixture3);

		updateNumberOfReplicas(2, index1, elasticsearchConnectionFixture1);

		assert2ReplicaShards(elasticsearchConnectionFixture1);
		assert2ReplicaShards(elasticsearchConnectionFixture2);
		assert2ReplicaShards(elasticsearchConnectionFixture3);

		_testCluster.createNode(4);

		_testCluster.destroyNode(1);

		_assert1ReplicaAnd1UnassignedShard(elasticsearchConnectionFixture2);
		_assert1ReplicaAnd1UnassignedShard(elasticsearchConnectionFixture3);

		updateNumberOfReplicas(1, index2, elasticsearchConnectionFixture2);

		assert1ReplicaShard(elasticsearchConnectionFixture2);
		assert1ReplicaShard(elasticsearchConnectionFixture3);

		_testCluster.createNode(5);

		_testCluster.destroyNode(2);

		_assert1PrimaryAnd1UnassignedShard(elasticsearchConnectionFixture3);

		updateNumberOfReplicas(0, index3, elasticsearchConnectionFixture3);

		_assert1PrimaryShardOnly(elasticsearchConnectionFixture3);
	}

	@Rule
	public TestName testName = new TestName();

	protected static void assert1ReplicaShard(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(2);
					setNumberOfDataNodes(3);
					setNumberOfNodes(3);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	protected Index createIndex(
		ElasticsearchConnectionFixture elasticsearchConnectionFixture) {

		IndexCreator indexCreator = new IndexCreator() {
			{
				setElasticsearchClientResolver(elasticsearchConnectionFixture);
			}
		};

		return indexCreator.createIndex(
			new IndexName(testName.getMethodName()));
	}

	protected void updateNumberOfReplicas(
		int numberOfReplicas, Index index,
		ElasticsearchConnectionFixture elasticsearchConnectionFixture) {

		RestHighLevelClient restHighLevelClient =
			elasticsearchConnectionFixture.getRestHighLevelClient();

		ReplicasManager replicasManager = new ReplicasManagerImpl(
			restHighLevelClient.indices());

		replicasManager.updateNumberOfReplicas(
			numberOfReplicas, index.getName());
	}

	private void _assert1PrimaryAnd1UnassignedShard(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(1);
					setNumberOfDataNodes(3);
					setNumberOfNodes(3);
					setStatus(ClusterHealthStatus.YELLOW);
					setUnassignedShards(1);
				}
			});
	}

	private void _assert1PrimaryShardOnly(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(1);
					setNumberOfDataNodes(3);
					setNumberOfNodes(3);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	private void _assert1ReplicaAnd1UnassignedShard(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
			new HealthExpectations() {
				{
					setActivePrimaryShards(1);
					setActiveShards(2);
					setNumberOfDataNodes(3);
					setNumberOfNodes(3);
					setStatus(ClusterHealthStatus.YELLOW);
					setUnassignedShards(1);
				}
			});
	}

	private final TestCluster _testCluster = new TestCluster(5, this);

}