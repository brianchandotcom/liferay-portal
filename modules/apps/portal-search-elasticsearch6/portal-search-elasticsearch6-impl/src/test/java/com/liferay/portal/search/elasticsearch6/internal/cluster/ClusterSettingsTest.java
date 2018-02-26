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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch6.internal.connection.EmbeddedElasticsearchConnection;

import org.elasticsearch.cluster.service.ClusterService;
import org.elasticsearch.cluster.service.MasterService;
import org.elasticsearch.common.inject.Injector;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.node.Node;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author William Newbury
 */
public class ClusterSettingsTest {

	@Before
	public void setUp() throws Exception {
		_testCluster.setUp();
	}

	@After
	public void tearDown() throws Exception {
		_testCluster.tearDown();
	}

	@Test
	public void testClusterSettings() throws Exception {
		ElasticsearchFixture elasticsearchFixture = _testCluster.getNode(0);

		EmbeddedElasticsearchConnection embeddedElasticsearchConnection =
			elasticsearchFixture.getEmbeddedElasticsearchConnection();

		Node node = embeddedElasticsearchConnection.getNode();

		Injector injector = node.injector();

		ClusterService clusterService = injector.getInstance(
			ClusterService.class);

		MasterService masterService = clusterService.getMasterService();

		TimeValue slowTaskLoggingThreshold = ReflectionTestUtil.getFieldValue(
			masterService, "slowTaskLoggingThreshold");

		Assert.assertEquals("10m", slowTaskLoggingThreshold.toString());
	}

	private final TestCluster _testCluster = new TestCluster(1, this);

}