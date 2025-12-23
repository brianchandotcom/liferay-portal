/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.snapshot.ElasticsearchSnapshotClient;
import co.elastic.clients.elasticsearch.snapshot.GetSnapshotRequest;
import co.elastic.clients.elasticsearch.snapshot.GetSnapshotResponse;
import co.elastic.clients.elasticsearch.snapshot.SnapshotInfo;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse;

import java.io.IOException;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class GetSnapshotsRequestExecutor {

	public GetSnapshotsRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public GetSnapshotsResponse execute(
		GetSnapshotsRequest getSnapshotsRequest) {

		GetSnapshotsResponse getSnapshotsResponse = new GetSnapshotsResponse();

		GetSnapshotResponse getSnapshotResponse = _getGetSnapshotResponse(
			_createGetSnapshotRequest(getSnapshotsRequest),
			getSnapshotsRequest);

		List<SnapshotInfo> snapshotInfos = getSnapshotResponse.snapshots();

		snapshotInfos.forEach(
			snapshotInfo -> getSnapshotsResponse.addSnapshotInfo(
				SnapshotInfoConverter.convert(snapshotInfo)));

		return getSnapshotsResponse;
	}

	private GetSnapshotRequest _createGetSnapshotRequest(
		GetSnapshotsRequest getSnapshotsRequest) {

		return GetSnapshotRequest.of(
			getSnapshotRequest -> getSnapshotRequest.ignoreUnavailable(
				getSnapshotsRequest.isIgnoreUnavailable()
			).repository(
				getSnapshotsRequest.getRepositoryName()
			).snapshot(
				ListUtil.fromArray(getSnapshotsRequest.getSnapshotNames())
			).verbose(
				getSnapshotsRequest.isVerbose()
			));
	}

	private GetSnapshotResponse _getGetSnapshotResponse(
		GetSnapshotRequest getSnapshotRequest,
		GetSnapshotsRequest getSnapshotsRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				getSnapshotsRequest.getConnectionId(),
				getSnapshotsRequest.isPreferLocalCluster());

		ElasticsearchSnapshotClient elasticsearchSnapshotClient =
			elasticsearchClient.snapshot();

		try {
			return elasticsearchSnapshotClient.get(getSnapshotRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}