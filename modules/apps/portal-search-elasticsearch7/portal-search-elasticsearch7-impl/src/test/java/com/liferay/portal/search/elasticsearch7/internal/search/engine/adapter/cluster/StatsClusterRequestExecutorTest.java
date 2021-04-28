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

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class StatsClusterRequestExecutorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				StatsClusterRequestExecutorTest.class.getSimpleName()
			).build();

		elasticsearchConnectionFixture.createNode();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
	}

	@After
	public void tearDown() throws Exception {
		_elasticsearchConnectionFixture.destroyNode();
	}

	@Test
	public void testClusterRequestExecution() {
		StatsClusterRequest statsClusterRequest = new StatsClusterRequest(
			new String[] {_NODE_ID});

		StatsClusterRequestExecutorImpl statsClusterRequestExecutorImpl =
			new StatsClusterRequestExecutorImpl() {
				{
					setElasticsearchClientResolver(
						_elasticsearchConnectionFixture);
					setClusterHealthStatusTranslator(
						new ClusterHealthStatusTranslatorImpl());
				}
			};

		StatsClusterResponse statsClusterResponse =
			statsClusterRequestExecutorImpl.execute(statsClusterRequest);

		Assert.assertNotNull(statsClusterResponse);

		Assert.assertNotNull(statsClusterResponse.getClusterHealthStatus());
	}

	private static final String _NODE_ID = "liferay";

	private ElasticsearchConnectionFixture _elasticsearchConnectionFixture;

}