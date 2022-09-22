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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
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
					_createCreateSnapshotRepositoryRequestExecutor(
						_elasticsearchClientResolver);
				createSnapshotRequestExecutor =
					_createCreateSnapshotRequestExecutor(
						_elasticsearchClientResolver);
				deleteSnapshotRequestExecutor =
					_createDeleteSnapshotRequestExecutor(
						_elasticsearchClientResolver);
				getSnapshotRepositoriesRequestExecutor =
					_createGetSnapshotRepositoriesRequestExecutor(
						_elasticsearchClientResolver);
				getSnapshotsRequestExecutor =
					_createGetSnapshotsRequestExecutor(
						_elasticsearchClientResolver);
				restoreSnapshotRequestExecutor =
					_createRestoreSnapshotRequestExecutor(
						_elasticsearchClientResolver);
			}
		};
	}

	protected void setElasticsearchClientResolver(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		_elasticsearchClientResolver = elasticsearchClientResolver;
	}

	private CreateSnapshotRepositoryRequestExecutor
		_createCreateSnapshotRepositoryRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		CreateSnapshotRepositoryRequestExecutor
			createSnapshotRepositoryRequestExecutor =
				new CreateSnapshotRepositoryRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createSnapshotRepositoryRequestExecutor,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		return createSnapshotRepositoryRequestExecutor;
	}

	private CreateSnapshotRequestExecutor _createCreateSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		CreateSnapshotRequestExecutor createSnapshotRequestExecutor =
			new CreateSnapshotRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createSnapshotRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return createSnapshotRequestExecutor;
	}

	private DeleteSnapshotRequestExecutor _createDeleteSnapshotRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		DeleteSnapshotRequestExecutor deleteSnapshotRequestExecutor =
			new DeleteSnapshotRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteSnapshotRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return deleteSnapshotRequestExecutor;
	}

	private GetSnapshotRepositoriesRequestExecutor
		_createGetSnapshotRepositoriesRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		GetSnapshotRepositoriesRequestExecutor
			getSnapshotRepositoriesRequestExecutor =
				new GetSnapshotRepositoriesRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getSnapshotRepositoriesRequestExecutor,
			"_elasticsearchClientResolver", elasticsearchClientResolver);

		return getSnapshotRepositoriesRequestExecutor;
	}

	private GetSnapshotsRequestExecutor _createGetSnapshotsRequestExecutor(
		ElasticsearchClientResolver elasticsearchClientResolver) {

		GetSnapshotsRequestExecutor getSnapshotsRequestExecutor =
			new GetSnapshotsRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getSnapshotsRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return getSnapshotsRequestExecutor;
	}

	private RestoreSnapshotRequestExecutor
		_createRestoreSnapshotRequestExecutor(
			ElasticsearchClientResolver elasticsearchClientResolver) {

		RestoreSnapshotRequestExecutor restoreSnapshotRequestExecutor =
			new RestoreSnapshotRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			restoreSnapshotRequestExecutor, "_elasticsearchClientResolver",
			elasticsearchClientResolver);

		return restoreSnapshotRequestExecutor;
	}

	private ElasticsearchClientResolver _elasticsearchClientResolver;
	private SnapshotRequestExecutor _snapshotRequestExecutor;

}