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
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;

/**
 * @author Michael C. Han
 */
public class SnapshotRequestExecutorFixture {

	public SnapshotRequestExecutor getSnapshotRequestExecutor() {
		return _snapshotRequestExecutor;
	}

	public void setUp() {
		_snapshotRequestExecutor = new ElasticsearchSnapshotRequestExecutor() {
			{
				createSnapshotRepositoryRequestExecutor =
					createCreateSnapshotRepositoryRequestExecutor(
						_elasticsearchClientResolver);
				createSnapshotRequestExecutor =
					createCreateSnapshotRequestExecutor(
						_elasticsearchClientResolver);
				deleteSnapshotRequestExecutor =
					createDeleteSnapshotRequestExecutor(
						_elasticsearchClientResolver);
				getSnapshotRepositoriesRequestExecutor =
					createGetSnapshotRepositoriesRequestExecutor(
						_elasticsearchClientResolver);
				getSnapshotsRequestExecutor = createGetSnapshotsRequestExecutor(
					_elasticsearchClientResolver);
				restoreSnapshotRequestExecutor =
					createRestoreSnapshotRequestExecutor(
						_elasticsearchClientResolver);
			}
		};
	}

	protected static CreateSnapshotRepositoryRequestExecutor
		createCreateSnapshotRepositoryRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CreateSnapshotRepositoryRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static CreateSnapshotRequestExecutor
		createCreateSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new CreateSnapshotRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static DeleteSnapshotRequestExecutor
		createDeleteSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new DeleteSnapshotRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static GetSnapshotRepositoriesRequestExecutor
		createGetSnapshotRepositoriesRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetSnapshotRepositoriesRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static GetSnapshotsRequestExecutor
		createGetSnapshotsRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new GetSnapshotsRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected static RestoreSnapshotRequestExecutor
		createRestoreSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		return new RestoreSnapshotRequestExecutorImpl() {
			{
				setElasticsearchClientResolver(elasticsearchClientResolver);
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SnapshotRequestExecutor _snapshotRequestExecutor;

}