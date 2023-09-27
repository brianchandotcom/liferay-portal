/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.aggregation.bucket;

import com.liferay.portal.search.aggregation.AggregationVisitor;
import com.liferay.portal.search.aggregation.bucket.GeoHashGridAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

/**
 * @author Michael C. Han
 */
public class GeoHashGridAggregationImpl
	extends BaseFieldAggregation implements GeoHashGridAggregation {

	public GeoHashGridAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public Integer getPrecision() {
		return _precision;
	}

	@Override
	public Integer getShardSize() {
		return _shardSize;
	}

	@Override
	public Integer getSize() {
		return _size;
	}

	@Override
	public void setPrecision(Integer precision) {
		_precision = precision;
	}

	@Override
	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	@Override
	public void setSize(Integer size) {
		_size = size;
	}

	private Integer _precision;
	private Integer _shardSize;
	private Integer _size;

}