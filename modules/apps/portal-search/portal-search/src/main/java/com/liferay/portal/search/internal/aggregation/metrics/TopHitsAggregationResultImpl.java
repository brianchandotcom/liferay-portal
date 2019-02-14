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

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.internal.aggregation.BaseAggregationResult;

/**
 * @author Michael C. Han
 */
public class TopHitsAggregationResultImpl
	extends BaseAggregationResult implements TopHitsAggregationResult {

	public TopHitsAggregationResultImpl(String name, SearchHits searchHits) {
		super(name);

		_searchHits = searchHits;
	}

	@Override
	public SearchHits getSearchHits() {
		return _searchHits;
	}

	private final SearchHits _searchHits;

}