/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.comparator;

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Pavel Savinov
 */
public class LayoutCreateDateComparator extends OrderByComparator<Layout> {

	public static final String ORDER_BY_ASC = "Layout.createDate ASC";

	public static final String ORDER_BY_DESC = "Layout.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public LayoutCreateDateComparator() {
		this(true);
	}

	public LayoutCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(Layout layout1, Layout layout2) {
		int value = DateUtil.compareTo(
			layout1.getCreateDate(), layout2.getCreateDate());

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