/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * Used to order record sets by name during search operations. The order can be
 * ascending or descending and is defined by the value specified in the class
 * constructor.
 *
 * @author Rafael Praxedes
 * @see    com.liferay.dynamic.data.lists.service.DDLRecordSetService#search(
 *         long, long, String, int, int, int, OrderByComparator)
 */
public class DDLRecordSetNameComparator
	extends OrderByComparator<DDLRecordSet> {

	public static final String ORDER_BY_ASC = "DDLRecordSet.name ASC";

	public static final String ORDER_BY_DESC = "DDLRecordSet.name DESC";

	public static final String[] ORDER_BY_FIELDS = {"name"};

	public static DDLRecordSetNameComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(DDLRecordSet ddlRecordSet1, DDLRecordSet ddlRecordSet2) {
		String name1 = StringUtil.toLowerCase(ddlRecordSet1.getName());
		String name2 = StringUtil.toLowerCase(ddlRecordSet2.getName());

		int value = name1.compareTo(name2);

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

	private DDLRecordSetNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DDLRecordSetNameComparator _INSTANCE_ASCENDING =
		new DDLRecordSetNameComparator(true);

	private static final DDLRecordSetNameComparator _INSTANCE_DESCENDING =
		new DDLRecordSetNameComparator(false);

	private final boolean _ascending;

}