/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.util.comparator;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionOptionRelCreateDateComparator
	extends OrderByComparator<CPDefinitionOptionRel> {

	public static final String ORDER_BY_ASC = "createDate ASC";

	public static final String ORDER_BY_DESC = "createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static CPDefinitionOptionRelCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		CPDefinitionOptionRel cpDefinitionOptionRel1,
		CPDefinitionOptionRel cpDefinitionOptionRel2) {

		int value = DateUtil.compareTo(
			cpDefinitionOptionRel1.getCreateDate(),
			cpDefinitionOptionRel2.getCreateDate());

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

	private CPDefinitionOptionRelCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final CPDefinitionOptionRelCreateDateComparator
		_INSTANCE_ASCENDING = new CPDefinitionOptionRelCreateDateComparator(
			true);

	private static final CPDefinitionOptionRelCreateDateComparator
		_INSTANCE_DESCENDING = new CPDefinitionOptionRelCreateDateComparator(
			false);

	private final boolean _ascending;

}