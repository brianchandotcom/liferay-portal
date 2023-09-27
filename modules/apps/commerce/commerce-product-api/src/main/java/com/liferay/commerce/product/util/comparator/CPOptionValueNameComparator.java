/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPOptionValueNameComparator
	extends OrderByComparator<CPOptionValue> {

	public static final String ORDER_BY_ASC = "CPOptionValue.name ASC";

	public static final String ORDER_BY_DESC = "CPOptionValue.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public CPOptionValueNameComparator() {
		this(false);
	}

	public CPOptionValueNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		CPOptionValue cpOptionValue1, CPOptionValue cpOptionValue2) {

		String name1 = StringUtil.toLowerCase(cpOptionValue1.getName());
		String name2 = StringUtil.toLowerCase(cpOptionValue2.getName());

		int value = name1.compareTo(name2);

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