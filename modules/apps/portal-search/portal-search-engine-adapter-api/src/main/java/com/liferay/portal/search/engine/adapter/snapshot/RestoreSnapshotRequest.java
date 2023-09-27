/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class RestoreSnapshotRequest
	extends CrossClusterRequest
	implements SnapshotRequest<RestoreSnapshotResponse> {

	public RestoreSnapshotRequest(String repositoryName, String snapshotName) {
		_repositoryName = repositoryName;
		_snapshotName = snapshotName;
	}

	@Override
	public RestoreSnapshotResponse accept(
		SnapshotRequestExecutor snapshotRequestExecutor) {

		return snapshotRequestExecutor.executeSnapshotRequest(this);
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public String getRenamePattern() {
		return _renamePattern;
	}

	public String getRenameReplacement() {
		return _renameReplacement;
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String getSnapshotName() {
		return _snapshotName;
	}

	public boolean isIncludeAliases() {
		return _includeAliases;
	}

	public boolean isPartialRestore() {
		return _partialRestore;
	}

	public boolean isRestoreGlobalState() {
		return _restoreGlobalState;
	}

	public boolean isWaitForCompletion() {
		return _waitForCompletion;
	}

	public void setIncludeAliases(boolean includeAliases) {
		_includeAliases = includeAliases;
	}

	public void setIndexNames(String... indexNames) {
		_indexNames = indexNames;
	}

	public void setPartialRestore(boolean partialRestore) {
		_partialRestore = partialRestore;
	}

	public void setRenamePattern(String renamePattern) {
		_renamePattern = renamePattern;
	}

	public void setRenameReplacement(String renameReplacement) {
		_renameReplacement = renameReplacement;
	}

	public void setRestoreGlobalState(boolean restoreGlobalState) {
		_restoreGlobalState = restoreGlobalState;
	}

	public void setWaitForCompletion(boolean waitForCompletion) {
		_waitForCompletion = waitForCompletion;
	}

	private boolean _includeAliases = true;
	private String[] _indexNames;
	private boolean _partialRestore = true;
	private String _renamePattern;
	private String _renameReplacement;
	private final String _repositoryName;
	private boolean _restoreGlobalState = true;
	private final String _snapshotName;
	private boolean _waitForCompletion = true;

}