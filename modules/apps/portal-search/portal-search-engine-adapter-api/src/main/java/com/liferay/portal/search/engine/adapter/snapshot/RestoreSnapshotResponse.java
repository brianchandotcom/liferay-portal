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