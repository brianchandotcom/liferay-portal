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
import com.liferay.portal.search.aggregation.bucket.BucketCountThresholds;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.SignificantTermsAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.significance.SignificanceHeuristic;

/**
 * @author Michael C. Han
 */
public class SignificantTermsAggregationImpl
	extends BaseFieldAggregation implements SignificantTermsAggregation {

	public SignificantTermsAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public Query getBackgroundFilterQuery() {
		return _backgroundFilterQuery;
	}

	@Override
	public BucketCountThresholds getBucketCountThresholds() {
		return _bucketCountThresholds;
	}

	@Override
	public String getExecutionHint() {
		return _executionHint;
	}

	@Override
	public IncludeExcludeClause getIncludeExcludeClause() {
		return _includeExcludeClause;
	}

	@Override
	public Long getMinDocCount() {
		return _minDocCount;
	}

	@Override
	public Long getShardMinDocCount() {
		return _shardMinDocCount;
	}

	@Override
	public Integer getShardSize() {
		return _shardSize;
	}

	@Override
	public SignificanceHeuristic getSignificanceHeuristic() {
		return _significanceHeuristic;
	}

	@Override
	public Integer getSize() {
		return _size;
	}

	@Override
	public void setBackgroundFilterQuery(Query backgroundFilterQuery) {
		_backgroundFilterQuery = backgroundFilterQuery;
	}

	@Override
	public void setBucketCountThresholds(
		BucketCountThresholds bucketCountThresholds) {

		_bucketCountThresholds = bucketCountThresholds;
	}

	@Override
	public void setExecutionHint(String executionHint) {
		_executionHint = executionHint;
	}

	@Override
	public void setIncludeExcludeClause(
		IncludeExcludeClause includeExcludeClause) {

		_includeExcludeClause = includeExcludeClause;
	}

	@Override
	public void setMinDocCount(Long minDocCount) {
		_minDocCount = minDocCount;
	}

	@Override
	public void setShardMinDocCount(Long shardMinDocCount) {
		_shardMinDocCount = shardMinDocCount;
	}

	@Override
	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	@Override
	public void setSignificanceHeuristic(
		SignificanceHeuristic significanceHeuristic) {

		_significanceHeuristic = significanceHeuristic;
	}

	@Override
	public void setSize(Integer size) {
		_size = size;
	}

	private Query _backgroundFilterQuery;
	private BucketCountThresholds _bucketCountThresholds;
	private String _executionHint;
	private IncludeExcludeClause _includeExcludeClause;
	private Long _minDocCount;
	private Long _shardMinDocCount;
	private Integer _shardSize;
	private SignificanceHeuristic _significanceHeuristic;
	private Integer _size;

}