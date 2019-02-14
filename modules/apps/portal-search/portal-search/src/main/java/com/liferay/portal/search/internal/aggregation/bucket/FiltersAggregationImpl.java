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
import com.liferay.portal.search.aggregation.bucket.FiltersAggregation;
import com.liferay.portal.search.internal.aggregation.BaseFieldAggregation;
import com.liferay.portal.search.query.Query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Michael C. Han
 */
public class FiltersAggregationImpl
	extends BaseFieldAggregation implements FiltersAggregation {

	public FiltersAggregationImpl(String name, String field) {
		super(name, field);
	}

	@Override
	public <T> T accept(AggregationVisitor<T> aggregationVisitor) {
		return aggregationVisitor.visit(this);
	}

	@Override
	public void addKeyedQuery(String key, Query query) {
		_keyedQueries.add(new KeyedQueryImpl(key, query));
	}

	@Override
	public List<KeyedQuery> getKeyedQueries() {
		return Collections.unmodifiableList(_keyedQueries);
	}

	@Override
	public Boolean getOtherBucket() {
		return _otherBucket;
	}

	@Override
	public String getOtherBucketKey() {
		return _otherBucketKey;
	}

	@Override
	public void setOtherBucket(Boolean otherBucket) {
		_otherBucket = otherBucket;
	}

	@Override
	public void setOtherBucketKey(String otherBucketKey) {
		_otherBucketKey = otherBucketKey;
	}

	public static class KeyedQueryImpl implements KeyedQuery {

		public KeyedQueryImpl(String key, Query query) {
			_key = key;
			_query = query;
		}

		@Override
		public String getKey() {
			return _key;
		}

		@Override
		public Query getQuery() {
			return _query;
		}

		private final String _key;
		private final Query _query;

	}

	private final List<KeyedQuery> _keyedQueries = new ArrayList<>();
	private Boolean _otherBucket;
	private String _otherBucketKey;

}