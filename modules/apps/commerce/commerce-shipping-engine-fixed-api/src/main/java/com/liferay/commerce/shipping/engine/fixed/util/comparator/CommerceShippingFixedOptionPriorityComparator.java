/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shipping.engine.fixed.util.comparator;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionPriorityComparator
	extends OrderByComparator<CommerceShippingFixedOption> {

	public static final String ORDER_BY_ASC =
		"CommerceShippingFixedOption.priority ASC";

	public static final String ORDER_BY_DESC =
		"CommerceShippingFixedOption.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public static CommerceShippingFixedOptionPriorityComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CommerceShippingFixedOption commerceShippingFixedOption1,
		CommerceShippingFixedOption commerceShippingFixedOption2) {

		int value = Double.compare(
			commerceShippingFixedOption1.getPriority(),
			commerceShippingFixedOption2.getPriority());

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

	private CommerceShippingFixedOptionPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CommerceShippingFixedOptionPriorityComparator
		_INSTANCE_ASCENDING = new CommerceShippingFixedOptionPriorityComparator(
			true);

	private static final CommerceShippingFixedOptionPriorityComparator
		_INSTANCE_DESCENDING =
			new CommerceShippingFixedOptionPriorityComparator(false);

	private final boolean _ascending;

}