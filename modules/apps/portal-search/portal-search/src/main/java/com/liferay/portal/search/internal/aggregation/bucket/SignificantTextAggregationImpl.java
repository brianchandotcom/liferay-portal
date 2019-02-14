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
import com.liferay.portal.search.aggregation.bucket.SignificantTextAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.significance.SignificanceHeuristic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class SignificantTextAggregationImpl
	extends BaseFieldAggregation implements SignificantTextAggregation {

	public SignificantTextAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	public void addSourceFields(String... fields) {
		Collections.addAll(_sourceFields, fields);
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
	public Boolean getFilterDuplicateText() {
		return _filterDuplicateText;
	}

	@Override
	public IncludeExcludeClauseImpl getIncludeExcludeClause() {
		return _includeExcludeClauseImpl;
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
	public List<String> getSourceFields() {
		return Collections.unmodifiableList(_sourceFields);
	}

	public void setBackgroundFilterQuery(Query backgroundFilterQuery) {
		_backgroundFilterQuery = backgroundFilterQuery;
	}

	public void setBucketCountThresholds(
		BucketCountThresholds bucketCountThresholds) {

		_bucketCountThresholds = bucketCountThresholds;
	}

	public void setExecutionHint(String executionHint) {
		_executionHint = executionHint;
	}

	public void setFilterDuplicateText(Boolean filterDuplicateText) {
		_filterDuplicateText = filterDuplicateText;
	}

	public void setIncludeExcludeClauseImpl(
		IncludeExcludeClauseImpl includeExcludeClauseImpl) {

		_includeExcludeClauseImpl = includeExcludeClauseImpl;
	}

	public void setMinDocCount(Long minDocCount) {
		_minDocCount = minDocCount;
	}

	public void setShardMinDocCount(Long shardMinDocCount) {
		_shardMinDocCount = shardMinDocCount;
	}

	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	public void setSignificanceHeuristic(
		SignificanceHeuristic significanceHeuristic) {

		_significanceHeuristic = significanceHeuristic;
	}

	public void setSize(Integer size) {
		_size = size;
	}

	private Query _backgroundFilterQuery;
	private BucketCountThresholds _bucketCountThresholds;
	private String _executionHint;
	private Boolean _filterDuplicateText;
	private IncludeExcludeClauseImpl _includeExcludeClauseImpl;
	private Long _minDocCount;
	private Long _shardMinDocCount;
	private Integer _shardSize;
	private SignificanceHeuristic _significanceHeuristic;
	private Integer _size;
	private final List<String> _sourceFields = new ArrayList<>();

}