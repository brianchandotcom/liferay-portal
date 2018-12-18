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

package com.liferay.portal.search.solr7.internal.search.engine.adapter.snapshot;

import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRepositoryResponse;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.CreateSnapshotResponse;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.DeleteSnapshotResponse;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotRepositoriesResponse;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsRequest;
import com.liferay.portal.search.engine.adapter.snapshot.GetSnapshotsResponse;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotRequest;
import com.liferay.portal.search.engine.adapter.snapshot.RestoreSnapshotResponse;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(
	immediate = true, property = "search.engine.impl=Solr",
	service = SnapshotRequestExecutor.class
)
public class SolrSnapshotRequestExecutor implements SnapshotRequestExecutor {

	@Override
	public CreateSnapshotRepositoryResponse executeSnapshotRequest(
		CreateSnapshotRepositoryRequest createSnapshotRepositoryRequest) {

		return _createSnapshotRepositoryRequestExecutor.execute(
			createSnapshotRepositoryRequest);
	}

	@Override
	public CreateSnapshotResponse executeSnapshotRequest(
		CreateSnapshotRequest createSnapshotRequest) {

		return _createSnapshotRequestExecutor.execute(createSnapshotRequest);
	}

	@Override
	public DeleteSnapshotResponse executeSnapshotRequest(
		DeleteSnapshotRequest deleteSnapshotRequest) {

		return _deleteSnapshotRequestExecutor.execute(deleteSnapshotRequest);
	}

	@Override
	public GetSnapshotRepositoriesResponse executeSnapshotRequest(
		GetSnapshotRepositoriesRequest getSnapshotRepositoriesRequest) {

		return _getSnapshotRepositoriesRequestExecutor.execute(
			getSnapshotRepositoriesRequest);
	}

	@Override
	public GetSnapshotsResponse executeSnapshotRequest(
		GetSnapshotsRequest getSnapshotsRequest) {

		return _getSnapshotsRequestExecutor.execute(getSnapshotsRequest);
	}

	@Override
	public RestoreSnapshotResponse executeSnapshotRequest(
		RestoreSnapshotRequest restoreSnapshotRequest) {

		return _restoreSnapshotRequestExecutor.execute(restoreSnapshotRequest);
	}

	@Reference(unbind = "-")
	protected void setCreateSnapshotRepositoryRequestExecutor(
		CreateSnapshotRepositoryRequestExecutor
			createSnapshotRepositoryRequestExecutor) {

		_createSnapshotRepositoryRequestExecutor =
			createSnapshotRepositoryRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setCreateSnapshotRequestExecutor(
		CreateSnapshotRequestExecutor createSnapshotRequestExecutor) {

		_createSnapshotRequestExecutor = createSnapshotRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setDeleteSnapshotRequestExecutor(
		DeleteSnapshotRequestExecutor deleteSnapshotRequestExecutor) {

		_deleteSnapshotRequestExecutor = deleteSnapshotRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setGetSnapshotRepositoriesRequestExecutor(
		GetSnapshotRepositoriesRequestExecutor
			getSnapshotRepositoriesRequestExecutor) {

		_getSnapshotRepositoriesRequestExecutor =
			getSnapshotRepositoriesRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setGetSnapshotsRequestExecutor(
		GetSnapshotsRequestExecutor getSnapshotsRequestExecutor) {

		_getSnapshotsRequestExecutor = getSnapshotsRequestExecutor;
	}

	@Reference(unbind = "-")
	protected void setRestoreSnapshotRequestExecutor(
		RestoreSnapshotRequestExecutor restoreSnapshotRequestExecutor) {

		_restoreSnapshotRequestExecutor = restoreSnapshotRequestExecutor;
	}

	private CreateSnapshotRepositoryRequestExecutor
		_createSnapshotRepositoryRequestExecutor;
	private CreateSnapshotRequestExecutor _createSnapshotRequestExecutor;
	private DeleteSnapshotRequestExecutor _deleteSnapshotRequestExecutor;
	private GetSnapshotRepositoriesRequestExecutor
		_getSnapshotRepositoriesRequestExecutor;
	private GetSnapshotsRequestExecutor _getSnapshotsRequestExecutor;
	private RestoreSnapshotRequestExecutor _restoreSnapshotRequestExecutor;

}