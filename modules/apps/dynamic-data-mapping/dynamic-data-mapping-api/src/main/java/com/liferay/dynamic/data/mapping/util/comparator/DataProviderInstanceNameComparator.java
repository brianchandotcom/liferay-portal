/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Leonardo Barros
 */
public class DataProviderInstanceNameComparator
	extends OrderByComparator<DDMDataProviderInstance> {

	public static DataProviderInstanceNameComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _ASCENDING;
		}

		return _DESCENDING;
	}

	@Override
	public int compare(
		DDMDataProviderInstance ddmDataProviderInstance1,
		DDMDataProviderInstance ddmDataProviderInstance2) {

		String name1 = StringUtil.toLowerCase(
			ddmDataProviderInstance1.getName());
		String name2 = StringUtil.toLowerCase(
			ddmDataProviderInstance2.getName());

		int value = name1.compareTo(name2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	@Override
	public String getOrderBy() {
		if (_ascending) {
			return _ORDER_BY_ASC;
		}

		return _ORDER_BY_DESC;
	}

	@Override
	public String[] getOrderByFields() {
		return _ORDER_BY_FIELDS;
	}

	@Override
	public boolean isAscending() {
		return _ascending;
	}

	private DataProviderInstanceNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DataProviderInstanceNameComparator _ASCENDING =
		new DataProviderInstanceNameComparator(true);

	private static final DataProviderInstanceNameComparator _DESCENDING =
		new DataProviderInstanceNameComparator(false);

	private static final String _ORDER_BY_ASC =
		"DDMDataProviderInstance.name ASC";

	private static final String _ORDER_BY_DESC =
		"DDMDataProviderInstance.name DESC";

	private static final String[] _ORDER_BY_FIELDS = {"name"};

	private final boolean _ascending;

}