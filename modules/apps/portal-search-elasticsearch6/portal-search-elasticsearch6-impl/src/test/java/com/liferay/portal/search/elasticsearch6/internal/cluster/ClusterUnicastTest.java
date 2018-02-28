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
import com.liferay.portal.search.elasticsearch6.internal.connection.Index;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexName;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
@Ignore
public class ClusterUnicastTest {

	@Before
	public void setUp() throws Exception {
		_testCluster.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_testCluster.tearDown();
	}

	@Test
	public void testSplitBrainPreventedEvenIfMasterLeaves() throws Exception {
		ElasticsearchFixture elasticsearchFixture0 = _testCluster.getNode(0);

		Index index0 = createIndex(elasticsearchFixture0);

		ElasticsearchFixture elasticsearchFixture1 = _testCluster.getNode(1);

		Index index1 = createIndex(elasticsearchFixture1);

		ElasticsearchFixture elasticsearchFixture2 = _testCluster.getNode(2);

		Index index2 = createIndex(elasticsearchFixture2);

		updateNumberOfReplicas(2, index0, elasticsearchFixture0);

		ClusterAssert.assert2ReplicaShards(elasticsearchFixture0);
		ClusterAssert.assert2ReplicaShards(elasticsearchFixture1);
		ClusterAssert.assert2ReplicaShards(elasticsearchFixture2);

		_testCluster.destroyNode(0);

		ClusterAssert.assert1ReplicaAnd1UnassignedShard(elasticsearchFixture1);
		ClusterAssert.assert1ReplicaAnd1UnassignedShard(elasticsearchFixture2);

		updateNumberOfReplicas(1, index1, elasticsearchFixture1);

		ClusterAssert.assert1ReplicaShard(elasticsearchFixture1);
		ClusterAssert.assert1ReplicaShard(elasticsearchFixture2);

		_testCluster.destroyNode(1);

		ClusterAssert.assert1PrimaryAnd1UnassignedShard(elasticsearchFixture2);

		updateNumberOfReplicas(0, index2, elasticsearchFixture2);

		ClusterAssert.assert1PrimaryShardOnly(elasticsearchFixture2);
	}

	@Rule
	public TestName testName = new TestName();

	protected Index createIndex(ElasticsearchFixture elasticsearchFixture) {
		IndexCreator indexCreator = new IndexCreator(elasticsearchFixture);

		return indexCreator.createIndex(
			new IndexName(testName.getMethodName()));
	}

	protected void updateNumberOfReplicas(
		int numberOfReplicas, Index index,
		ElasticsearchFixture elasticsearchFixture) {

		ReplicasManager replicasManager = new ReplicasManagerImpl(
			elasticsearchFixture.getIndicesAdminClient());

		replicasManager.updateNumberOfReplicas(
			numberOfReplicas, index.getName());
	}

	private final TestCluster _testCluster = new TestCluster(3, this);

}