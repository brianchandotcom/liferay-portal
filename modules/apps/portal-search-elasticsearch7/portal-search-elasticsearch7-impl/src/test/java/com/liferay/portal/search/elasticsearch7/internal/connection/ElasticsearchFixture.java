/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import java.io.IOException;

import java.util.Map;

import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.health.ClusterHealthStatus;

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

	public Map<String, Object> getElasticsearchConfigurationProperties() {
		return _elasticsearchConnectionFixture.
			getElasticsearchConfigurationProperties();
	}

	public ElasticsearchConnection getElasticsearchConnection() {
		return _elasticsearchConnectionFixture.getElasticsearchConnection();
	}

	public GetIndexResponse getIndex(String... indices) {
		RestHighLevelClient restHighLevelClient = getRestHighLevelClient();

		IndicesClient indicesClient = restHighLevelClient.indices();

		GetIndexRequest getIndexRequest = new GetIndexRequest(indices);

		try {
			return indicesClient.get(getIndexRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient() {
		return _elasticsearchConnectionFixture.getRestHighLevelClient();
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient(String connectionId) {
		return getRestHighLevelClient();
	}

	@Override
	public RestHighLevelClient getRestHighLevelClient(
		String connectionId, boolean preferLocalCluster) {

		return getRestHighLevelClient();
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
		ClusterHealthResponseUtil.getClusterHealthResponse(
			this,
			new HealthExpectations() {
				{
					setActivePrimaryShards(0);
					setActiveShards(0);
					setNumberOfDataNodes(1);
					setNumberOfNodes(1);
					setStatus(ClusterHealthStatus.GREEN);
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