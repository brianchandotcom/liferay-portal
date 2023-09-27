/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.ccr;

/**
 * @author Bryan Engler
 */
public class PauseFollowCCRRequest
	extends CrossClusterRequest implements CCRRequest<PauseFollowCCRResponse> {

	public PauseFollowCCRRequest(String indexName) {
		_indexName = indexName;

		setPreferLocalCluster(true);
	}

	@Override
	public PauseFollowCCRResponse accept(
		CCRRequestExecutor ccrRequestExecutor) {

		return ccrRequestExecutor.executeCCRRequest(this);
	}

	public String getIndexName() {
		return _indexName;
	}

	private final String _indexName;

}