/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.connection;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.indices.ElasticsearchIndicesClient;
import co.elastic.clients.elasticsearch.indices.GetIndexRequest;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.json.JsonpMapper;

import com.liferay.portal.kernel.util.ListUtil;

import java.io.IOException;

import java.util.Map;

/**
 * @author André de Oliveira
 */
public class ElasticsearchFixture implements ElasticsearchClientResolver {

	public ElasticsearchFixture() {
		this(
			_elasticsearchConnectionFixtureSingleton.
				getElasticsearchConnectionFixture(),
			true);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public ElasticsearchFixture(Class<?> clazz) {
		this();
	}

	public ElasticsearchFixture(
		ElasticsearchConnectionFixture elasticsearchConnectionFixture) {

		this(elasticsearchConnectionFixture, false);
	}

	public ElasticsearchFixture(
		ElasticsearchConnectionFixture elasticsearchConnectionFixture,
		boolean singleton) {

		_elasticsearchConnectionFixture = elasticsearchConnectionFixture;
		_singleton = singleton;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public ElasticsearchFixture(String subdirName) {
		this();
	}

	@Override
	public ElasticsearchClient getElasticsearchClient() {
		return _elasticsearchConnectionFixture.getElasticsearchClient();
	}

	@Override
	public ElasticsearchClient getElasticsearchClient(String connectionId) {
		return getElasticsearchClient();
	}

	@Override
	public ElasticsearchClient getElasticsearchClient(
		String connectionId, boolean preferLocalCluster) {

		return getElasticsearchClient();
	}

	public Map<String, Object> getElasticsearchConfigurationProperties() {
		return _elasticsearchConnectionFixture.
			getElasticsearchConfigurationProperties();
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnectionFixture.getElasticsearchConnection();
	}

	public GetIndexResponse getIndex(String... indices) {
		ElasticsearchClient elasticsearchClient = getElasticsearchClient();

		ElasticsearchIndicesClient elasticsearchIndicesClient =
			elasticsearchClient.indices();

		try {
			return elasticsearchIndicesClient.get(
				GetIndexRequest.of(
					getIndexRequest -> getIndexRequest.index(
						ListUtil.fromArray(indices))));
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public JsonpMapper getJsonpMapper(String connectionId) {
		ElasticsearchConnection elasticsearchConnection =
			getElasticsearchConnection();

		return elasticsearchConnection.getJsonpMapper();
	}

	public void setUp() throws Exception {
		if (_singleton) {
			_elasticsearchConnectionFixtureSingleton.start();

			return;
		}

		_elasticsearchConnectionFixture.createNode();
	}

	public void tearDown() throws Exception {
		if (!_singleton) {
			_elasticsearchConnectionFixture.destroyNode();
		}
	}

	public void waitForElasticsearchToStart() {
		ClusterHealthResponseUtil.getHealthResponse(
			this,
			new HealthExpectations() {
				{
					setActivePrimaryShards(0);
					setActiveShards(0);
					setHealthStatus(HealthStatus.Green);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setUnassignedShards(0);
				}
			});
	}

	private static final ElasticsearchConnectionFixtureSingleton
		_elasticsearchConnectionFixtureSingleton =
			new ElasticsearchConnectionFixtureSingleton();

	private final ElasticsearchConnectionFixture
		_elasticsearchConnectionFixture;
	private final boolean _singleton;

	private static class ElasticsearchConnectionFixtureSingleton {

		public void start() {
			if (!_connected) {
				_elasticsearchConnectionFixture.createNode();

				_connected = true;
			}
		}

		protected ElasticsearchConnectionFixture
			getElasticsearchConnectionFixture() {

			return _elasticsearchConnectionFixture;
		}

		private ElasticsearchConnectionFixtureSingleton() {
			_elasticsearchConnectionFixture =
				ElasticsearchConnectionFixture.builder(
				).clusterName(
					ElasticsearchFixture.class.getSimpleName()
				).build();
		}

		private boolean _connected;
		private final ElasticsearchConnectionFixture
			_elasticsearchConnectionFixture;

	}

}