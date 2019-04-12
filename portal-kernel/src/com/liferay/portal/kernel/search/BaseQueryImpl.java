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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public abstract class BaseQueryImpl implements Query {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return null;
	}

	@Override
	public float getBoost() {
		return _boost;
	}

	@Override
	public Filter getPostFilter() {
		return _postFilter;
	}

	@Override
	public BooleanFilter getPreBooleanFilter() {
		return _preFilter;
	}

	@Override
	public QueryConfig getQueryConfig() {
		if (_queryConfig == null) {
			_queryConfig = new QueryConfig();
		}

		return _queryConfig;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	@Override
	public boolean isDefaultBoost() {
		if (_boost == BOOST_DEFAULT) {
			return true;
		}

		return false;
	}

	@Override
	public void setBoost(float boost) {
		_boost = boost;
	}

	@Override
	public void setPostFilter(Filter postFilter) {
		_postFilter = postFilter;
	}

	@Override
	public void setPreBooleanFilter(BooleanFilter preFilter) {
		_preFilter = preFilter;
	}

	@Override
	public void setQueryConfig(QueryConfig queryConfig) {
		_queryConfig = queryConfig;
	}

	private float _boost = BOOST_DEFAULT;
	private Filter _postFilter;
	private BooleanFilter _preFilter;
	private QueryConfig _queryConfig;

}