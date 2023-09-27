/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.util.comparator;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luca Pellizzon
 */
public class CommerceInventoryWarehouseCityComparator
	extends OrderByComparator<CommerceInventoryWarehouse> {

	public static final String ORDER_BY_ASC = "CIWarehouse.city ASC";

	public static final String ORDER_BY_DESC = "CIWarehouse.city DESC";

	public static final String[] ORDER_BY_FIELDS = {"city"};

	public CommerceInventoryWarehouseCityComparator() {
		this(false);
	}

	public CommerceInventoryWarehouseCityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceInventoryWarehouse commerceInventoryWarehouse1,
		CommerceInventoryWarehouse commerceInventoryWarehouse2) {

		String city1 = StringUtil.toLowerCase(
			commerceInventoryWarehouse1.getCity());
		String city2 = StringUtil.toLowerCase(
			commerceInventoryWarehouse2.getCity());

		int value = city1.compareTo(city2);

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