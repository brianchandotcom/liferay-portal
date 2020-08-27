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

package com.liferay.commerce.inventory.util.comparator;

import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Luca Pellizzon
 */
public class CommerceReplenishmentAvailabilityDateComparator
	extends OrderByComparator<CommerceInventoryReplenishmentItem> {

	public static final String ORDER_BY_ASC = "availabilityDate ASC";

	public static final String ORDER_BY_DESC = "availabilityDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"availabilityDate"};

	public CommerceReplenishmentAvailabilityDateComparator() {
		this(true);
	}

	public CommerceReplenishmentAvailabilityDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem1,
		CommerceInventoryReplenishmentItem
			commerceInventoryReplenishmentItem2) {

		int value = DateUtil.compareTo(
			commerceInventoryReplenishmentItem1.getAvailabilityDate(),
			commerceInventoryReplenishmentItem2.getAvailabilityDate());

		if (_ascending) {
			return value;
		}

		return Math.negateExact(value);
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

	private final boolean _ascending;

}