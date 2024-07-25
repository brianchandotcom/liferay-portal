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
public class LayoutRelevanceComparator extends OrderByComparator<Layout> {

	public static final String ORDER_BY_ASC = "Layout.score ASC";

	public static final String ORDER_BY_DESC = "Layout.score DESC";

	public static final String[] ORDER_BY_FIELDS = {"score"};

	public static LayoutRelevanceComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
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

	private LayoutRelevanceComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final LayoutRelevanceComparator _INSTANCE_ASCENDING =
		new LayoutRelevanceComparator(true);

	private static final LayoutRelevanceComparator _INSTANCE_DESCENDING =
		new LayoutRelevanceComparator(false);

	private final boolean _ascending;

}