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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchConnectionFixture;
import com.liferay.portal.search.elasticsearch7.internal.connection.HttpPortRange;

import java.util.HashMap;

/**
 * @author André de Oliveira
 */
public class TestCluster {

	public TestCluster(int size, Object object) {
		String prefix = _getPrefix(object);

		_elasticsearchFixtures = new ElasticsearchConnectionFixture[size];

		_prefix = prefix;
	}

	public ElasticsearchConnectionFixture createNode(int number) {
		ElasticsearchConnectionFixture elasticsearchConnectionFixture =
			ElasticsearchConnectionFixture.builder(
			).clusterName(
				_prefix + "-" + number
			).discoveryTypeZen(
				true
			).elasticsearchConfigurationProperties(
				_createElasticsearchConfigurationProperties(
					_getClusterName(), _getNodeName(number),
					_getPortRange(9310, _elasticsearchFixtures.length))
			).build();

		elasticsearchConnectionFixture.createNode();

		_elasticsearchFixtures[number - 1] = elasticsearchConnectionFixture;

		return elasticsearchConnectionFixture;
	}

	public void createNodes() {
		for (int i = 1; i <= _elasticsearchFixtures.length; i++) {
			createNode(i);
		}
	}

	public void destroyNode(int number) {
		if (_elasticsearchFixtures[number - 1] != null) {
			_elasticsearchFixtures[number - 1].destroyNode();

			_elasticsearchFixtures[number - 1] = null;
		}
	}

	public void destroyNodes() {
		for (int i = 1; i <= _elasticsearchFixtures.length; i++) {
			destroyNode(i);
		}
	}

	public ElasticsearchConnectionFixture getNode(int index) {
		return _elasticsearchFixtures[index - 1];
	}

	public void setUp() {
		createNodes();
	}

	public void tearDown() {
		destroyNodes();
	}

	private HashMap<String, Object> _createElasticsearchConfigurationProperties(
		String clusterName, String nodeName, String transportRange) {

		return HashMapBuilder.<String, Object>put(
			"clusterName", clusterName
		).put(
			"discoveryZenPingUnicastHostsPort", transportRange
		).put(
			"nodeName", nodeName
		).put(
			"sidecarHttpPort", HttpPortRange.AUTO
		).put(
			"transportTcpPort", transportRange
		).build();
	}

	private String _getClusterName() {
		return _prefix + "-Cluster";
	}

	private String _getNodeName(int number) {
		return _prefix + "-Node-" + number;
	}

	private String _getPortRange(int startingPort, int size) {
		if (size > 1) {
			return String.valueOf(startingPort) + StringPool.MINUS +
				String.valueOf(startingPort + size - 1);
		}

		return String.valueOf(startingPort);
	}

	private String _getPrefix(Object object) {
		Class<?> clazz = object.getClass();

		return clazz.getSimpleName();
	}

	private final ElasticsearchConnectionFixture[] _elasticsearchFixtures;
	private final String _prefix;

}