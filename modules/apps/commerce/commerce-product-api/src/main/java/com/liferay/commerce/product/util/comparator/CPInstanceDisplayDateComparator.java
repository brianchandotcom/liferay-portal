/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceDisplayDateComparator
	extends OrderByComparator<CPInstance> {

	public static final String ORDER_BY_ASC = "displayDate ASC";

	public static final String ORDER_BY_DESC = "displayDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"displayDate"};

	public CPInstanceDisplayDateComparator() {
		this(false);
	}

	public CPInstanceDisplayDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(CPInstance cpInstance1, CPInstance cpInstance2) {
		int value = DateUtil.compareTo(
			cpInstance1.getDisplayDate(), cpInstance2.getDisplayDate());

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