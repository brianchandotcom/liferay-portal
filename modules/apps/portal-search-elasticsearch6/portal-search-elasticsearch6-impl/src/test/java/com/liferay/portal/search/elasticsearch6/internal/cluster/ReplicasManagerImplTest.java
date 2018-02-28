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

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexCreator;
import com.liferay.portal.search.elasticsearch6.internal.connection.IndexName;
import com.liferay.portal.search.elasticsearch6.internal.index.IndexNameBuilder;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Artur Aquino
 */
@Ignore
public class ReplicasManagerImplTest {

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

		ElasticsearchFixture elasticsearchFixture0 = createNode(0);

		IndexCreator indexCreator0 = new IndexCreator(elasticsearchFixture0);

		indexCreator0.createIndex(getTestIndexName(CompanyConstants.SYSTEM));

		ElasticsearchFixture elasticsearchFixture1 = createNode(1);

		ClusterAssert.assert1PrimaryShardAnd2Nodes(elasticsearchFixture0);

		IndexCreator indexCreator1 = new IndexCreator(elasticsearchFixture1);

		indexCreator1.createIndex(getTestIndexName(companyId));

		ClusterAssert.assert2PrimaryShardsAnd2Nodes(elasticsearchFixture1);

		ReplicasManager replicasManager = new ReplicasManagerImpl(
			elasticsearchFixture0.getIndicesAdminClient());

		replicasManager.updateNumberOfReplicas(
			1, _replicasClusterContext.getTargetIndexNames());

		ClusterAssert.assert2PrimaryShards1ReplicaAnd2Nodes(
			elasticsearchFixture0);

		_testCluster.destroyNode(0);

		ClusterAssert.assert2Primary2UnassignedShardsAnd1Node(
			elasticsearchFixture1);
	}

	@Rule
	public TestName testName = new TestName();

	protected ElasticsearchFixture createNode(int index) throws Exception {
		_testCluster.createNode(index);

		return _testCluster.getNode(index);
	}

	protected ReplicasClusterContext createReplicasClusterContext() {
		ElasticsearchCluster elasticsearchCluster = new ElasticsearchCluster();

		elasticsearchCluster.companyLocalService = _companyLocalService;

		elasticsearchCluster.indexNameBuilder = new IndexNameBuilder() {

			@Override
			public String getIndexName(long companyId) {
				IndexName indexName = getTestIndexName(companyId);

				return indexName.getName();
			}

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

	@Mock
	private CompanyLocalService _companyLocalService;

	private ReplicasClusterContext _replicasClusterContext;
	private final TestCluster _testCluster = new TestCluster(2, this);

}