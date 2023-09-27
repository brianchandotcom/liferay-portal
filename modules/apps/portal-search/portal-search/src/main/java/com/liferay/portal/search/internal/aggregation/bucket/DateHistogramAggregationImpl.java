/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.bucket.DateHistogramAggregation;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class DateHistogramAggregationImpl
	extends BaseFieldAggregation implements DateHistogramAggregation {

	public DateHistogramAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public void addOrders(Order... orders) {
		Collections.addAll(_orders, orders);
	}

	@Override
	public String getDateHistogramInterval() {
		return _dateHistogramInterval;
	}

	@Override
	public Long getInterval() {
		return _interval;
	}

	@Override
	public Boolean getKeyed() {
		return _keyed;
	}

	@Override
	public Long getMaxBound() {
		return _maxBound;
	}

	@Override
	public Long getMinBound() {
		return _minBound;
	}

	@Override
	public Long getMinDocCount() {
		return _minDocCount;
	}

	@Override
	public Long getOffset() {
		return _offset;
	}

	@Override
	public List<Order> getOrders() {
		return Collections.unmodifiableList(_orders);
	}

	@Override
	public void setBounds(Long minBound, Long maxBound) {
		_minBound = minBound;
		_maxBound = maxBound;
	}

	@Override
	public void setDateHistogramInterval(String dateHistogramInterval) {
		_dateHistogramInterval = dateHistogramInterval;
	}

	@Override
	public void setInterval(Long interval) {
		_interval = interval;
	}

	@Override
	public void setKeyed(Boolean keyed) {
		_keyed = keyed;
	}

	@Override
	public void setMinDocCount(Long minDocCount) {
		_minDocCount = minDocCount;
	}

	@Override
	public void setOffset(Long offset) {
		_offset = offset;
	}

	private String _dateHistogramInterval;
	private Long _interval;
	private Boolean _keyed;
	private Long _maxBound;
	private Long _minBound;
	private Long _minDocCount;
	private Long _offset;
	private final List<Order> _orders = new ArrayList<>();

}