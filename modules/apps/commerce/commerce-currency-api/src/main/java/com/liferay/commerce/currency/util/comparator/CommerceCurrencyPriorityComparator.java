/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.currency.util.comparator;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceCurrencyPriorityComparator
	extends OrderByComparator<CommerceCurrency> {

	public static final String ORDER_BY_ASC = "CommerceCurrency.priority ASC";

	public static final String ORDER_BY_DESC = "CommerceCurrency.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public static CommerceCurrencyPriorityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceCurrency commerceCurrency1,
		CommerceCurrency commerceCurrency2) {

		int value = Double.compare(
			commerceCurrency1.getPriority(), commerceCurrency2.getPriority());

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

	private CommerceCurrencyPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceCurrencyPriorityComparator
		_INSTANCE_ASCENDING = new CommerceCurrencyPriorityComparator(true);

	private static final CommerceCurrencyPriorityComparator
		_INSTANCE_DESCENDING = new CommerceCurrencyPriorityComparator(false);

	private final boolean _ascending;

}