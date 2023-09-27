/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Bryan Engler
 */
public class ClosePointInTimeRequest
	extends CrossClusterRequest
	implements SearchRequest<ClosePointInTimeResponse> {

	public ClosePointInTimeRequest(String pointInTimeId) {
		_pointInTimeId = pointInTimeId;

		setPreferLocalCluster(true);
	}

	@Override
	public ClosePointInTimeResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

	public String getPointInTimeId() {
		return _pointInTimeId;
	}

	private final String _pointInTimeId;

}