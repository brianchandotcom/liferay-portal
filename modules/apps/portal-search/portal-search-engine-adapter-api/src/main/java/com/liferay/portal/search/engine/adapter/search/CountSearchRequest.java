/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

/**
 * @author Dylan Rebelak
 */
public class CountSearchRequest
	extends BaseSearchRequest implements SearchRequest<CountSearchResponse> {

	public CountSearchRequest() {
		setPreferLocalCluster(true);
	}

	@Override
	public CountSearchResponse accept(
		SearchRequestExecutor searchRequestExecutor) {

		return searchRequestExecutor.executeSearchRequest(this);
	}

}