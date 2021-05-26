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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster;

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.core.TimeValue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class HealthClusterRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(
			HealthClusterRequestExecutorTest.class.getSimpleName());

		_elasticsearchFixture.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testClusterRequestTranslation() {
		HealthClusterRequest healthClusterRequest = new HealthClusterRequest(
			_INDEX_NAME);

		healthClusterRequest.setTimeout(1000);
		healthClusterRequest.setWaitForClusterHealthStatus(
			ClusterHealthStatus.GREEN);

		HealthClusterRequestExecutorImpl healthClusterRequestExecutorImpl =
			new HealthClusterRequestExecutorImpl() {
				{
					setClusterHealthStatusTranslator(
						new ClusterHealthStatusTranslatorImpl());
					setElasticsearchClientResolver(_elasticsearchFixture);
				}
			};

		ClusterHealthRequest clusterHealthRequest =
			healthClusterRequestExecutorImpl.createClusterHealthRequest(
				healthClusterRequest);

		String[] indices = clusterHealthRequest.indices();

		Assert.assertArrayEquals(new String[] {_INDEX_NAME}, indices);

		ClusterHealthStatusTranslator clusterHealthStatusTranslator =
			new ClusterHealthStatusTranslatorImpl();

		Assert.assertEquals(
			healthClusterRequest.getWaitForClusterHealthStatus(),
			clusterHealthStatusTranslator.translate(
				clusterHealthRequest.waitForStatus()));

		Assert.assertEquals(
			TimeValue.timeValueMillis(1000), clusterHealthRequest.timeout());

		Assert.assertEquals(
			TimeValue.timeValueMillis(1000),
			clusterHealthRequest.masterNodeTimeout());
	}

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;

}