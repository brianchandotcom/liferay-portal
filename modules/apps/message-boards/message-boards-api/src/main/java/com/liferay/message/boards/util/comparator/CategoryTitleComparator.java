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

package com.liferay.message.boards.util.comparator;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author David Zhang
 */
public class CategoryTitleComparator<T> extends OrderByComparator<T> {

	public static final String ORDER_BY_ASC = "name ASC, modifiedDate DESC";

	public static final String ORDER_BY_DESC = "name DESC, modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"name", "modifiedDate"};

	public CategoryTitleComparator() {
		this(false);
	}

	public CategoryTitleComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(T t1, T t2) {
		String name1 = StringUtil.toLowerCase(getCategoryName(t1));
		String name2 = StringUtil.toLowerCase(getCategoryName(t2));

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}

		return ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected String getCategoryName(Object obj) {
		if (obj instanceof MBCategory) {
			MBCategory mbCategory = (MBCategory)obj;

			return mbCategory.getName();
		}

		return null;
	}

	private final boolean _ascending;

}