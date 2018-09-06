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

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * Models a Sort param for sorting structured content by different fields and
 * sort directives.
 *
 * @author Cristina González
 * @review
 */
public class Sort {

	public static final Sort EMPTY_SORT = new Sort();

	/**
	 * Returns an empty Sort.
	 *
	 * @return - an empty Sort
	 * @review
	 */
	public static Sort emptySort() {
		return EMPTY_SORT;
	}

	/**
	 * Creates a new Sort from a list of sort fields.
	 *
	 * @param  sortFields - list of sort fields
	 * @review
	 */
	public Sort(List<SortField> sortFields) {
		if (ListUtil.isEmpty(sortFields)) {
			throw new InvalidSortException("Sort fields is empty");
		}

		_sortFields = Collections.unmodifiableList(sortFields);
	}

	/**
	 * Returns the list of sort fields.
	 *
	 * @return - the list of sort fields
	 * @review
	 */
	public List<SortField> getSortFields() {
		return _sortFields;
	}

	private Sort() {
		_sortFields = Collections.emptyList();
	}

	private final List<SortField> _sortFields;

}