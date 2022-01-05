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

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;
import java.util.function.Consumer;

import org.apache.http.HttpHost;

import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class ElasticsearchConnectionTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_elasticsearchConnection = new ElasticsearchConnection();
	}

	@Test
	public void testConnectAndClose() {
		_elasticsearchConnection.setNetworkHostAddresses(
			new String[] {"http://localhost:9200"});

		Runnable postCloseRunnable = Mockito.mock(Runnable.class);

		_elasticsearchConnection.setPostCloseRunnable(postCloseRunnable);

		Consumer<ElasticsearchConnection>
			preConnectElasticsearchConnectionConsumer = Mockito.mock(
				Consumer.class);

		_elasticsearchConnection.setPreConnectElasticsearchConnectionConsumer(
			preConnectElasticsearchConnectionConsumer);

		Assert.assertFalse(_elasticsearchConnection.isConnected());

		_elasticsearchConnection.connect();

		Assert.assertTrue(_elasticsearchConnection.isConnected());

		Mockito.verify(
			preConnectElasticsearchConnectionConsumer
		).accept(
			Mockito.any()
		);

		_assertNetworkHostAddress("localhost", 9200);

		_elasticsearchConnection.close();

		Assert.assertFalse(_elasticsearchConnection.isConnected());

		Mockito.verify(
			postCloseRunnable
		).run();

		_elasticsearchConnection.setNetworkHostAddresses(
			new String[] {"http://127.0.0.1:9999"});

		_elasticsearchConnection.connect();

		Assert.assertTrue(_elasticsearchConnection.isConnected());

		_assertNetworkHostAddress("127.0.0.1", 9999);
	}

	private void _assertNetworkHostAddress(String hostString, int port) {
		RestHighLevelClient restHighLevelClient =
			_elasticsearchConnection.getRestHighLevelClient();

		RestClient restClient = restHighLevelClient.getLowLevelClient();

		List<Node> nodes = restClient.getNodes();

		Assert.assertEquals(nodes.toString(), 1, nodes.size());

		Node node = nodes.get(0);

		HttpHost httpHost = node.getHost();

		Assert.assertEquals(hostString, httpHost.getHostName());
		Assert.assertEquals(port, httpHost.getPort());
	}

	private ElasticsearchConnection _elasticsearchConnection;

}