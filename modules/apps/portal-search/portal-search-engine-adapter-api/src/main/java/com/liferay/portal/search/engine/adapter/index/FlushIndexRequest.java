/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class FlushIndexRequest
	extends CrossClusterRequest implements IndexRequest<FlushIndexResponse> {

	public FlushIndexRequest() {
		_indexNames = StringPool.EMPTY_ARRAY;
	}

	public FlushIndexRequest(String... indexNames) {
		_indexNames = indexNames;
	}

	@Override
	public FlushIndexResponse accept(
		IndexRequestExecutor indexRequestExecutor) {

		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	public boolean isForce() {
		return _force;
	}

	public boolean isWaitIfOngoing() {
		return _waitIfOngoing;
	}

	public void setForce(boolean force) {
		_force = force;
	}

	public void setWaitIfOngoing(boolean waitIfOngoing) {
		_waitIfOngoing = waitIfOngoing;
	}

	private boolean _force;
	private final String[] _indexNames;
	private boolean _waitIfOngoing;

}