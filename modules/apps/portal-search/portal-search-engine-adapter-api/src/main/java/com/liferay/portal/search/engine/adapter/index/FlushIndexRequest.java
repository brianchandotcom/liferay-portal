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

package com.liferay.portal.search.engine.adapter.index;

import com.liferay.petra.string.StringPool;

/**
 * @author Michael C. Han
 */
public class FlushIndexRequest implements IndexRequest<FlushIndexResponse> {

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

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement. This method
	 *             should not be in the parent interface.  Only certain
	 *             IndexRequests work with mappings.
	 */
	@Deprecated
	@Override
	public String getMappingName() {
		throw new UnsupportedOperationException();
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