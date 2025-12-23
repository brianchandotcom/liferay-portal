/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.snapshot.ElasticsearchSnapshotClient;

import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotResponse;

import java.io.IOException;

/**
 * @author Michael C. Han
 */
public class DeleteSnapshotRequestExecutor {

	public DeleteSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public DeleteSnapshotResponse execute(
		DeleteSnapshotRequest deleteSnapshotRequest) {

		co.elastic.clients.elasticsearch.snapshot.DeleteSnapshotResponse
			deleteSnapshotResponse = _getDeleteSnapshotResponse(
				deleteSnapshotRequest,
				createDeleteSnapshotRequest(deleteSnapshotRequest));

		return new DeleteSnapshotResponse(
			deleteSnapshotResponse.acknowledged());
	}

	protected co.elastic.clients.elasticsearch.snapshot.DeleteSnapshotRequest
		createDeleteSnapshotRequest(
			DeleteSnapshotRequest deleteSnapshotRequest) {

		return co.elastic.clients.elasticsearch.snapshot.DeleteSnapshotRequest.
			of(
				elasticsearchDeleteSnapshotRequest ->
					elasticsearchDeleteSnapshotRequest.repository(
						deleteSnapshotRequest.getRepositoryName()
					).snapshot(
						deleteSnapshotRequest.getSnapshotName()
					));
	}

	private co.elastic.clients.elasticsearch.snapshot.DeleteSnapshotResponse
		_getDeleteSnapshotResponse(
			DeleteSnapshotRequest deleteSnapshotRequest,
			co.elastic.clients.elasticsearch.snapshot.DeleteSnapshotRequest
				elasticsearchDeleteSnapshotRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				deleteSnapshotRequest.getConnectionId(),
				deleteSnapshotRequest.isPreferLocalCluster());

		ElasticsearchSnapshotClient elasticsearchSnapshotClient =
			elasticsearchClient.snapshot();

		try {
			return elasticsearchSnapshotClient.delete(
				elasticsearchDeleteSnapshotRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}