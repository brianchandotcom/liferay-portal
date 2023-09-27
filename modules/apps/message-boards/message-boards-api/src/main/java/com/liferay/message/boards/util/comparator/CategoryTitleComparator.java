/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	protected String getCategoryName(Object object) {
		if (object instanceof MBCategory) {
			MBCategory mbCategory = (MBCategory)object;

			return mbCategory.getName();
		}

		return null;
	}

	private final boolean _ascending;

}