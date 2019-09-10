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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchFixture;
import com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.cluster.ClusterRequestExecutorFixture;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.cluster.ClusterHealthStatus;
import com.liferay.portal.search.engine.adapter.cluster.ClusterRequestExecutor;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.HealthClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StateClusterResponse;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterRequest;
import com.liferay.portal.search.engine.adapter.cluster.StatsClusterResponse;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Dylan Rebelak
 */
public class ElasticsearchSearchEngineAdapterClusterRequestTest {

	@Before
	public void setUp() throws Exception {
		_elasticsearchFixture = new ElasticsearchFixture(getClass());

		_elasticsearchFixture.setUp();

		_searchEngineAdapter = createSearchEngineAdapter(_elasticsearchFixture);

		createIndex();
	}

	@After
	public void tearDown() throws Exception {
		deleteIndex();

		_elasticsearchFixture.tearDown();
	}

	@Test
	public void testExecuteHealthClusterRequest() throws JSONException {
		HealthClusterRequest healthClusterRequest = new HealthClusterRequest(
			new String[] {_INDEX_NAME});

		HealthClusterResponse healthClusterResponse =
			_searchEngineAdapter.execute(healthClusterRequest);

		ClusterHealthStatus clusterHealthStatus =
			healthClusterResponse.getClusterHealthStatus();

		Assert.assertTrue(
			clusterHealthStatus.equals(ClusterHealthStatus.GREEN) ||
			clusterHealthStatus.equals(ClusterHealthStatus.YELLOW));

		String healthStatusMessage =
			healthClusterResponse.getHealthStatusMessage();

		JSONFactory jsonFactory = new JSONFactoryImpl();

		JSONObject jsonObject = jsonFactory.createJSONObject(
			healthStatusMessage);

		Assert.assertEquals(
			"LiferayElasticsearchCluster",
			jsonObject.getString("cluster_name"));
		Assert.assertEquals(
			_ELASTICSEARCH_DEFAULT_NUMBER_OF_SHARDS,
			jsonObject.getString("active_shards"));
	}

	@Test
	public void testExecuteStateClusterRequest() throws JSONException {
		StateClusterRequest stateClusterRequest = new StateClusterRequest(
			new String[] {_INDEX_NAME});

		StateClusterResponse stateClusterResponse =
			_searchEngineAdapter.execute(stateClusterRequest);

		String stateMessage = stateClusterResponse.getStateMessage();

		JSONFactory jsonFactory = new JSONFactoryImpl();

		JSONObject jsonObject = jsonFactory.createJSONObject(stateMessage);

		String nodesString = jsonObject.getString("nodes");

		Assert.assertTrue(nodesString.contains("127.0.0.1"));
	}

	@Test
	public void testExecuteStatsClusterRequest() throws JSONException {
		StatsClusterRequest statsClusterRequest = new StatsClusterRequest(
			new String[] {_INDEX_NAME});

		StatsClusterResponse statsClusterResponse =
			_searchEngineAdapter.execute(statsClusterRequest);

		ClusterHealthStatus clusterHealthStatus =
			statsClusterResponse.getClusterHealthStatus();

		Assert.assertTrue(
			clusterHealthStatus.equals(ClusterHealthStatus.GREEN) ||
			clusterHealthStatus.equals(ClusterHealthStatus.YELLOW));

		String statusMessage = statsClusterResponse.getStatsMessage();

		JSONFactory jsonFactory = new JSONFactoryImpl();

		JSONObject jsonObject = jsonFactory.createJSONObject(statusMessage);

		JSONObject indicesJSONObject = jsonObject.getJSONObject("indices");

		Assert.assertEquals("1", indicesJSONObject.getString("count"));
	}

	protected static ClusterRequestExecutor createClusterRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		ClusterRequestExecutorFixture clusterRequestExecutorFixture =
			new ClusterRequestExecutorFixture() {
				{
					setElasticsearchClientResolver(elasticsearchClientResolver);
				}
			};

		clusterRequestExecutorFixture.setUp();

		return clusterRequestExecutorFixture.getClusterRequestExecutor();
	}

	protected static SearchEngineAdapter createSearchEngineAdapter(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		return new ElasticsearchSearchEngineAdapterImpl() {
			{
				setClusterRequestExecutor(
					createClusterRequestExecutor(elasticsearchClientResolver));
			}
		};
	}

	protected void createIndex() {
		Client client = _elasticsearchFixture.getClient();

		AdminClient adminClient = client.admin();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(_INDEX_NAME);

		createIndexRequestBuilder.get();
	}

	protected void deleteIndex() {
		Client client = _elasticsearchFixture.getClient();

		AdminClient adminClient = client.admin();

		IndicesAdminClient indicesAdminClient = adminClient.indices();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(_INDEX_NAME);

		deleteIndexRequestBuilder.get();
	}

	private static final String _ELASTICSEARCH_DEFAULT_NUMBER_OF_SHARDS = "1";

	private static final String _INDEX_NAME = "test_request_index";

	private ElasticsearchFixture _elasticsearchFixture;
	private SearchEngineAdapter _searchEngineAdapter;

}