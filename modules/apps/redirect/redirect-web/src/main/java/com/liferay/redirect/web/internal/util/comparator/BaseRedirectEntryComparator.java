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

package com.liferay.redirect.web.internal.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.redirect.model.RedirectEntry;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseRedirectEntryComparator<T extends Comparable<T>>
	extends OrderByComparator<RedirectEntry> {

	public BaseRedirectEntryComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		RedirectEntry redirectEntry1, RedirectEntry redirectEntry2) {

		T fieldValue1 = getFieldValue(redirectEntry1);
		T fieldValue2 = getFieldValue(redirectEntry1);

		int value = fieldValue1.compareTo(fieldValue2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return "RedirectEntry." + getFieldName() + " ASC";
		}

		return "RedirectEntry." + getFieldName() + " DESC";
	}

	@Override
	public String[] getOrderByFields() {
		return new String[] {getFieldName()};
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected abstract String getFieldName();

	protected abstract T getFieldValue(RedirectEntry redirectEntry);

	private final boolean _ascending;

}