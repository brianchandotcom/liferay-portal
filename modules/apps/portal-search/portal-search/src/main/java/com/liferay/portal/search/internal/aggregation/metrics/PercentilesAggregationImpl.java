/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.metrics;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.metrics.PercentilesAggregation;
import com.liferay.portal.search.aggregation.metrics.PercentilesMethod;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

/**
 * @author Michael C. Han
 */
public class PercentilesAggregationImpl
	extends BaseFieldAggregation implements PercentilesAggregation {

	public PercentilesAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public Integer getCompression() {
		return _compression;
	}

	@Override
	public Integer getHdrSignificantValueDigits() {
		return _hdrSignificantValueDigits;
	}

	@Override
	public Boolean getKeyed() {
		return _keyed;
	}

	@Override
	public PercentilesMethod getPercentilesMethod() {
		return _percentilesMethod;
	}

	@Override
	public double[] getPercents() {
		return _percents;
	}

	@Override
	public void setCompression(Integer compression) {
		_compression = compression;
	}

	@Override
	public void setHdrSignificantValueDigits(
		Integer hdrSignificantValueDigits) {

		_hdrSignificantValueDigits = hdrSignificantValueDigits;
	}

	@Override
	public void setKeyed(Boolean keyed) {
		_keyed = keyed;
	}

	@Override
	public void setPercentilesMethod(PercentilesMethod percentilesMethod) {
		_percentilesMethod = percentilesMethod;
	}

	@Override
	public void setPercents(double... percents) {
		_percents = percents;
	}

	private Integer _compression;
	private Integer _hdrSignificantValueDigits;
	private Boolean _keyed;
	private PercentilesMethod _percentilesMethod;
	private double[] _percents;

}