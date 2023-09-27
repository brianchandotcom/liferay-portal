/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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