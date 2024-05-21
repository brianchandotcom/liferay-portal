/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Leonardo Barros
 */
public class DataProviderInstanceModifiedDateComparator
	extends OrderByComparator<DDMDataProviderInstance> {

	public DataProviderInstanceModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		DDMDataProviderInstance ddmDataProviderInstance1,
		DDMDataProviderInstance ddmDataProviderInstance2) {

		int value = DateUtil.compareTo(
			ddmDataProviderInstance1.getModifiedDate(),
			ddmDataProviderInstance2.getModifiedDate());

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

	private static final String _ORDER_BY_ASC =
		"DDMDataProviderInstance.modifiedDate ASC";

	private static final String _ORDER_BY_DESC =
		"DDMDataProviderInstance.modifiedDate DESC";

	private static final String[] _ORDER_BY_FIELDS = {"modifiedDate"};

	private final boolean _ascending;

}