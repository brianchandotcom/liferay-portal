/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util.comparator;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Crescenzo Rega
 */
public class CommerceShippingMethodPriorityComparator
	extends OrderByComparator<CommerceShippingMethod> {

	public static final String ORDER_BY_ASC =
		"CommerceShippingMethod.priority ASC";

	public static final String ORDER_BY_DESC =
		"CommerceShippingMethod.priority DESC";

	public static final String[] ORDER_BY_FIELDS = {"priority"};

	public CommerceShippingMethodPriorityComparator() {
		this(false);
	}

	public CommerceShippingMethodPriorityComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CommerceShippingMethod commerceShippingMethod1,
		CommerceShippingMethod commerceShippingMethod2) {

		int value = Double.compare(
			commerceShippingMethod1.getPriority(),
			commerceShippingMethod2.getPriority());

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