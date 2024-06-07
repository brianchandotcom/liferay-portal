/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.util.comparator;

import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

/**
 * @author Marco Leo
 */
public class CommerceTierPriceEntryMinQuantityComparator
	extends OrderByComparator<CommerceTierPriceEntry> {

	public static final String ORDER_BY_ASC = "minQuantity ASC";

	public static final String ORDER_BY_DESC = "minQuantity DESC";

	public static final String[] ORDER_BY_FIELDS = {"minQuantity"};

	public static CommerceTierPriceEntryMinQuantityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceTierPriceEntry commerceTierPriceEntry1,
		CommerceTierPriceEntry commerceTierPriceEntry2) {

		BigDecimal minQuantity = commerceTierPriceEntry1.getMinQuantity();

		int value = minQuantity.compareTo(
			commerceTierPriceEntry2.getMinQuantity());

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

	private CommerceTierPriceEntryMinQuantityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceTierPriceEntryMinQuantityComparator
		_INSTANCE_ASCENDING = new CommerceTierPriceEntryMinQuantityComparator(
			true);

	private static final CommerceTierPriceEntryMinQuantityComparator
		_INSTANCE_DESCENDING = new CommerceTierPriceEntryMinQuantityComparator(
			false);

	private final boolean _ascending;

}