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
import com.liferay.portal.search.aggregation.bucket.SamplerAggregation;
import com.liferay.portal.search.internal.aggregation.BaseAggregation;

/**
 * @author Michael C. Han
 */
public class SamplerAggregationImpl
	extends BaseAggregation implements SamplerAggregation {

	public SamplerAggregationImpl(String name) {
		super(name);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public Integer getShardSize() {
		return _shardSize;
	}

	@Override
	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	private Integer _shardSize;

}