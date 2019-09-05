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
import com.liferay.portal.search.aggregation.bucket.CollectionMode;
import com.liferay.portal.search.aggregation.bucket.IncludeExcludeClause;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class TermsAggregationImpl
	extends BaseFieldAggregation implements TermsAggregation {

	public TermsAggregationImpl(String name, String field) {
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
	public CollectionMode getCollectionMode() {
		return _collectionMode;
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
	public Integer getMinDocCount() {
		return _minDocCount;
	}

	@Override
	public List<Order> getOrders() {
		return Collections.unmodifiableList(_orders);
	}

	@Override
	public Integer getShardMinDocCount() {
		return _shardMinDocCount;
	}

	@Override
	public Integer getShardSize() {
		return _shardSize;
	}

	@Override
	public Boolean getShowTermDocCountError() {
		return _showTermDocCountError;
	}

	@Override
	public Integer getSize() {
		return _size;
	}

	@Override
	public void setCollectionMode(CollectionMode collectionMode) {
		_collectionMode = collectionMode;
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
	public void setMinDocCount(Integer minDocCount) {
		_minDocCount = minDocCount;
	}

	@Override
	public void setShardMinDocCount(Integer shardMinDocCount) {
		_shardMinDocCount = shardMinDocCount;
	}

	@Override
	public void setShardSize(Integer shardSize) {
		_shardSize = shardSize;
	}

	@Override
	public void setShowTermDocCountError(Boolean showTermDocCountError) {
		_showTermDocCountError = showTermDocCountError;
	}

	@Override
	public void setSize(Integer size) {
		_size = size;
	}

	private CollectionMode _collectionMode;
	private String _executionHint;
	private IncludeExcludeClause _includeExcludeClause;
	private Integer _minDocCount;
	private final List<Order> _orders = new ArrayList<>();
	private Integer _shardMinDocCount;
	private Integer _shardSize;
	private Boolean _showTermDocCountError;
	private Integer _size;

}