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

	public Query getFilterQuery() {
		return _filterQuery;
	}

	public int getMaxChildren() {
		return _maxChildren;
	}

	public NestedSort getNestedSort() {
		return _nestedSort;
	}

	public String getPath() {
		return _path;
	}

	public void setFilterQuery(Query filterQuery) {
		_filterQuery = filterQuery;
	}

	public void setMaxChildren(int maxChildren) {
		_maxChildren = maxChildren;
	}

	public void setNestedSort(NestedSort nestedSort) {
		_nestedSort = nestedSort;
	}

	private Query _filterQuery;
	private int _maxChildren = Integer.MAX_VALUE;
	private NestedSort _nestedSort;
	private final String _path;

}