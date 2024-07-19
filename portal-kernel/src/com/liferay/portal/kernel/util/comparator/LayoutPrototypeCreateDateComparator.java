/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.LayoutPrototype;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class LayoutPrototypeCreateDateComparator
	extends OrderByComparator<LayoutPrototype> {

	public static final String ORDER_BY_ASC = "LayoutPrototype.createDate ASC";

	public static final String ORDER_BY_DESC =
		"LayoutPrototype.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static LayoutPrototypeCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		LayoutPrototype layoutPrototype1, LayoutPrototype layoutPrototype2) {

		int value = DateUtil.compareTo(
			layoutPrototype1.getCreateDate(), layoutPrototype2.getCreateDate());

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

	private LayoutPrototypeCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final LayoutPrototypeCreateDateComparator
		_INSTANCE_ASCENDING = new LayoutPrototypeCreateDateComparator(true);

	private static final LayoutPrototypeCreateDateComparator
		_INSTANCE_DESCENDING = new LayoutPrototypeCreateDateComparator(false);

	private final boolean _ascending;

}