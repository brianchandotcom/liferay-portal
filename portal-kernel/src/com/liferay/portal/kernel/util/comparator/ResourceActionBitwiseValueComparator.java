/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class ResourceActionBitwiseValueComparator
	extends OrderByComparator<ResourceAction> {

	public static final String ORDER_BY_ASC = "bitwiseValue ASC";

	public static final String ORDER_BY_DESC = "bitwiseValue DESC";

	public static final String[] ORDER_BY_FIELDS = {"bitwiseValue"};

	public static ResourceActionBitwiseValueComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		ResourceAction resourceAction1, ResourceAction resourceAction2) {

		int value =
			(int)(resourceAction1.getBitwiseValue() -
				resourceAction2.getBitwiseValue());

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

	private ResourceActionBitwiseValueComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final ResourceActionBitwiseValueComparator
		_INSTANCE_ASCENDING = new ResourceActionBitwiseValueComparator(true);

	private static final ResourceActionBitwiseValueComparator
		_INSTANCE_DESCENDING = new ResourceActionBitwiseValueComparator(false);

	private final boolean _ascending;

}