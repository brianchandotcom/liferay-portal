/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

/**
 * @author Michael C. Han
 */
public class SnapshotDetails {

	public SnapshotDetails(String snapshotName, String snapshotUuid) {
		_snapshotName = snapshotName;
		_snapshotUuid = snapshotUuid;
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public SnapshotState getSnapshotState() {
		return _snapshotState;
	}

	public int getSuccessfulShards() {
		return _successfulShards;
	}

	public int getTotalShards() {
		return _totalShards;
	}

	public void setIndexNames(String[] indexNames) {
		_indexNames = indexNames;
	}

	public void setSnapshotState(SnapshotState snapshotState) {
		_snapshotState = snapshotState;
	}

	public void setSuccessfulShards(int successfulShards) {
		_successfulShards = successfulShards;
	}

	public void setTotalShards(int totalShards) {
		_totalShards = totalShards;
	}

	private String[] _indexNames;
	private final String _snapshotName;
	private SnapshotState _snapshotState;
	private final String _snapshotUuid;
	private int _successfulShards;
	private int _totalShards;

}