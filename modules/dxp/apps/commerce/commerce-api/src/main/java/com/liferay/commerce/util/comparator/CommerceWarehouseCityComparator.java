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

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceWarehouse;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseCityComparator
	extends OrderByComparator<CommerceWarehouse> {

	public static final String ORDER_BY_ASC = "CommerceWarehouse.city ASC";

	public static final String ORDER_BY_DESC = "CommerceWarehouse.city DESC";

	public static final String[] ORDER_BY_FIELDS = {"city"};

	public CommerceWarehouseCityComparator() {
		this(false);
	}

	public CommerceWarehouseCityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceWarehouse commerceWarehouse1,
		CommerceWarehouse commerceWarehouse2) {

		String city1 = StringUtil.toLowerCase(commerceWarehouse1.getCity());
		String city2 = StringUtil.toLowerCase(commerceWarehouse2.getCity());

		int value = city1.compareTo(city2);

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