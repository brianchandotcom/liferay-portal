/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceShipment;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentIdComparator
	extends OrderByComparator<CommerceShipment> {

	public static final String ORDER_BY_ASC =
		"CommerceShipment.commerceShipmentId ASC";

	public static final String ORDER_BY_DESC =
		"CommerceShipment.commerceShipmentId DESC";

	public static final String[] ORDER_BY_FIELDS = {"commerceShipmentId"};

	public CommerceShipmentIdComparator() {
		this(false);
	}

	public CommerceShipmentIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceShipment commerceShipment1,
		CommerceShipment commerceShipment2) {

		int value = Long.compare(
			commerceShipment1.getCommerceShipmentId(),
			commerceShipment2.getCommerceShipmentId());

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