/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentShippingDateComparator
	extends OrderByComparator<CommerceShipment> {

	public static final String ORDER_BY_ASC = "shippingDate ASC";

	public static final String ORDER_BY_DESC = "shippingDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"shippingDate"};

	public CommerceShipmentShippingDateComparator() {
		this(false);
	}

	public CommerceShipmentShippingDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceShipment commerceShipment1,
		CommerceShipment commerceShipment2) {

		int value = DateUtil.compareTo(
			commerceShipment1.getShippingDate(),
			commerceShipment2.getShippingDate());

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