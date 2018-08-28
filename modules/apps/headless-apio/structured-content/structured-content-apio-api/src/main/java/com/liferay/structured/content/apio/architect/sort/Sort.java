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

package com.liferay.structured.content.apio.architect.sort;

/**
 * Models a Sort param for sorting structured content by different fields and
 * sort directives.
 *
 * @author Cristina González
 * @review
 */
public class Sort {

	/**
	 * Creates a new Sort from a sort query.
	 *
	 * @param  sortQuery - a sort query
	 * @review
	 */
	public Sort(SortQuery sortQuery) {
		_sortQuery = sortQuery;
	}

	/**
	 * Returns the sort query.
	 *
	 * @return - a sort query
	 * @review
	 */
	public SortQuery getSortQuery() {
		return _sortQuery;
	}

	private final SortQuery _sortQuery;

}