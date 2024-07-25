/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.style.book.model.StyleBookEntry;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookEntryCreateDateComparator
	extends OrderByComparator<StyleBookEntry> {

	public static final String ORDER_BY_ASC = "StyleBookEntry.createDate ASC";

	public static final String ORDER_BY_DESC = "StyleBookEntry.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public static StyleBookEntryCreateDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		StyleBookEntry styleBookEntry1, StyleBookEntry styleBookEntry2) {

		int value = DateUtil.compareTo(
			styleBookEntry1.getCreateDate(), styleBookEntry2.getCreateDate());

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

	private StyleBookEntryCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final StyleBookEntryCreateDateComparator
		_INSTANCE_ASCENDING = new StyleBookEntryCreateDateComparator(true);

	private static final StyleBookEntryCreateDateComparator
		_INSTANCE_DESCENDING = new StyleBookEntryCreateDateComparator(false);

	private final boolean _ascending;

}