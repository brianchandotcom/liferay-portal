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

import com.liferay.portal.search.sort.Sort;
import com.liferay.portal.search.sort.SortOrder;

/**
 * @author Michael C. Han
 */
public abstract class SortImpl implements Sort {

	@Override
	public SortOrder getSortOrder() {
		if (_sortOrder != null) {
			return _sortOrder;
		}

		return SortOrder.ASC;
	}

	@Override
	public void setSortOrder(SortOrder sortOrder) {
		_sortOrder = sortOrder;
	}

	private SortOrder _sortOrder;

}