/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.bucket.Range;
import com.liferay.portal.search.aggregation.bucket.RangeAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class RangeAggregationImpl
	extends BaseFieldAggregation implements RangeAggregation {

	public RangeAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public void addRange(Range range) {
		_ranges.add(range);
	}

	@Override
	public void addRanges(Range... ranges) {
		Collections.addAll(_ranges, ranges);
	}

	@Override
	public void addUnboundedFrom(Double from) {
		addRange(new Range(from, null));
	}

	@Override
	public void addUnboundedFrom(String key, Double from) {
		addRange(new Range(key, from, null));
	}

	@Override
	public void addUnboundedTo(Double to) {
		addRange(new Range(null, to));
	}

	@Override
	public void addUnboundedTo(String key, Double to) {
		addRange(new Range(key, null, to));
	}

	@Override
	public String getFormat() {
		return _format;
	}

	@Override
	public Boolean getKeyed() {
		return _keyed;
	}

	@Override
	public List<Range> getRanges() {
		return Collections.unmodifiableList(_ranges);
	}

	@Override
	public void setFormat(String format) {
		_format = format;
	}

	@Override
	public void setKeyed(Boolean keyed) {
		_keyed = keyed;
	}

	private String _format;
	private Boolean _keyed;
	private final List<Range> _ranges = new ArrayList<>();

}