/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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