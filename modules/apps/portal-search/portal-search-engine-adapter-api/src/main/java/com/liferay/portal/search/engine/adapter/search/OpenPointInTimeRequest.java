/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Bryan Engler
 */
public class OpenPointInTimeRequest
	extends CrossClusterRequest
	implements SearchRequest<OpenPointInTimeResponse> {

	public OpenPointInTimeRequest(long keepAliveMinutes) {
		_keepAliveMinutes = keepAliveMinutes;

		setPreferLocalCluster(true);
	}

	@Override
	public OpenPointInTimeResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

	public String[] getIndices() {
		return _indices;
	}

	public long getKeepAliveMinutes() {
		return _keepAliveMinutes;
	}

	public void setIndices(String... indices) {
		_indices = indices;
	}

	private String[] _indices;
	private final long _keepAliveMinutes;

}