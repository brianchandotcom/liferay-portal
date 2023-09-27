/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.util.comparator;

import com.liferay.data.engine.model.DEDataListView;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Gabriel Albuquerque
 */
public class DEDataListViewCreateDateComparator
	extends OrderByComparator<DEDataListView> {

	public static final String ORDER_BY_ASC = "DEDataListView.createDate ASC";

	public static final String ORDER_BY_DESC = "DEDataListView.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public DEDataListViewCreateDateComparator() {
		this(false);
	}

	public DEDataListViewCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		DEDataListView deDataListView1, DEDataListView deDataListView2) {

		int value = DateUtil.compareTo(
			deDataListView1.getCreateDate(), deDataListView2.getCreateDate());

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