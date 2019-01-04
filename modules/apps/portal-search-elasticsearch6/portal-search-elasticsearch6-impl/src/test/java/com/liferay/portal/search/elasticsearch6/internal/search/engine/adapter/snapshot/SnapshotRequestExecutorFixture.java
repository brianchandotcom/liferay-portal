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

	public SnapshotRequestExecutor getSnapshotRequestExecutor() {
		return _snapshotRequestExecutor;
	}

	public void setUp() {
		ElasticsearchClientResolver elasticsearchClientResolver1 =
			elasticsearchClientResolver;

		_snapshotRequestExecutor = new ElasticsearchSnapshotRequestExecutor() {
			{
				createSnapshotRepositoryRequestExecutor =
					createCreateSnapshotRepositoryRequestExecutor(
						elasticsearchClientResolver1);
				createSnapshotRequestExecutor =
					createCreateSnapshotRequestExecutor(
						elasticsearchClientResolver1);
				deleteSnapshotRequestExecutor =
					createDeleteSnapshotRequestExecutor(
						elasticsearchClientResolver1);
				getSnapshotRepositoriesRequestExecutor =
					createGetSnapshotRepositoriesRequestExecutor(
						elasticsearchClientResolver1);
				getSnapshotsRequestExecutor = createGetSnapshotsRequestExecutor(
					elasticsearchClientResolver1);
				restoreSnapshotRequestExecutor =
					createRestoreSnapshotRequestExecutor(
						elasticsearchClientResolver1);
			}
		};
	}

	protected static CreateSnapshotRepositoryRequestExecutor
		createCreateSnapshotRepositoryRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new CreateSnapshotRepositoryRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static CreateSnapshotRequestExecutor
		createCreateSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new CreateSnapshotRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static DeleteSnapshotRequestExecutor
		createDeleteSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new DeleteSnapshotRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static GetSnapshotRepositoriesRequestExecutor
		createGetSnapshotRepositoriesRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new GetSnapshotRepositoriesRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static GetSnapshotsRequestExecutor
		createGetSnapshotsRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new GetSnapshotsRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected static RestoreSnapshotRequestExecutor
		createRestoreSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver1) {

		return new RestoreSnapshotRequestExecutorImpl() {
			{
				elasticsearchClientResolver = elasticsearchClientResolver1;
			}
		};
	}

	protected ElasticsearchClientResolver elasticsearchClientResolver;

	private SnapshotRequestExecutor _snapshotRequestExecutor;

}