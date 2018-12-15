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

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotDetails;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotRequest;
import org.elasticsearch.action.admin.cluster.snapshots.create.CreateSnapshotResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = CreateSnapshotRequestExecutor.class)
public class CreateSnapshotRequestExecutorImpl
	implements CreateSnapshotRequestExecutor {

	@Override
	public
		com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotResponse
			execute(
				com.liferay.portal.search.engine.adapter.snapshot.
					CreateSnapshotRequest createSnapshotRequest) {

		CreateSnapshotRequest elasticsearchCreateSnapshotRequest =
			createCreateSnapshotRequest(createSnapshotRequest);

		CreateSnapshotResponse elasticsearchCreateSnapshotResponse =
			getCreateSnapshotResponse(elasticsearchCreateSnapshotRequest);

		SnapshotDetails snapshotDetails = SnapshotInfoConverter.convert(
			elasticsearchCreateSnapshotResponse.getSnapshotInfo());

		return new com.liferay.portal.search.engine.adapter.snapshot.
			CreateSnapshotResponse(snapshotDetails);
	}

	protected CreateSnapshotRequest createCreateSnapshotRequest(
		com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest
			createSnapshotRequest) {

		CreateSnapshotRequest elasticsearchCreateSnapshotRequest =
			new CreateSnapshotRequest();

		if (ArrayUtil.isNotEmpty(createSnapshotRequest.getIndexNames())) {
			elasticsearchCreateSnapshotRequest.indices(
				createSnapshotRequest.getIndexNames());
		}

		elasticsearchCreateSnapshotRequest.repository(
			createSnapshotRequest.getRepositoryName());
		elasticsearchCreateSnapshotRequest.snapshot(
			createSnapshotRequest.getSnapshotName());
		elasticsearchCreateSnapshotRequest.waitForCompletion(
			createSnapshotRequest.isWaitForCompletion());

		return elasticsearchCreateSnapshotRequest;
	}

	protected CreateSnapshotResponse getCreateSnapshotResponse(
		CreateSnapshotRequest createSnapshotRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.create(
				createSnapshotRequest, RequestOptions.DEFAULT);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}