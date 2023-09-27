/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRequest
	extends CrossClusterRequest
	implements SnapshotRequest<CreateSnapshotResponse> {

	public CreateSnapshotRequest(String repositoryName, String snapshotName) {
		_repositoryName = repositoryName;
		_snapshotName = snapshotName;
	}

	@Override
	public CreateSnapshotResponse accept(
		SnapshotRequestExecutor snapshotRequestExecutor) {

		return snapshotRequestExecutor.executeSnapshotRequest(this);
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getSnapshotName() {
		return _snapshotName;
	}

	public boolean isWaitForCompletion() {
		return _waitForCompletion;
	}

	public void setIndexNames(String... indexNames) {
		_indexNames = indexNames;
	}

	public void setWaitForCompletion(boolean waitForCompletion) {
		_waitForCompletion = waitForCompletion;
	}

	private String[] _indexNames = StringPool.EMPTY_ARRAY;
	private final String _repositoryName;
	private final String _snapshotName;
	private boolean _waitForCompletion = true;

}