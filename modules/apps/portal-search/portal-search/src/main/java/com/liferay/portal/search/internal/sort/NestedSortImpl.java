/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.sort;

import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.sort.NestedSort;

/**
 * @author Michael C. Han
 */
public class NestedSortImpl implements NestedSort {

	public NestedSortImpl(String path) {
		_path = path;
	}

	@Override
	public Query getFilterQuery() {
		return _filterQuery;
	}

	@Override
	public int getMaxChildren() {
		return _maxChildren;
	}

	@Override
	public NestedSort getNestedSort() {
		return _nestedSort;
	}

	@Override
	public String getPath() {
		return _path;
	}

	@Override
	public void setFilterQuery(Query filterQuery) {
		_filterQuery = filterQuery;
	}

	@Override
	public void setMaxChildren(int maxChildren) {
		_maxChildren = maxChildren;
	}

	@Override
	public void setNestedSort(NestedSort nestedSort) {
		_nestedSort = nestedSort;
	}

	private Query _filterQuery;
	private int _maxChildren = Integer.MAX_VALUE;
	private NestedSort _nestedSort;
	private final String _path;

}