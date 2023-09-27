/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

import com.liferay.portal.search.engine.adapter.ccr.CrossClusterRequest;

/**
 * @author Gustavo Lima
 */
public class ClearScrollRequest
	extends CrossClusterRequest implements SearchRequest<ClearScrollResponse> {

	public ClearScrollRequest(String scrollId) {
		_scrollId = scrollId;

		setPreferLocalCluster(true);
	}

	@Override
	public ClearScrollResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

	public String getScrollId() {
		return _scrollId;
	}

	private final String _scrollId;

}