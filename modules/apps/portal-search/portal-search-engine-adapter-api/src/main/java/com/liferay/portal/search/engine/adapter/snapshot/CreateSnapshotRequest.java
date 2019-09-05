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

import com.liferay.petra.string.StringPool;

/**
 * @author Michael C. Han
 */
public class CreateSnapshotRequest
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