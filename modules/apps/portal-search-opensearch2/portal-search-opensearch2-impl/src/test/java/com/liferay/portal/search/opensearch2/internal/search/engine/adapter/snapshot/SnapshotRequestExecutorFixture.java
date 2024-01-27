/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.search.engine.adapter.snapshot;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.engine.adapter.snapshot.SnapshotRequestExecutor;
import com.liferay.portal.search.opensearch2.internal.connection.OpenSearchConnectionManager;

/**
 * @author Michael C. Han
 */
public class SnapshotRequestExecutorFixture {

	public SnapshotRequestExecutor getSnapshotRequestExecutor() {
		return _snapshotRequestExecutor;
	}

	public void setUp() {
		_snapshotRequestExecutor = new OpenSearchSnapshotRequestExecutor() {
			{
				createSnapshotRepositoryRequestExecutor =
					_createCreateSnapshotRepositoryRequestExecutor(
						_openSearchConnectionManager);
				createSnapshotRequestExecutor =
					_createCreateSnapshotRequestExecutor(
						_openSearchConnectionManager);
				deleteSnapshotRequestExecutor =
					_createDeleteSnapshotRequestExecutor(
						_openSearchConnectionManager);
				getSnapshotRepositoriesRequestExecutor =
					_createGetSnapshotRepositoriesRequestExecutor(
						_openSearchConnectionManager);
				getSnapshotsRequestExecutor =
					_createGetSnapshotsRequestExecutor(
						_openSearchConnectionManager);
				restoreSnapshotRequestExecutor =
					_createRestoreSnapshotRequestExecutor(
						_openSearchConnectionManager);
			}
		};
	}

	protected void setOpenSearchConnectionManager(
		OpenSearchConnectionManager openSearchConnectionManager) {

		_openSearchConnectionManager = openSearchConnectionManager;
	}

	private CreateSnapshotRepositoryRequestExecutor
		_createCreateSnapshotRepositoryRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		CreateSnapshotRepositoryRequestExecutor
			createSnapshotRepositoryRequestExecutor =
				new CreateSnapshotRepositoryRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createSnapshotRepositoryRequestExecutor,
			"_openSearchConnectionManager", openSearchConnectionManager);

		return createSnapshotRepositoryRequestExecutor;
	}

	private CreateSnapshotRequestExecutor _createCreateSnapshotRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		CreateSnapshotRequestExecutor createSnapshotRequestExecutor =
			new CreateSnapshotRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			createSnapshotRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return createSnapshotRequestExecutor;
	}

	private DeleteSnapshotRequestExecutor _createDeleteSnapshotRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		DeleteSnapshotRequestExecutor deleteSnapshotRequestExecutor =
			new DeleteSnapshotRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			deleteSnapshotRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return deleteSnapshotRequestExecutor;
	}

	private GetSnapshotRepositoriesRequestExecutor
		_createGetSnapshotRepositoriesRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		GetSnapshotRepositoriesRequestExecutor
			getSnapshotRepositoriesRequestExecutor =
				new GetSnapshotRepositoriesRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getSnapshotRepositoriesRequestExecutor,
			"_openSearchConnectionManager", openSearchConnectionManager);

		return getSnapshotRepositoriesRequestExecutor;
	}

	private GetSnapshotsRequestExecutor _createGetSnapshotsRequestExecutor(
		OpenSearchConnectionManager openSearchConnectionManager) {

		GetSnapshotsRequestExecutor getSnapshotsRequestExecutor =
			new GetSnapshotsRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			getSnapshotsRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return getSnapshotsRequestExecutor;
	}

	private RestoreSnapshotRequestExecutor
		_createRestoreSnapshotRequestExecutor(
			OpenSearchConnectionManager openSearchConnectionManager) {

		RestoreSnapshotRequestExecutor restoreSnapshotRequestExecutor =
			new RestoreSnapshotRequestExecutorImpl();

		ReflectionTestUtil.setFieldValue(
			restoreSnapshotRequestExecutor, "_openSearchConnectionManager",
			openSearchConnectionManager);

		return restoreSnapshotRequestExecutor;
	}

	private OpenSearchConnectionManager _openSearchConnectionManager;
	private SnapshotRequestExecutor _snapshotRequestExecutor;

}