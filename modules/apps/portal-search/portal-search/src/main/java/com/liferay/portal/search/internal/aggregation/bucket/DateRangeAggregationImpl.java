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
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.Range;

/**
 * @author Michael C. Han
 */
public class DateRangeAggregationImpl
	extends RangeAggregationImpl implements DateRangeAggregation {

	public DateRangeAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit((DateRangeAggregation)this);
	}

	@Override
	public void addRange(String fromDate, String toDate) {
		addRange(new Range(fromDate, toDate));
	}

	@Override
	public void addRange(String key, String fromDate, String toDate) {
		addRange(new Range(key, fromDate, toDate));
	}

	@Override
	public void addUnboundedFrom(String fromDate) {
		addRange(new Range(fromDate, null));
	}

	@Override
	public void addUnboundedFrom(String key, String fromDate) {
		addRange(new Range(key, fromDate, null));
	}

	@Override
	public void addUnboundedTo(String toDate) {
		addRange(new Range(null, toDate));
	}

	@Override
	public void addUnboundedTo(String key, String toDate) {
		addRange(new Range(key, null, toDate));
	}

}