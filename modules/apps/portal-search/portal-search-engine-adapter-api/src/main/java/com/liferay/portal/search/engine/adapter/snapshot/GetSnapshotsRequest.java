/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class GetSnapshotsRequest
	extends CrossClusterRequest
	implements SnapshotRequest<GetSnapshotsResponse> {

	public GetSnapshotsRequest(String repositoryName) {
		_repositoryName = repositoryName;
	}

	@Override
	public GetSnapshotsResponse accept(
		SnapshotRequestExecutor snapshotRequestExecutor) {

		return snapshotRequestExecutor.executeSnapshotRequest(this);
	}

	public String getRepositoryName() {
		return _repositoryName;
	}

	public String[] getSnapshotNames() {
		return _snapshotNames;
	}

	public boolean isIgnoreUnavailable() {
		return _ignoreUnavailable;
	}

	public boolean isVerbose() {
		return _verbose;
	}

	public void setIgnoreUnavailable(boolean ignoreUnavailable) {
		_ignoreUnavailable = ignoreUnavailable;
	}

	public void setSnapshotNames(String... snapshotNames) {
		_snapshotNames = snapshotNames;
	}

	public void setVerbose(boolean verbose) {
		_verbose = verbose;
	}

	private boolean _ignoreUnavailable = true;
	private final String _repositoryName;
	private String[] _snapshotNames;
	private boolean _verbose;

}