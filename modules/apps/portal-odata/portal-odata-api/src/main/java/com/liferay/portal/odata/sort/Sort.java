/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.sort;

import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collections;
import java.util.List;

/**
 * Models a sort parameter for sorting structured content by different fields
 * and sort directives.
 *
 * @author Cristina González
 * @review
 */
public class Sort {

	public static final Sort EMPTY_SORT = new Sort();

	/**
	 * Returns an empty sort.
	 *
	 * @return the empty sort
	 * @review
	 */
	public static Sort emptySort() {
		return EMPTY_SORT;
	}

	/**
	 * Creates a new sort from the sort fields.
	 *
	 * @param  sortFields the sort fields
	 * @review
	 */
	public Sort(List<SortField> sortFields) {
		if (ListUtil.isEmpty(sortFields)) {
			throw new InvalidSortException("Sort fields is empty");
		}

		_sortFields = Collections.unmodifiableList(sortFields);
	}

	/**
	 * Returns the sort fields.
	 *
	 * @return the sort fields
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