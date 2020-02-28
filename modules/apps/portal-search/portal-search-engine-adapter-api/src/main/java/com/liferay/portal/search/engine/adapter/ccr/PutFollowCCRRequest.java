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

package com.liferay.portal.search.engine.adapter.ccr;

/**
 * @author Bryan Engler
 */
public class PutFollowCCRRequest
	extends CrossClusterRequest implements CCRRequest<PutFollowCCRResponse> {

	public PutFollowCCRRequest(
		String remoteClusterAlias, String leaderIndexName,
		String followerIndexName) {

		_remoteClusterAlias = remoteClusterAlias;
		_leaderIndexName = leaderIndexName;
		_followerIndexName = followerIndexName;
	}

	@Override
	public PutFollowCCRResponse accept(CCRRequestExecutor ccrRequestExecutor) {
		return ccrRequestExecutor.executeCCRRequest(this);
	}

	public String getFollowerIndexName() {
		return _followerIndexName;
	}

	public String getLeaderIndexName() {
		return _leaderIndexName;
	}

	public String getRemoteClusterAlias() {
		return _remoteClusterAlias;
	}

	public int getWaitForActiveShards() {
		return _waitForActiveShards;
	}

	public void setWaitForActiveShards(int waitForActiveShards) {
		_waitForActiveShards = waitForActiveShards;
	}

	private final String _followerIndexName;
	private final String _leaderIndexName;
	private final String _remoteClusterAlias;
	private int _waitForActiveShards;

}