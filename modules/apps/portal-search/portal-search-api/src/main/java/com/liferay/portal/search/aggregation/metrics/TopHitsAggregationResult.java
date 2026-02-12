/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.hits.SearchHits;

/**
 * @author Michael C. Han
 */
public class TopHitsAggregationResult extends AggregationResult {

	public TopHitsAggregationResult(String name, SearchHits searchHits) {
		super(name);

		_searchHits = searchHits;
	}

	public SearchHits getSearchHits() {
		return _searchHits;
	}

	private final SearchHits _searchHits;

}