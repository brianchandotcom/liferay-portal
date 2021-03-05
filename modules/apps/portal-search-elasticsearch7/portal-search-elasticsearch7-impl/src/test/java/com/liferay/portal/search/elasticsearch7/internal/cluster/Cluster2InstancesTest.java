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
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class Cluster2InstancesTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_testCluster.createNodes();
	}

	@After
	public void tearDown() throws Exception {
		_testCluster.destroyNodes();
	}

	@Test
	public void test2Nodes1PrimaryShard() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture1 =
			_testCluster.getNode(1);

		createIndex(elasticsearchConnectionFixture1);

		assert1PrimaryShardAnd2Nodes(elasticsearchConnectionFixture1);

		ElasticsearchConnectionFixture elasticsearchConnectionFixture2 =
			_testCluster.getNode(2);

		createIndex(elasticsearchConnectionFixture2);

		assert1PrimaryShardAnd2Nodes(elasticsearchConnectionFixture2);
	}

	@Test
	public void testExpandAndShrink() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture1 =
			_testCluster.getNode(1);

		Index index1 = createIndex(elasticsearchConnectionFixture1);

		ElasticsearchConnectionFixture elasticsearchConnectionFixture2 =
			_testCluster.getNode(2);

		Index index2 = createIndex(elasticsearchConnectionFixture2);

		updateNumberOfReplicas(1, index2, elasticsearchConnectionFixture2);

		assert1ReplicaShard(elasticsearchConnectionFixture1);
		assert1ReplicaShard(elasticsearchConnectionFixture2);

		updateNumberOfReplicas(0, index1, elasticsearchConnectionFixture1);

		assert1PrimaryShardAnd2Nodes(elasticsearchConnectionFixture1);
		assert1PrimaryShardAnd2Nodes(elasticsearchConnectionFixture2);
	}

	@Rule
	public TestName testName = new TestName();

	protected static void assert1PrimaryShardAnd2Nodes(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
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

	protected static void assert1ReplicaShard(
			ElasticsearchClientResolver elasticsearchClientResolver)
		throws Exception {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
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

	private final TestCluster _testCluster = new TestCluster(2, this);

}