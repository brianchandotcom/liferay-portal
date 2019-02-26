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
import com.liferay.portal.search.aggregation.bucket.DiversifiedSamplerAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

/**
 * @author Michael C. Han
 */
public class DiversifiedSamplerAggregationImpl
	extends BaseFieldAggregation implements DiversifiedSamplerAggregation {

	public DiversifiedSamplerAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public String getExecutionHint() {
		return _executionHint;
	}

	@Override
	public Integer getMaxDocsPerValue() {
		return _maxDocsPerValue;
	}

	@Override
	public Integer getShardSize() {
		return _shardSize;
	}

	@Override
	public void setExecutionHint(String executionHint) {
		_executionHint = executionHint;
	}

	@Override
	public void setMaxDocsPerValue(Integer maxDocsPerValue) {
		_maxDocsPerValue = maxDocsPerValue;
	}

	@Override
	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	private String _executionHint;
	private Integer _maxDocsPerValue;
	private Integer _shardSize;

}