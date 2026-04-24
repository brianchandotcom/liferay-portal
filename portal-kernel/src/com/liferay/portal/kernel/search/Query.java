/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public abstract class Query implements Serializable {

	public static final float BOOST_DEFAULT = 1.0F;

	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return null;
	}

	public float getBoost() {
		return _boost;
	}

	public Filter getPostFilter() {
		return _postFilter;
	}

	public BooleanFilter getPreBooleanFilter() {
		return _preFilter;
	}

	public QueryConfig getQueryConfig() {
		if (_queryConfig == null) {
			_queryConfig = new QueryConfig();
		}

		return _queryConfig;
	}

	public boolean hasChildren() {
		return false;
	}

	public boolean isDefaultBoost() {
		if (_boost == BOOST_DEFAULT) {
			return true;
		}

		return false;
	}

	public void setBoost(float boost) {
		_boost = boost;
	}

	public void setPostFilter(Filter postFilter) {
		_postFilter = postFilter;
	}

	public void setPreBooleanFilter(BooleanFilter preFilter) {
		_preFilter = preFilter;
	}

	public void setQueryConfig(QueryConfig queryConfig) {
		_queryConfig = queryConfig;
	}

	private float _boost = BOOST_DEFAULT;
	private Filter _postFilter;
	private BooleanFilter _preFilter;
	private QueryConfig _queryConfig;

}