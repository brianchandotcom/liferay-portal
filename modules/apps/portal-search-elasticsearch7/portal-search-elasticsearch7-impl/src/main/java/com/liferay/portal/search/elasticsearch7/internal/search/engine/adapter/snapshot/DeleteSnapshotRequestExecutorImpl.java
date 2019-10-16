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

import com.liferay.portal.search.elasticsearch7.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotResponse;

import java.io.IOException;

import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.SnapshotClient;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(service = DeleteSnapshotRequestExecutor.class)
public class DeleteSnapshotRequestExecutorImpl
	implements DeleteSnapshotRequestExecutor {

	@Override
	public DeleteSnapshotResponse execute(
		com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest
			deleteSnapshotRequest) {

		DeleteSnapshotRequest elasticsearchDeleteSnapshotRequest =
			createDeleteSnapshotRequest(deleteSnapshotRequest);

		AcknowledgedResponse acknowledgedResponse = getAcknowledgedResponse(
			elasticsearchDeleteSnapshotRequest);

		return new DeleteSnapshotResponse(
			acknowledgedResponse.isAcknowledged());
	}

	protected DeleteSnapshotRequest createDeleteSnapshotRequest(
		com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest
			deleteSnapshotRequest) {

		DeleteSnapshotRequest elasticsearchDeleteSnapshotRequest =
			new DeleteSnapshotRequest();

		elasticsearchDeleteSnapshotRequest.repository(
			deleteSnapshotRequest.getRepositoryName());
		elasticsearchDeleteSnapshotRequest.snapshot(
			deleteSnapshotRequest.getSnapshotName());

		return elasticsearchDeleteSnapshotRequest;
	}

	protected AcknowledgedResponse getAcknowledgedResponse(
		DeleteSnapshotRequest deleteSnapshotRequest) {

		RestHighLevelClient restHighLevelClient =
			_elasticsearchClientResolver.getRestHighLevelClient();

		SnapshotClient snapshotClient = restHighLevelClient.snapshot();

		try {
			return snapshotClient.delete(
				deleteSnapshotRequest, RequestOptions.DEFAULT);
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