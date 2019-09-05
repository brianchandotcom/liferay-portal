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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class FlushIndexResponse implements IndexResponse {

	public void addIndexRequestShardFailure(
		IndexRequestShardFailure indexRequestShardFailure) {

		if (_indexRequestShardFailures == null) {
			_indexRequestShardFailures = new ArrayList<>();
		}

		_indexRequestShardFailures.add(indexRequestShardFailure);
	}

	public int getFailedShards() {
		return _failedShards;
	}

	public List<IndexRequestShardFailure> getIndexRequestShardFailures() {
		return _indexRequestShardFailures;
	}

	public int getRestStatus() {
		return _restStatus;
	}

	public int getSuccessfulShards() {
		return _successfulShards;
	}

	public int getTotalShards() {
		return _totalShards;
	}

	public void setFailedShards(int failedShards) {
		_failedShards = failedShards;
	}

	public void setRestStatus(int restStatus) {
		_restStatus = restStatus;
	}

	public void setSuccessfulShards(int successfulShards) {
		_successfulShards = successfulShards;
	}

	public void setTotalShards(int totalShards) {
		_totalShards = totalShards;
	}

	private int _failedShards;
	private List<IndexRequestShardFailure> _indexRequestShardFailures;
	private int _restStatus;
	private int _successfulShards;
	private int _totalShards;

}