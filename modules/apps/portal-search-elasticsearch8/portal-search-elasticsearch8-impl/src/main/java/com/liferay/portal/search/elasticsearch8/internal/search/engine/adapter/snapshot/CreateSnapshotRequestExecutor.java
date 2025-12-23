/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch8.internal.search.engine.adapter.snapshot;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.snapshot.CreateSnapshotRequest.Builder;
import co.elastic.clients.elasticsearch.snapshot.ElasticsearchSnapshotClient;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch8.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotResponse;

import java.io.IOException;

import java.util.Arrays;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRequestExecutor {

	public CreateSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public CreateSnapshotResponse execute(
		CreateSnapshotRequest createSnapshotRequest) {

		co.elastic.clients.elasticsearch.snapshot.CreateSnapshotResponse
			elasticsearchCreateSnapshotResponse = _getCreateSnapshotResponse(
				createSnapshotRequest,
				createCreateSnapshotRequest(createSnapshotRequest));

		return new CreateSnapshotResponse(
			SnapshotInfoConverter.convert(
				elasticsearchCreateSnapshotResponse.snapshot()));
	}

	protected co.elastic.clients.elasticsearch.snapshot.CreateSnapshotRequest
		createCreateSnapshotRequest(
			CreateSnapshotRequest createSnapshotRequest) {

		Builder builder = new Builder();

		if (ArrayUtil.isNotEmpty(createSnapshotRequest.getIndexNames())) {
			builder.indices(
				Arrays.asList(createSnapshotRequest.getIndexNames()));
		}

		builder.repository(createSnapshotRequest.getRepositoryName());
		builder.snapshot(createSnapshotRequest.getSnapshotName());
		builder.waitForCompletion(createSnapshotRequest.isWaitForCompletion());

		return builder.build();
	}

	private co.elastic.clients.elasticsearch.snapshot.CreateSnapshotResponse
		_getCreateSnapshotResponse(
			CreateSnapshotRequest createSnapshotRequest,
			co.elastic.clients.elasticsearch.snapshot.CreateSnapshotRequest
				elasticsearchCreateSnapshotRequest) {

		ElasticsearchClient elasticsearchClient =
			_elasticsearchClientResolver.getElasticsearchClient(
				createSnapshotRequest.getConnectionId(),
				createSnapshotRequest.isPreferLocalCluster());

		ElasticsearchSnapshotClient elasticsearchSnapshotClient =
			elasticsearchClient.snapshot();

		try {
			return elasticsearchSnapshotClient.create(
				elasticsearchCreateSnapshotRequest);
		}
		catch (IOException ioException) {
			throw new RuntimeException(ioException);
		}
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}