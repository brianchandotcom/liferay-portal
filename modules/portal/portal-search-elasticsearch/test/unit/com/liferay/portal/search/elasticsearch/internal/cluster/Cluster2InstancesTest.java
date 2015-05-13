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

import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture.Index;

import org.elasticsearch.indices.recovery.RecoveryState.Type;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

/**
 * @author André de Oliveira
 */
public class Cluster2InstancesTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_elasticsearchFixture1 = createFixture(1);
		_elasticsearchFixture2 = createFixture(2);

		_elasticsearchFixture1.setUpClass();
		_elasticsearchFixture2.setUpClass();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchFixture1.tearDownClass();
		_elasticsearchFixture2.tearDownClass();

		_elasticsearchFixture1 = null;
		_elasticsearchFixture2 = null;
	}

	@Test
	public void test2Nodes1PrimaryShard() throws Exception {
		Index index1 = createIndex(_elasticsearchFixture1);
		Index index2 = createIndex(_elasticsearchFixture2);

		assertEntireClusterHasOnly1PrimaryShard(index1);
		assertEntireClusterHasOnly1PrimaryShard(index2);
	}

	@Test
	public void testExpandAndShrink() throws Exception {
		Index index1 = createIndex(_elasticsearchFixture1);
		Index index2 = createIndex(_elasticsearchFixture2);

		updateNumberOfReplicas(1, index2, _elasticsearchFixture2);

		assertClusterHas1PrimaryAnd1ReplicaShard(index1);
		assertClusterHas1PrimaryAnd1ReplicaShard(index2);

		updateNumberOfReplicas(0, index1, _elasticsearchFixture1);

		assertEntireClusterHasOnly1PrimaryShard(index1);
		assertEntireClusterHasOnly1PrimaryShard(index2);
	}

	@Rule
	public TestName testName = new TestName();

	protected static ElasticsearchFixture createFixture(int i) {
		return new ElasticsearchFixture(
			Cluster2InstancesTest.class.getSimpleName() + "-" + i);
	}

	protected void assertClusterHas1PrimaryAnd1ReplicaShard(Index index)
		throws Exception {

		ClusterAssert.assertShards(index, Type.GATEWAY, Type.REPLICA);
	}

	protected void assertEntireClusterHasOnly1PrimaryShard(Index index)
		throws Exception {

		ClusterAssert.assertShards(index, Type.GATEWAY);
	}

	protected Index createIndex(ElasticsearchFixture elasticsearchFixture) {
		return elasticsearchFixture.createIndex(testName.getMethodName());
	}

	protected void updateNumberOfReplicas(
		int numberOfReplicas, Index index,
		ElasticsearchFixture elasticsearchFixture) {

		ReplicasManager replicasManager = new ReplicasManagerImpl(
			elasticsearchFixture.getIndicesAdminClient());

		replicasManager.updateNumberOfReplicas(
			numberOfReplicas, index.getName());
	}

	private static ElasticsearchFixture _elasticsearchFixture1;
	private static ElasticsearchFixture _elasticsearchFixture2;

}