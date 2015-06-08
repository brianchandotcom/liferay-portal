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

import com.liferay.portal.search.elasticsearch.internal.connection.ElasticsearchFixture;

/**
 * @author André de Oliveira
 */
public class TestCluster {

	public TestCluster(int size, Object object) {
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

	private ElasticsearchFixture _createNode(int index) throws Exception {
		ElasticsearchFixture elasticsearchFixture = new ElasticsearchFixture(
			_prefix + "-" + index);

		elasticsearchFixture.setClusterSettingsContext(
			new TestClusterSettingsContext(index));

		elasticsearchFixture.createNode();

		_elasticsearchFixtures[index] = elasticsearchFixture;

		return elasticsearchFixture;
	}

	private final ElasticsearchFixture[] _elasticsearchFixtures;
	private final String _prefix;

	private static class TestClusterSettingsContext
		implements ClusterSettingsContext {

		public TestClusterSettingsContext(int index) {
			_hosts = _getHosts(index);
		}

		@Override
		public String[] getHosts() {
			return _hosts;
		}

		@Override
		public boolean isClusterEnabled() {
			return true;
		}

		private String[] _getHosts(int index) {
			String[] hosts = new String[index + 1];

			for (int i = 0; i < hosts.length; i++) {
				int port = _STARTING_PORT + i;
				hosts[i] = "127.0.0.1:" + port;
			}

			return hosts;
		}

		private static final int _STARTING_PORT = 9300;

		private final String[] _hosts;

	}

}