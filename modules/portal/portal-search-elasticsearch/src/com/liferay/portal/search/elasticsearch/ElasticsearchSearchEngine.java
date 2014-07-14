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

package com.liferay.portal.search.elasticsearch;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseSearchEngine;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnection;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.util.LogUtil;

import java.util.concurrent.Future;

import com.opensymphony.oscache.util.StringUtil;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotResponse;
import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngine extends BaseSearchEngine {

	@Override
	public synchronized String backup(long companyId, String backupName)
		throws SearchException {

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		Client client = elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();
		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		CreateSnapshotRequestBuilder createSnapshotRequestBuilder =
			clusterAdminClient.prepareCreateSnapshot(
				_BACKUP_REPOSITORY_NAME, backupName);

		createSnapshotRequestBuilder.setWaitForCompletion(true);

		try {
			Future<CreateSnapshotResponse> future =
				createSnapshotRequestBuilder.execute();

			CreateSnapshotResponse createSnapshotResponse = future.get();

			LogUtil.logActionResponse(_log, createSnapshotResponse);

			return backupName;
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void initialize(long companyId) {
		super.initialize(companyId);

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		ClusterHealthResponse clusterHealthResponse =
			elasticsearchConnection.getClusterHealthResponse();

		if (clusterHealthResponse.getStatus() == ClusterHealthStatus.RED) {
			throw new IllegalStateException(
				"Unable to initialize Elasticsearch cluster: " +
					clusterHealthResponse);
		}

		Client client = elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();

		try {
			_indexFactory.createIndices(adminClient, companyId);
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public synchronized void removeBackup(long companyId, String backupName)
		throws SearchException {

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		Client client = elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();
		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		DeleteSnapshotRequestBuilder deleteSnapshotRequestBuilder =
			clusterAdminClient.prepareDeleteSnapshot(
				_BACKUP_REPOSITORY_NAME, backupName);

		try {
			Future<DeleteSnapshotResponse> future =
				deleteSnapshotRequestBuilder.execute();

			DeleteSnapshotResponse deleteSnapshotResponse = future.get();

			LogUtil.logActionResponse(_log, deleteSnapshotResponse);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void removeCompany(long companyId) {
		super.removeCompany(companyId);

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		Client client = elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();

		try {
			_indexFactory.deleteIndices(adminClient, companyId);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete index for " + companyId, e);
			}
		}
	}

	@Override
	public synchronized void restore(long companyId, String backupName)
		throws SearchException {

		ElasticsearchConnection elasticsearchConnection =
			_elasticsearchConnectionManager.getElasticsearchConnection();

		Client client = elasticsearchConnection.getClient();

		AdminClient adminClient = client.admin();
		ClusterAdminClient clusterAdminClient = adminClient.cluster();

		RestoreSnapshotRequestBuilder restoreSnapshotRequestBuilder =
			clusterAdminClient.prepareRestoreSnapshot(
				_BACKUP_REPOSITORY_NAME, backupName);

		try {
			Future<RestoreSnapshotResponse> future =
				restoreSnapshotRequestBuilder.execute();

			RestoreSnapshotResponse restoreSnapshotResponse = future.get();

			LogUtil.logActionResponse(_log, restoreSnapshotResponse);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}

		ClusterHealthResponse clusterHealthResponse =
			elasticsearchConnection.getClusterHealthResponse();

		if (clusterHealthResponse.getStatus() == ClusterHealthStatus.RED) {
			throw new IllegalStateException(
				"Unable to initialize Elasticsearch cluster: " +
					clusterHealthResponse);
		}
	}

	public void setElasticsearchConnectionManager(
		ElasticsearchConnectionManager elasticsearchConnectionManager) {

		_elasticsearchConnectionManager = elasticsearchConnectionManager;
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		_indexFactory = indexFactory;
	}

	private static final String _BACKUP_REPOSITORY_NAME = "backup_liferay";

	private static Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngine.class);

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private IndexFactory _indexFactory;

}