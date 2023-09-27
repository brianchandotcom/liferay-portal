/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.engine.adapter.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class MultisearchSearchResponse implements SearchResponse {

	public void addSearchResponse(SearchSearchResponse searchSearchResponse) {
		_searchSearchResponses.add(searchSearchResponse);
	}

	public List<SearchSearchResponse> getSearchSearchResponses() {
		return _searchSearchResponses;
	}

	private final List<SearchSearchResponse> _searchSearchResponses =
		new ArrayList<>();

}