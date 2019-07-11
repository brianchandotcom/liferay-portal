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
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotResponse;

import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotAction;
import org.elasticsearch.action.admin.cluster.snapshots.delete.DeleteSnapshotRequestBuilder;
import org.elasticsearch.action.support.master.AcknowledgedResponse;

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
		DeleteSnapshotRequest deleteSnapshotRequest) {

		DeleteSnapshotRequestBuilder deleteSnapshotRequestBuilder =
			createDeleteSnapshotRequestBuilder(deleteSnapshotRequest);

		AcknowledgedResponse acknowledgedResponse =
			deleteSnapshotRequestBuilder.get();

		return new DeleteSnapshotResponse(
			acknowledgedResponse.isAcknowledged());
	}

	protected DeleteSnapshotRequestBuilder createDeleteSnapshotRequestBuilder(
		DeleteSnapshotRequest deleteSnapshotRequest) {

		DeleteSnapshotRequestBuilder deleteSnapshotRequestBuilder =
			new DeleteSnapshotRequestBuilder(
				_elasticsearchClientResolver.getClient(),
				DeleteSnapshotAction.INSTANCE);

		deleteSnapshotRequestBuilder.setRepository(
			deleteSnapshotRequest.getRepositoryName());
		deleteSnapshotRequestBuilder.setSnapshot(
			deleteSnapshotRequest.getSnapshotName());

		return deleteSnapshotRequestBuilder;
	}

	@Reference(unbind = "-")
	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;

}