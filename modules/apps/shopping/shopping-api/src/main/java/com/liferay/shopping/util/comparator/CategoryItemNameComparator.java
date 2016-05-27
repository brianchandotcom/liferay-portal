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

package com.liferay.shopping.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.shopping.model.ShoppingCategory;
import com.liferay.shopping.model.ShoppingItem;

/**
 * @author Eudaldo Alonso
 */
public class CategoryItemNameComparator extends OrderByComparator<Object> {

	public static final String ORDER_BY_ASC = "modelCategory DESC, name ASC";

	public static final String ORDER_BY_DESC = "modelCategory DESC, name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CategoryItemNameComparator() {
		this(false);
	}

	public CategoryItemNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Object object1, Object object2) {
		int value = 0;

		if ((object1 instanceof ShoppingCategory) &&
			(object2 instanceof ShoppingCategory)) {

			value = getName(object1).compareTo(getName(object2));
		}
		else if (object1 instanceof ShoppingCategory) {
			value = -1;
		}
		else if (object2 instanceof ShoppingCategory) {
			value = 1;
		}
		else {
			value = getName(object1).compareTo(getName(object2));
		}

		if (_ascending) {
			return value;
		}
		else {
			return -value;
		}
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return ORDER_BY_ASC;
		}
		else {
			return ORDER_BY_DESC;
		}
	}

	@Override
	public String[] getOrderByFields() {
		return ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	protected String getName(Object object) {
		if (object instanceof ShoppingItem) {
			ShoppingItem item = (ShoppingItem)object;

			return item.getName();
		}
		else {
			ShoppingCategory category = (ShoppingCategory)object;

			return category.getName();
		}
	}

	private final boolean _ascending;

}