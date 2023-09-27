/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public FilterAggregationImpl(String name, Query filterQuery) {
		super(name);

		_filterQuery = filterQuery;
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