/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		setPreferLocalCluster(true);
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