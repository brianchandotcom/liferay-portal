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

package com.liferay.portal.search.elasticsearch.internal.connection;

import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch.internal.cluster.UnicastSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequestBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.indices.IndexMissingException;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class ElasticsearchFixture {

	public ElasticsearchFixture(String subdirName) {
		this(subdirName, Collections.<String, Object>emptyMap());
	}

	public ElasticsearchFixture(
		String subdirName,
		Map<String, Object> elasticsearchConfigurationProperties) {

		_elasticsearchConfigurationProperties =
			createElasticsearchConfigurationProperties(
				elasticsearchConfigurationProperties);

		_tmpDirName = "tmp/" + subdirName;
	}

	public Index createIndex(String indexName) {
		return createIndex(indexName, Mockito.mock(IndexCreationHelper.class));
	}

	public Index createIndex(
		String indexName, IndexCreationHelper indexCreationHelper) {

		indexName = StringUtil.toLowerCase(indexName);

		IndicesAdminClient indicesAdminClient = getIndicesAdminClient();

		DeleteIndexRequestBuilder deleteIndexRequestBuilder =
			indicesAdminClient.prepareDelete(indexName);

		try {
			deleteIndexRequestBuilder.get();
		}
		catch (IndexMissingException ime) {
		}

		CreateIndexRequestBuilder createIndexRequestBuilder =
			indicesAdminClient.prepareCreate(indexName);

		indexCreationHelper.contribute(createIndexRequestBuilder);

		createIndexRequestBuilder.get();

		return new Index(indexName);
	}

	public void createNode() throws Exception {
		deleteTmpDir();

		_embeddedElasticsearchConnection = createElasticsearchConnection();
	}

	public void destroyNode() throws Exception {
		_embeddedElasticsearchConnection.close();

		deleteTmpDir();
	}

	public AdminClient getAdminClient() {
		Client client = getClient();

		return client.admin();
	}

	public Client getClient() {
		return _embeddedElasticsearchConnection.getClient();
	}

	public ClusterHealthResponse getClusterHealthResponse(
		HealthExpectations healthExpectations) {

		AdminClient adminClient = getAdminClient();

		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		ClusterHealthRequestBuilder clusterHealthRequestBuilder =
			clusterAdminClient.prepareHealth();

		ClusterHealthRequest clusterHealthRequest =
			clusterHealthRequestBuilder.request();

		clusterHealthRequest.timeout(new TimeValue(10, TimeUnit.MINUTES));
		clusterHealthRequest.waitForActiveShards(
			healthExpectations.activeShards);
		clusterHealthRequest.waitForNodes(
			String.valueOf(healthExpectations.numberOfNodes));
		clusterHealthRequest.waitForRelocatingShards(0);
		clusterHealthRequest.waitForStatus(healthExpectations.status);

		ActionFuture<ClusterHealthResponse> health = clusterAdminClient.health(
			clusterHealthRequest);

		return health.actionGet();
	}

	public EmbeddedElasticsearchConnection
		getEmbeddedElasticsearchConnection() {

		return _embeddedElasticsearchConnection;
	}

	public GetIndexResponse getIndex(String... indices) {
		IndicesAdminClient indicesAdminClient = getIndicesAdminClient();

		GetIndexRequestBuilder getIndexRequestBuilder =
			indicesAdminClient.prepareGetIndex();

		getIndexRequestBuilder.addIndices(indices);

		return getIndexRequestBuilder.get();
	}

	public IndicesAdminClient getIndicesAdminClient() {
		AdminClient adminClient = getAdminClient();

		return adminClient.indices();
	}

	public void setClusterSettingsContext(
		ClusterSettingsContext clusterSettingsContext) {

		_clusterSettingsContext = clusterSettingsContext;
	}

	public void setUp() throws Exception {
		createNode();
	}

	public void tearDown() throws Exception {
		destroyNode();
	}

	public class Index {

		public Index(String name) {
			_name = name;
		}

		public String getName() {
			return _name;
		}

		private final String _name;

	}

	protected void addClusterLoggingThresholdContributor(
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection) {

		embeddedElasticsearchConnection.addSettingsContributor(
			new BaseSettingsContributor(0) {

				@Override
				public void populate(Builder builder) {
					builder.put(
						"cluster.service.cluster.service." +
							"slow_task_logging_threshold",
						"600s");
				}

			});
	}

	protected void addDiskThresholdSettingsContributor(
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection) {

		embeddedElasticsearchConnection.addSettingsContributor(
			new BaseSettingsContributor(0) {

				@Override
				public void populate(Builder builder) {
					builder.put(
						"cluster.routing.allocation.disk.threshold_enabled",
						"false");
				}

			});
	}

	protected void addUnicastSettingsContributor(
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection) {

		if (_clusterSettingsContext == null) {
			return;
		}

		UnicastSettingsContributor unicastSettingsContributor =
			new UnicastSettingsContributor() {
				{
					setClusterSettingsContext(_clusterSettingsContext);

					activate(_elasticsearchConfigurationProperties);
				}
			};

		embeddedElasticsearchConnection.addSettingsContributor(
			unicastSettingsContributor);
	}

	protected Map<String, Object> createElasticsearchConfigurationProperties(
		Map<String, Object> elasticsearchConfigurationProperties) {

		Map<String, Object> map = new HashMap<>(
			elasticsearchConfigurationProperties);

		map.put("configurationPid", ElasticsearchConfiguration.class.getName());
		map.put("logExceptionsOnly", false);

		return map;
	}

	protected EmbeddedElasticsearchConnection createElasticsearchConnection() {
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection =
			new EmbeddedElasticsearchConnection();

		addClusterLoggingThresholdContributor(embeddedElasticsearchConnection);
		addDiskThresholdSettingsContributor(embeddedElasticsearchConnection);
		addUnicastSettingsContributor(embeddedElasticsearchConnection);

		Props props = Mockito.mock(Props.class);

		Mockito.when(
			props.get(PropsKeys.LIFERAY_HOME)
		).thenReturn(
			_tmpDirName
		);

		ClusterSettingsContext clusterSettingsContext = _clusterSettingsContext;

		if (clusterSettingsContext == null) {
			clusterSettingsContext = Mockito.mock(ClusterSettingsContext.class);
		}

		embeddedElasticsearchConnection.setClusterSettingsContext(
			clusterSettingsContext);

		embeddedElasticsearchConnection.setProps(props);

		embeddedElasticsearchConnection.activate(
			_elasticsearchConfigurationProperties);

		embeddedElasticsearchConnection.connect();

		return embeddedElasticsearchConnection;
	}

	protected void deleteTmpDir() throws Exception {
		FileUtils.deleteDirectory(new File(_tmpDirName));
	}

	private ClusterSettingsContext _clusterSettingsContext;
	private final Map<String, Object> _elasticsearchConfigurationProperties;
	private EmbeddedElasticsearchConnection _embeddedElasticsearchConnection;
	private final String _tmpDirName;

}