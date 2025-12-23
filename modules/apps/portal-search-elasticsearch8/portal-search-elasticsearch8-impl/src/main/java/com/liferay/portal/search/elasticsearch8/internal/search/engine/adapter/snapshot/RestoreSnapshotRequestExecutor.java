/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ShardStatistics;
import co.elastic.clients.elasticsearch.snapshot.ElasticsearchSnapshotClient;
import co.elastic.clients.elasticsearch.snapshot.RestoreRequest;
import co.elastic.clients.elasticsearch.snapshot.RestoreResponse;
import co.elastic.clients.elasticsearch.snapshot.restore.SnapshotRestore;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.elasticsearch8.internal.util.ConversionUtil;
import com.liferay.portal.search.elasticsearch8.internal.util.SetterUtil;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotResponse;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class RestoreSnapshotRequestExecutor {

	public RestoreSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public RestoreSnapshotResponse execute(
		RestoreSnapshotRequest restoreSnapshotRequest) {

		RestoreResponse restoreResponse = _getRestoreResponse(
			_createRestoreRequest(restoreSnapshotRequest),
			restoreSnapshotRequest);

		SnapshotRestore snapshotRestore = restoreResponse.snapshot();

		ShardStatistics shardStatistics = snapshotRestore.shards();

		return new RestoreSnapshotResponse(
			snapshotRestore.snapshot(),
			ArrayUtil.toStringArray(snapshotRestore.indices()),
			ConversionUtil.toInt(shardStatistics.total()),
			ConversionUtil.toInt(shardStatistics.failed()));
	}

	private RestoreRequest _createRestoreRequest(
		RestoreSnapshotRequest restoreSnapshotRequest) {

		RestoreRequest.Builder builder = new RestoreRequest.Builder();

		builder.includeAliases(restoreSnapshotRequest.isIncludeAliases());
		builder.includeGlobalState(
			restoreSnapshotRequest.isRestoreGlobalState());
		builder.indices(
			ListUtil.fromArray(restoreSnapshotRequest.getIndexNames()));
		builder.partial(restoreSnapshotRequest.isPartialRestore());

		SetterUtil.setNotBlankString(
			builder::renamePattern, restoreSnapshotRequest.getRenamePattern());
		SetterUtil.setNotBlankString(
			builder::renameReplacement,
			restoreSnapshotRequest.getRenameReplacement());

		builder.repository(restoreSnapshotRequest.getRepositoryName());
		builder.snapshot(restoreSnapshotRequest.getSnapshotName());
		builder.waitForCompletion(restoreSnapshotRequest.isWaitForCompletion());

		return builder.build();
	}

	private RestoreResponse _getRestoreResponse(
		RestoreRequest restoreRequest,
		RestoreSnapshotRequest restoreSnapshotRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				restoreSnapshotRequest.getConnectionId(),
				restoreSnapshotRequest.isPreferLocalCluster());

		ElasticsearchSnapshotClient elasticsearchSnapshotClient =
			elasticsearchClient.snapshot();

		try {
			return elasticsearchSnapshotClient.restore(restoreRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}