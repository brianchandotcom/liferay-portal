/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.snapshot;

/**
 * @author Michael C. Han
 */
public class RestoreSnapshotResponse implements SnapshotResponse {

	public RestoreSnapshotResponse(
		String snapshotName, String[] indexNames, int totalShards,
		int successfulShards) {

		_snapshotName = snapshotName;
		_indexNames = indexNames;
		_totalShards = totalShards;
		_successfulShards = successfulShards;
	}

	public String[] getIndexNames() {
		return _indexNames;
	}

	public String getSnapshotName() {
		return _snapshotName;
	}

	public int getSuccessfulShards() {
		return _successfulShards;
	}

	public int getTotalShards() {
		return _totalShards;
	}

	private final String[] _indexNames;
	private final String _snapshotName;
	private final int _successfulShards;
	private final int _totalShards;

}