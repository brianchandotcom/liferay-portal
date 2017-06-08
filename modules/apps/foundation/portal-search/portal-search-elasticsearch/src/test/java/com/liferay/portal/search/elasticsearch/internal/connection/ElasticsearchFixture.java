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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.internal.cluster.ClusterSettingsContext;
import com.liferay.portal.search.elasticsearch.internal.cluster.UnicastSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.ClientSettingsHelper;
import com.liferay.portal.util.FileImpl;

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
import org.elasticsearch.action.admin.indices.get.GetIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.unit.TimeValue;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class ElasticsearchFixture implements IndicesAdminClientSupplier {

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

	public void createNode() throws Exception {
		deleteTmpDir();

		_embeddedElasticsearchConnection = createElasticsearchConnection();

		ReflectionTestUtil.setFieldValue(
			_embeddedElasticsearchConnection, "_file", new FileImpl());
	}

	public void destroyNode() throws Exception {
		if (_embeddedElasticsearchConnection != null) {
			_embeddedElasticsearchConnection.close();
		}

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

	public Map<String, Object> getElasticsearchConfigurationProperties() {
		return _elasticsearchConfigurationProperties;
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

	@Override
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

	protected void addClusterLoggingThresholdContributor(
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection) {

		embeddedElasticsearchConnection.addSettingsContributor(
			new BaseSettingsContributor(0) {

				@Override
				public void populate(
					ClientSettingsHelper clientSettingsHelper) {

					clientSettingsHelper.put(
						"cluster.service.slow_task_logging_threshold", "600s");
				}

			});
	}

	protected void addDiskThresholdSettingsContributor(
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection) {

		embeddedElasticsearchConnection.addSettingsContributor(
			new BaseSettingsContributor(0) {

				@Override
				public void populate(
					ClientSettingsHelper clientSettingsHelper) {

					clientSettingsHelper.put(
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

		embeddedElasticsearchConnection.clusterSettingsContext =
			clusterSettingsContext;

		embeddedElasticsearchConnection.props = props;

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