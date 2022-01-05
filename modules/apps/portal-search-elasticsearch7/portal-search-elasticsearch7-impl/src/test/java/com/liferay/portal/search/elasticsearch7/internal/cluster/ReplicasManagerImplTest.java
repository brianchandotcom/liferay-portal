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

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.HealthExpectations;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch7.internal.connection.IndexName;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Artur Aquino
 */
public class ReplicasManagerImplTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		_replicasClusterContext = createReplicasClusterContext();
	}

	@After
	public void tearDown() throws Exception {
		_testCluster.destroyNodes();
	}

	@Test
	public void testSystemCompanyIndexIsReplicatedAndMigrated()
		throws Exception {

		long companyId = RandomTestUtil.randomLong();

		setUpCompanyLocalService(companyId);

		ElasticsearchClientResolver elasticsearchClientResolver1 = createNode(
			1);

		IndexCreator indexCreator1 = new IndexCreator() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver1);
			}
		};

		indexCreator1.createIndex(getTestIndexName(CompanyConstants.SYSTEM));

		ElasticsearchClientResolver elasticsearchClientResolver2 = createNode(
			2);

		assert1PrimaryShardAnd2Nodes(elasticsearchClientResolver1);

		IndexCreator indexCreator2 = new IndexCreator() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver2);
			}
		};

		indexCreator2.createIndex(getTestIndexName(companyId));

		_assert2PrimaryShardsAnd2Nodes(elasticsearchClientResolver2);

		RestHighLevelClient restHighLevelClient =
			elasticsearchClientResolver1.getRestHighLevelClient();

		ReplicasManager replicasManager = new ReplicasManagerImpl(
			restHighLevelClient.indices());

		replicasManager.updateNumberOfReplicas(
			1, _replicasClusterContext.getTargetIndexNames());

		_assert2PrimaryShards1ReplicaAnd2Nodes(elasticsearchClientResolver1);

		createNode(3);
		createNode(4);

		_testCluster.destroyNode(1);

		_waitForShardReroute();

		_assert2PrimaryShards1ReplicaAnd3Nodes(elasticsearchClientResolver2);
	}

	@Rule
	public TestName testName = new TestName();

	protected static void assert1PrimaryShardAnd2Nodes(
		ElasticsearchClientResolver elasticsearchClientResolver) {

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

	protected ElasticsearchClientResolver createNode(int number) {
		_testCluster.createNode(number);

		return _testCluster.getNode(number);
	}

	protected ReplicasClusterContext createReplicasClusterContext() {
		ElasticsearchCluster elasticsearchCluster = new ElasticsearchCluster();

		elasticsearchCluster.companyLocalService = _companyLocalService;

		elasticsearchCluster.indexNameBuilder = companyId -> {
			IndexName indexName = getTestIndexName(companyId);

			return indexName.getName();
		};

		return elasticsearchCluster.new ReplicasClusterContextImpl();
	}

	protected IndexName getTestIndexName(long companyId) {
		return new IndexName(testName.getMethodName() + "-" + companyId);
	}

	protected void setUpCompanyLocalService(long companyId) {
		Company company = Mockito.mock(Company.class);

		Mockito.when(
			company.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			_companyLocalService.getCompanies()
		).thenReturn(
			Collections.singletonList(company)
		);
	}

	private static void _assert2PrimaryShards1ReplicaAnd2Nodes(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
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

	private static void _assert2PrimaryShards1ReplicaAnd3Nodes(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
			new HealthExpectations() {
				{
					setActivePrimaryShards(2);
					setActiveShards(4);
					setNumberOfDataNodes(3);
					setNumberOfNodes(3);
					setStatus(ClusterHealthStatus.GREEN);
					setUnassignedShards(0);
				}
			});
	}

	private static void _assert2PrimaryShardsAnd2Nodes(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterAssert.assertHealth(
			elasticsearchClientResolver,
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

	private void _waitForShardReroute() throws Exception {
		Thread.sleep(60000);
	}

	@Mock
	private CompanyLocalService _companyLocalService;

	private ReplicasClusterContext _replicasClusterContext;
	private final TestCluster _testCluster = new TestCluster(4, this);

}