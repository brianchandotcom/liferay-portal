/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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