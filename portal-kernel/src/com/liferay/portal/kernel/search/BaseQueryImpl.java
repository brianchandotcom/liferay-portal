/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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