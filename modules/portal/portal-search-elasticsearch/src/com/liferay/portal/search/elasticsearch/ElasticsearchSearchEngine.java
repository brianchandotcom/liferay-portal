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
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.search.elasticsearch.connection.ElasticsearchConnectionManager;
import com.liferay.portal.search.elasticsearch.index.IndexFactory;
import com.liferay.portal.search.elasticsearch.util.LogUtil;

import java.util.concurrent.Future;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthStatus;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesRequestBuilder;
import org.elasticsearch.action.admin.cluster.repositories.get.GetRepositoriesResponse;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryRequestBuilder;
import org.elasticsearch.action.admin.cluster.repositories.put.PutRepositoryResponse;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotResponse;
import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotRequestBuilder;
import org.elasticsearch.action.admin.cluster.snapshots.restore.RestoreSnapshotResponse;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.cluster.metadata.RepositoryMetaData;
import org.elasticsearch.common.collect.ImmutableList;
import org.elasticsearch.common.settings.ImmutableSettings;

/**
 * @author Michael C. Han
 */
public class ElasticsearchSearchEngine extends BaseSearchEngine {

	@Override
	public synchronized String backup(long companyId, String backupName)
		throws SearchException {

		ClusterAdminClient clusterAdminClient =
			_elasticsearchConnectionManager.getClusterAdminClient();

		CreateSnapshotRequestBuilder createSnapshotRequestBuilder =
			clusterAdminClient.prepareCreateSnapshot(
				_BACKUP_REPOSITORY_NAME, backupName);

		createSnapshotRequestBuilder.setWaitForCompletion(true);

		try {
			createBackupRepository(clusterAdminClient);

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

		ClusterHealthResponse clusterHealthResponse =
			_elasticsearchConnectionManager.getClusterHealthResponse();

		if (clusterHealthResponse.getStatus() == ClusterHealthStatus.RED) {
			throw new IllegalStateException(
				"Unable to initialize Elasticsearch cluster: " +
					clusterHealthResponse);
		}

		try {
			_indexFactory.createIndices(
				_elasticsearchConnectionManager.getAdminClient(), companyId);
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public synchronized void removeBackup(long companyId, String backupName)
		throws SearchException {

		ClusterAdminClient clusterAdminClient =
			_elasticsearchConnectionManager.getClusterAdminClient();

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

		try {
			_indexFactory.deleteIndices(
				_elasticsearchConnectionManager.getAdminClient(), companyId);
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

		ClusterAdminClient clusterAdminClient =
			_elasticsearchConnectionManager.getClusterAdminClient();

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
			_elasticsearchConnectionManager.getClusterHealthResponse();

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

	protected void createBackupRepository(ClusterAdminClient clusterAdminClient)
		throws Exception {

		GetRepositoriesRequestBuilder getRepositoriesRequestBuilder =
			clusterAdminClient.prepareGetRepositories(_BACKUP_REPOSITORY_NAME);

		Future<GetRepositoriesResponse> getRepositoriesResponseFuture =
			getRepositoriesRequestBuilder.execute();

		GetRepositoriesResponse getRepositoriesResponse =
			getRepositoriesResponseFuture.get();

		ImmutableList<RepositoryMetaData> repositoryMetaDatas =
			getRepositoriesResponse.repositories();

		if (!repositoryMetaDatas.isEmpty()) {
			return;
		}

		PutRepositoryRequestBuilder putRepositoryRequestBuilder =
			clusterAdminClient.preparePutRepository(_BACKUP_REPOSITORY_NAME);

		ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder();

		String location = SystemProperties.get("java.io.tmpdir") + "/es_backup";

		builder.put("location", location);

		putRepositoryRequestBuilder.setSettings(builder);

		putRepositoryRequestBuilder.setType("fs");

		Future<PutRepositoryResponse> putRepositoryResponseFuture =
			putRepositoryRequestBuilder.execute();

		PutRepositoryResponse putRepositoryResponse =
			putRepositoryResponseFuture.get();

		LogUtil.logActionResponse(_log, putRepositoryResponse);
	}

	private static final String _BACKUP_REPOSITORY_NAME = "liferay_backup";

	private static final Log _log = LogFactoryUtil.getLog(
		ElasticsearchSearchEngine.class);

	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private IndexFactory _indexFactory;

}