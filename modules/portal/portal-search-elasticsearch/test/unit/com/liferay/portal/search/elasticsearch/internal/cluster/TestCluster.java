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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;

import java.net.InetAddress;
import java.net.NetworkInterface;

import java.util.HashMap;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class TestCluster {

	public TestCluster(int size, Object object) {
		_elasticsearchConfigurationProperties =
			createElasticsearchConfigurationProperties(size);

		_elasticsearchFixtures = new ElasticsearchFixture[size];

		Class<?> clazz = object.getClass();

		_prefix = clazz.getSimpleName();
	}

	public void createNodes() throws Exception {
		for (int i = 0; i < _elasticsearchFixtures.length; i++) {
			_createNode(i);
		}
	}

	public void destroyNode(int index) throws Exception {
		if (_elasticsearchFixtures[index] != null) {
			_elasticsearchFixtures[index].destroyNode();

			_elasticsearchFixtures[index] = null;
		}
	}

	public void destroyNodes() throws Exception {
		for (int i = 0; i < _elasticsearchFixtures.length; i++) {
			destroyNode(i);
		}
	}

	public ElasticsearchFixture getNode(int index) {
		return _elasticsearchFixtures[index];
	}

	public void setUp() throws Exception {
		createNodes();
	}

	public void tearDown() throws Exception {
		destroyNodes();
	}

	protected HashMap<String, Object>
		createElasticsearchConfigurationProperties(int size) {

		HashMap<String, Object> properties = new HashMap<>();

		int startingPort = 9310;

		String range = String.valueOf(startingPort);

		if (size > 1) {
			int endingPort = startingPort + size - 1;
			range = range + StringPool.MINUS + endingPort;
		}

		properties.put("discoveryZenPingUnicastHostsPort", range);
		properties.put("transportTcpPort", range);

		return properties;
	}

	private ElasticsearchFixture _createNode(int index) throws Exception {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			_prefix + "-" + index, _elasticsearchConfigurationProperties);

		elasticsearchFixture.setClusterSettingsContext(
			new TestClusterSettingsContext());

		elasticsearchFixture.createNode();

		_elasticsearchFixtures[index] = elasticsearchFixture;

		return elasticsearchFixture;
	}

	private final HashMap<String, Object> _elasticsearchConfigurationProperties;
	private final ElasticsearchFixture[] _elasticsearchFixtures;
	private final String _prefix;

	private static class TestClusterSettingsContext
		implements ClusterSettingsContext {

		@Override
		public String[] getHosts() {
			return new String[] {"127.0.0.1"};
		}

		@Override
		public InetAddress getLocalBindInetAddress() {
			return InetAddress.getLoopbackAddress();
		}

		@Override
		public NetworkInterface getLocalBindNetworkInterface() {
			return Mockito.mock(NetworkInterface.class);
		}

		@Override
		public boolean isClusterEnabled() {
			return true;
		}

	}

}