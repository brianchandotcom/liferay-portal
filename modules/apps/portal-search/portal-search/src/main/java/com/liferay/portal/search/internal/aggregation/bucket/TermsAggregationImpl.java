/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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