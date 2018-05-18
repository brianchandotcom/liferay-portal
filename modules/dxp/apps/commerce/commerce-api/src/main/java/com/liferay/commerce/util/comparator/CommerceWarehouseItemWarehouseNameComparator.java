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
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseItemWarehouseNameComparator
	extends OrderByComparator<CommerceWarehouseItem> {

	public static final String ORDER_BY_ASC = "CommerceWarehouse.name ASC";

	public static final String ORDER_BY_DESC = "CommerceWarehouse.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"CommerceWarehouse.name"};

	public CommerceWarehouseItemWarehouseNameComparator() {
		this(false);
	}

	public CommerceWarehouseItemWarehouseNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceWarehouseItem commerceWarehouseItem1,
		CommerceWarehouseItem commerceWarehouseItem2) {

		try {
			CommerceWarehouse commerceWarehouse1 =
				commerceWarehouseItem1.getCommerceWarehouse();
			CommerceWarehouse commerceWarehouse2 =
				commerceWarehouseItem2.getCommerceWarehouse();

			String name1 = StringUtil.toLowerCase(commerceWarehouse1.getName());
			String name2 = StringUtil.toLowerCase(commerceWarehouse2.getName());

			int value = name1.compareTo(name2);

			if (_ascending) {
				return value;
			}
			else {
				return Math.negateExact(value);
			}
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
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