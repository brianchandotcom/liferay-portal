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

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.internal.aggregation.BaseAggregation;
import com.liferay.portal.search.query.Query;

/**
 * @author Michael C. Han
 */
public class FilterAggregationImpl
	extends BaseAggregation implements FilterAggregation {

	public FilterAggregationImpl(String name, Query query) {
		super(name);

		_filterQuery = query;
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public Query getFilterQuery() {
		return _filterQuery;
	}

	@Override
	public void setFilterQuery(Query filterQuery) {
		_filterQuery = filterQuery;
	}

	private Query _filterQuery;

}