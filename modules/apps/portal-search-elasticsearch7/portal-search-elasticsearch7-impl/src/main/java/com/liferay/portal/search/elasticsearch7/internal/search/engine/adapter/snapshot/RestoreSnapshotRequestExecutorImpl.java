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

package com.liferay.portal.search.elasticsearch7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotResponse;

import java.io.IOException;

import java.util.List;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;
import org.elasticsearch.snapshots.RestoreInfo;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = RestoreSnapshotRequestExecutor.class)
public class RestoreSnapshotRequestExecutorImpl
	implements RestoreSnapshotRequestExecutor {

	@Override
	public RestoreSnapshotResponse execute(
		RestoreSnapshotRequest restoreSnapshotRequest) {

		org.elasticsearch.action.admin.cluster.snapshots.restore.
			RestoreSnapshotRequest elasticsearchRestoreSnapshotRequest =
				createRestoreSnapshotRequest(restoreSnapshotRequest);

		org.elasticsearch.action.admin.cluster.snapshots.restore.
			RestoreSnapshotResponse elasticsearchRestoreSnapshotResponse =
				_getRestoreSnapshotResponse(
					elasticsearchRestoreSnapshotRequest,
					restoreSnapshotRequest);

		RestoreInfo restoreInfo =
			elasticsearchRestoreSnapshotResponse.getRestoreInfo();

		List<String> indexNames = restoreInfo.indices();

		return new RestoreSnapshotResponse(
			restoreInfo.name(), indexNames.toArray(new String[0]),
			restoreInfo.totalShards(), restoreInfo.failedShards());
	}

	protected org.elasticsearch.action.admin.cluster.snapshots.restore.
		RestoreSnapshotRequest createRestoreSnapshotRequest(
			RestoreSnapshotRequest restoreSnapshotRequest) {

		org.elasticsearch.action.admin.cluster.snapshots.restore.
			RestoreSnapshotRequest elasticsearchRestoreSnapshotRequest =
				new org.elasticsearch.action.admin.cluster.snapshots.restore.
					RestoreSnapshotRequest();

		elasticsearchRestoreSnapshotRequest.includeAliases(
			restoreSnapshotRequest.isIncludeAliases());
		elasticsearchRestoreSnapshotRequest.indices(
			restoreSnapshotRequest.getIndexNames());
		elasticsearchRestoreSnapshotRequest.partial(
			restoreSnapshotRequest.isPartialRestore());

		if (Validator.isNotNull(
				restoreSnapshotRequest.getRenameReplacement())) {

			elasticsearchRestoreSnapshotRequest.renameReplacement(
				restoreSnapshotRequest.getRenameReplacement());
		}

		if (Validator.isNotNull(restoreSnapshotRequest.getRenamePattern())) {
			elasticsearchRestoreSnapshotRequest.renamePattern(
				restoreSnapshotRequest.getRenamePattern());
		}

		elasticsearchRestoreSnapshotRequest.repository(
			restoreSnapshotRequest.getRepositoryName());
		elasticsearchRestoreSnapshotRequest.includeGlobalState(
			restoreSnapshotRequest.isRestoreGlobalState());
		elasticsearchRestoreSnapshotRequest.snapshot(
			restoreSnapshotRequest.getSnapshotName());
		elasticsearchRestoreSnapshotRequest.waitForCompletion(
			restoreSnapshotRequest.isWaitForCompletion());

		return elasticsearchRestoreSnapshotRequest;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private org.elasticsearch.action.admin.cluster.snapshots.restore.
		RestoreSnapshotResponse _getRestoreSnapshotResponse(
			org.elasticsearch.action.admin.cluster.snapshots.restore.
				RestoreSnapshotRequest elasticsearchRestoreSnapshotRequest,
			RestoreSnapshotRequest restoreSnapshotRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient(
				restoreSnapshotRequest.getConnectionId(),
				restoreSnapshotRequest.isPreferLocalCluster());

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.restore(
				elasticsearchRestoreSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}