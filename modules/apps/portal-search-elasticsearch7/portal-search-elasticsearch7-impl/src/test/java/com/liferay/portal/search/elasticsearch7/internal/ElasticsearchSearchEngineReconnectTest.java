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

package com.liferay.portal.search.elasticsearch7.internal;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionManager;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class ElasticsearchSearchEngineReconnectTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				ElasticsearchSearchEngineReconnectTest.class.getSimpleName()
			).build();

		ElasticsearchSearchEngineFixture elasticsearchSearchEngineFixture =
			new ElasticsearchSearchEngineFixture(
				elasticsearchConnectionFixture);

		elasticsearchSearchEngineFixture.setUp();

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;

		_elasticsearchSearchEngineFixture = elasticsearchSearchEngineFixture;
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_elasticsearchSearchEngineFixture.tearDown();
	}

	public SnapshotClient getSnapshotClient() {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnectionFixture.getRestHighLevelClient();

		return restHighLevelClient.snapshot();
	}

	@Test
	public void testInitializeAfterReconnect() {
		ElasticsearchSearchEngine elasticsearchSearchEngine =
			_elasticsearchSearchEngineFixture.getElasticsearchSearchEngine();

		long companyId = RandomTestUtil.randomLong();

		elasticsearchSearchEngine.initialize(companyId);

		_reconnect(
			_elasticsearchSearchEngineFixture.
				getElasticsearchConnectionManager());

		elasticsearchSearchEngine.initialize(companyId);
	}

	private void _reconnect(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		ElasticsearchConnection elasticsearchConnection =
			elasticsearchConnectionManager.getElasticsearchConnection();

		elasticsearchConnection.close();

		elasticsearchConnection.connect();
	}

	private static ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private static ElasticsearchSearchEngineFixture
		_elasticsearchSearchEngineFixture;

}