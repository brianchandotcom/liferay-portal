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

package com.liferay.portal.search.elasticsearch6.internal.search.engine.adapter.snapshot;

import com.liferay.portal.search.elasticsearch6.internal.connection.ElasticsearchClientResolver;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;

/**
 * @author Michael C. Han
 */
public class SnapshotRequestExecutorFixture {

	public SnapshotRequestExecutorFixture(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	public SnapshotRequestExecutor createExecutor() {
		return new ElasticsearchSnapshotRequestExecutor() {
			{

				createSnapshotRepositoryRequestExecutor =
					createCreateSnapshotRepositoryRequestExecutor();
				createSnapshotRequestExecutor =
					createCreateSnapshotRequestExecutor();
				deleteSnapshotRequestExecutor =
					createDeleteSnapshotRequestExecutor();
				getSnapshotRepositoriesRequestExecutor =
					createGetSnapshotRepositoriesRequestExecutor();
				getSnapshotsRequestExecutor =
					createGetSnapshotsRequestExecutor();
				restoreSnapshotRequestExecutor =
					createRestoreSnapshotRequestExecutor();
			}
		};
	}

	protected CreateSnapshotRepositoryRequestExecutor
		createCreateSnapshotRepositoryRequestExecutor() {

		return new CreateSnapshotRepositoryRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected CreateSnapshotRequestExecutor
		createCreateSnapshotRequestExecutor() {

		return new CreateSnapshotRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected DeleteSnapshotRequestExecutor
		createDeleteSnapshotRequestExecutor() {

		return new DeleteSnapshotRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected GetSnapshotRepositoriesRequestExecutor
		createGetSnapshotRepositoriesRequestExecutor() {

		return new GetSnapshotRepositoriesRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected GetSnapshotsRequestExecutor createGetSnapshotsRequestExecutor() {
		return new GetSnapshotsRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	protected RestoreSnapshotRequestExecutor
		createRestoreSnapshotRequestExecutor() {

		return new RestoreSnapshotRequestExecutorImpl() {
			{
				elasticsearchClientResolver = _elasticsearchClientResolver;
			}
		};
	}

	private final ElasticsearchClientResolver _elasticsearchClientResolver;

}