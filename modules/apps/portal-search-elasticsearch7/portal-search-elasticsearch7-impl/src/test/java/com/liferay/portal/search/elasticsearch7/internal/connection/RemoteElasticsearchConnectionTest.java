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

import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.elasticsearch7.configuration.OperationMode;

import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpHost;

import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class RemoteElasticsearchConnectionTest {

	@Before
	public void setUp() {
		_remoteElasticsearchConnection = new RemoteElasticsearchConnection();

		_remoteElasticsearchConnection.props = PropsTestUtil.setProps(
			HashMapBuilder.<String, Object>put(
				PropsKeys.DNS_SECURITY_ADDRESS_TIMEOUT_SECONDS,
				String.valueOf(2)
			).put(
				PropsKeys.DNS_SECURITY_THREAD_LIMIT, String.valueOf(10)
			).build());
	}

	@Test
	public void testModifyConnected() {
		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).build();

		_remoteElasticsearchConnection.activate(properties);

		Assert.assertFalse(_remoteElasticsearchConnection.isConnected());

		_remoteElasticsearchConnection.connect();

		Assert.assertTrue(_remoteElasticsearchConnection.isConnected());

		assertNetworkHostAddress("localhost", 9200);

		properties.put("networkHostAddresses", "127.0.0.1:9999");

		_remoteElasticsearchConnection.modified(properties);

		Assert.assertTrue(_remoteElasticsearchConnection.isConnected());

		assertNetworkHostAddress("127.0.0.1", 9999);
	}

	@Test
	public void testModifyUnconnected() {
		Assert.assertFalse(_remoteElasticsearchConnection.isConnected());

		HashMap<String, Object> properties = HashMapBuilder.<String, Object>put(
			"operationMode", OperationMode.REMOTE.name()
		).build();

		_remoteElasticsearchConnection.modified(properties);

		Assert.assertTrue(_remoteElasticsearchConnection.isConnected());
	}

	protected void assertNetworkHostAddress(String hostString, int port) {
		RestHighLevelClient restHighLevelClient =
			_remoteElasticsearchConnection.getRestHighLevelClient();

		RestClient restClient = restHighLevelClient.getLowLevelClient();

		List<Node> nodes = restClient.getNodes();

		Assert.assertEquals(nodes.toString(), 1, nodes.size());

		Node node = nodes.get(0);

		HttpHost httpHost = node.getHost();

		Assert.assertEquals(hostString, httpHost.getHostName());
		Assert.assertEquals(port, httpHost.getPort());
	}

	private RemoteElasticsearchConnection _remoteElasticsearchConnection;

}