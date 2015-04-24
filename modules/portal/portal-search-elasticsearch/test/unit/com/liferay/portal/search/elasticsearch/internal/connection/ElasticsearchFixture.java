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

import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.recovery.RecoveryRequestBuilder;
import org.elasticsearch.action.admin.indices.recovery.RecoveryResponse;
import org.elasticsearch.action.admin.indices.recovery.ShardRecoveryResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.indices.IndexMissingException;
import org.elasticsearch.indices.recovery.RecoveryState;

import org.junit.Assert;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class ElasticsearchFixture {

	public ElasticsearchFixture(String subdir) {
		_tmpDir = "tmp/" + subdir;
	}

	public Index createIndex(String indexName) {
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

		createIndexRequestBuilder.get();

		return new Index(indexName);
	}

	public IndicesAdminClient getIndicesAdminClient() {
		Client client = _elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();

		return adminClient.indices();
	}

	public void setUpClass() throws Exception {
		deleteTmpDir();

		_elasticsearchConnection = createElasticsearchConnection();
	}

	public void tearDownClass() throws Exception {
		_elasticsearchConnection.close();

		deleteTmpDir();
	}

	public class Index {

		public Index(String name) {
			this._name = name;
		}

		public String getName() {
			return _name;
		}

		public RecoveryState[] getRecoveryStates(final int expectedShards)
			throws Exception {

			List<ShardRecoveryResponse> shardRecoveryResponses =
				IdempotentRetryAssert.retryAssert(
					3, TimeUnit.SECONDS,
					new Callable<List<ShardRecoveryResponse>>() {

						@Override
						public List<ShardRecoveryResponse> call()
							throws Exception {

							List<ShardRecoveryResponse> list =
								getShardRecoveryResponses();

							Assert.assertEquals(expectedShards, list.size());

							return list;
						}

					});

			RecoveryState[] recoveryStates = new RecoveryState[expectedShards];

			for (int i = 0; i < expectedShards; i++) {
				ShardRecoveryResponse shardRecoveryResponse =
					shardRecoveryResponses.get(i);
				recoveryStates[i] = shardRecoveryResponse.recoveryState();
			}

			return recoveryStates;
		}

		protected List<ShardRecoveryResponse> getShardRecoveryResponses() {
			IndicesAdminClient indicesAdminClient = getIndicesAdminClient();

			RecoveryRequestBuilder recoveryRequestBuilder =
				indicesAdminClient.prepareRecoveries(_name);

			RecoveryResponse recoveryResponse = recoveryRequestBuilder.get();

			Map<String, List<ShardRecoveryResponse>> shardResponses =
				recoveryResponse.shardResponses();

			return shardResponses.get(_name);
		}

		private final String _name;

	}

	protected ElasticsearchConnection createElasticsearchConnection() {
		EmbeddedElasticsearchConnection embeddedElasticsearchConnection =
			new EmbeddedElasticsearchConnection();

		Props props = Mockito.mock(Props.class);

		Mockito.when(
			props.get(PropsKeys.LIFERAY_HOME)
		).thenReturn(
			_tmpDir
		);

		embeddedElasticsearchConnection.setProps(props);

		HashMap<String, Object> properties = new HashMap<>();

		properties.put(
			"configurationPid", ElasticsearchConfiguration.class.getName());

		embeddedElasticsearchConnection.activate(properties);

		embeddedElasticsearchConnection.initialize();

		return embeddedElasticsearchConnection;
	}

	protected void deleteTmpDir() throws Exception {
		FileUtils.deleteDirectory(new File(_tmpDir));
	}

	private ElasticsearchConnection _elasticsearchConnection;
	private final String _tmpDir;

}