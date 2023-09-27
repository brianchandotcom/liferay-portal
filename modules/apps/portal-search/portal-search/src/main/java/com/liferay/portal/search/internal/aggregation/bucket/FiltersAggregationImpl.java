/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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