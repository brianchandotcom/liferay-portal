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

package com.liferay.commerce.price.list.util.comparator;

import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Marco Leo
 */
public class CommerceTierPriceEntryMinQuantityComparator
	extends OrderByComparator<CommerceTierPriceEntry> {

	public static final String ORDER_BY_ASC = "minQuantity ASC";

	public static final String ORDER_BY_DESC = "minQuantity DESC";

	public static final String[] ORDER_BY_FIELDS = {"minQuantity"};

	public CommerceTierPriceEntryMinQuantityComparator() {
		this(false);
	}

	public CommerceTierPriceEntryMinQuantityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceTierPriceEntry commerceTierPriceEntry1,
		CommerceTierPriceEntry commerceTierPriceEntry2) {

		int value = Integer.compare(
			commerceTierPriceEntry1.getMinQuantity(),
			commerceTierPriceEntry2.getMinQuantity());

		if (_ascending) {
			return value;
		}
		else {
			return Math.negateExact(value);
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