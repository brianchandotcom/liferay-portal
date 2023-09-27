/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util.comparator;

import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Eudaldo Alonso
 */
public class LayoutSetPrototypeCreateDateComparator
	extends OrderByComparator<LayoutSetPrototype> {

	public static final String ORDER_BY_ASC =
		"LayoutSetPrototype.createDate ASC";

	public static final String ORDER_BY_DESC =
		"LayoutSetPrototype.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public LayoutSetPrototypeCreateDateComparator() {
		this(false);
	}

	public LayoutSetPrototypeCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		LayoutSetPrototype layoutSetPrototype1,
		LayoutSetPrototype layoutSetPrototype2) {

		int value = DateUtil.compareTo(
			layoutSetPrototype1.getCreateDate(),
			layoutSetPrototype2.getCreateDate());

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

	private final boolean _ascending;

}