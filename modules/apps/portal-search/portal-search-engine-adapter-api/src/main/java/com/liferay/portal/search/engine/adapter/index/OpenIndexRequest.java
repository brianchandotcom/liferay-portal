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

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Michael C. Han
 */
public class OpenIndexRequest
	extends CrossClusterRequest implements IndexRequest<OpenIndexResponse> {

	public OpenIndexRequest(String... indexNames) {
		_indexNames = indexNames;
	}

	@Override
	public OpenIndexResponse accept(IndexRequestExecutor indexRequestExecutor) {
		return indexRequestExecutor.executeIndexRequest(this);
	}

	@Override
	public String[] getIndexNames() {
		return _indexNames;
	}

	public IndicesOptions getIndicesOptions() {
		return _indicesOptions;
	}

	public long getTimeout() {
		return _timeout;
	}

	public int getWaitForActiveShards() {
		return _waitForActiveShards;
	}

	public void setIndicesOptions(IndicesOptions indicesOptions) {
		_indicesOptions = indicesOptions;
	}

	public void setTimeout(long timeout) {
		_timeout = timeout;
	}

	public void setWaitForActiveShards(int waitForActiveShards) {
		_waitForActiveShards = waitForActiveShards;
	}

	private final String[] _indexNames;
	private IndicesOptions _indicesOptions;
	private long _timeout;
	private int _waitForActiveShards;

}