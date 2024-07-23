/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceSkuComparator extends OrderByComparator<CPInstance> {

	public static final String ORDER_BY_ASC = "CPInstance.sku ASC";

	public static final String ORDER_BY_DESC = "CPInstance.sku DESC";

	public static final String[] ORDER_BY_FIELDS = {"sku"};

	public static CPInstanceSkuComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(CPInstance cpInstance1, CPInstance cpInstance2) {
		String sku1 = StringUtil.toLowerCase(cpInstance1.getSku());
		String sku2 = StringUtil.toLowerCase(cpInstance2.getSku());

		int value = sku1.compareTo(sku2);

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

	private CPInstanceSkuComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CPInstanceSkuComparator _INSTANCE_ASCENDING =
		new CPInstanceSkuComparator(true);

	private static final CPInstanceSkuComparator _INSTANCE_DESCENDING =
		new CPInstanceSkuComparator(false);

	private final boolean _ascending;

}