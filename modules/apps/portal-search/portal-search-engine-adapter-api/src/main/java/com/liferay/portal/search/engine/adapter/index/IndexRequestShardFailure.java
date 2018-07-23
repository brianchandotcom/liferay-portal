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

/**
 * @author Michael C. Han
 */
public class IndexRequestShardFailure {

	public String getIndex() {
		return _index;
	}

	public String getReason() {
		return _reason;
	}

	public int getRestStatus() {
		return _restStatus;
	}

	public int getShardId() {
		return _shardId;
	}

	public void setIndex(String index) {
		_index = index;
	}

	public void setReason(String reason) {
		_reason = reason;
	}

	public void setRestStatus(int restStatus) {
		_restStatus = restStatus;
	}

	public void setShardId(int shardId) {
		_shardId = shardId;
	}

	private String _index;
	private String _reason;
	private int _restStatus;
	private int _shardId;

}