/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.subscription.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.subscription.model.Subscription;

/**
 * @author Peter Shin
 */
public class SubscriptionClassNameIdComparator
	extends OrderByComparator<Subscription> {

	public static final String ORDER_BY_ASC = "Subscription.classNameId ASC";

	public static final String ORDER_BY_DESC = "Subscription.classNameId DESC";

	public static final String[] ORDER_BY_FIELDS = {"classNameId"};

	public static SubscriptionClassNameIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(Subscription subscription1, Subscription subscription2) {
		int value = 0;

		if (subscription1.getClassNameId() < subscription2.getClassNameId()) {
			value = -1;
		}

		if (subscription1.getClassNameId() > subscription2.getClassNameId()) {
			value = 1;
		}

		if (_ascending) {
			return value;
		}

		return -value;
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

	private SubscriptionClassNameIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final SubscriptionClassNameIdComparator _INSTANCE_ASCENDING =
		new SubscriptionClassNameIdComparator(true);

	private static final SubscriptionClassNameIdComparator
		_INSTANCE_DESCENDING = new SubscriptionClassNameIdComparator(false);

	private final boolean _ascending;

}