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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.shopping.model.ShoppingItem;

/**
 * @author Brian Wing Shun Chan
 */
public class ItemPriceComparator extends OrderByComparator<ShoppingItem> {

	public static final String ORDER_BY_ASC =
		"ShoppingItem.categoryId ASC, ShoppingItem.price ASC, " +
			"ShoppingItem.name ASC";

	public static final String ORDER_BY_DESC =
		"ShoppingItem.categoryId DESC, ShoppingItem.price DESC, " +
			"ShoppingItem.name DESC";

	public static final String[] ORDER_BY_FIELDS =
		{"categoryId", "price", "name"};

	public ItemPriceComparator() {
		this(false);
	}

	public ItemPriceComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(ShoppingItem item1, ShoppingItem item2) {
		Long categoryId1 = Long.valueOf(item1.getCategoryId());
		Long categoryId2 = Long.valueOf(item2.getCategoryId());

		int value = categoryId1.compareTo(categoryId2);

		if (value == 0) {
			double price1 = (1 - item1.getDiscount()) * item1.getPrice();
			double price2 = (1 - item2.getDiscount()) * item2.getPrice();

			if (price1 < price2) {
				value = -1;
			}
			else if (price1 > price2) {
				value = 1;
			}
		}

		if (value == 0) {
			String name1 = StringUtil.toLowerCase(item1.getName());
			String name2 = StringUtil.toLowerCase(item2.getName());

			value = name1.compareTo(name2);
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

	private final boolean _ascending;

}