/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.style.book.model.StyleBookEntry;

/**
 * @author Eudaldo Alonso
 */
public class StyleBookEntryNameComparator
	extends OrderByComparator<StyleBookEntry> {

	public static StyleBookEntryNameComparator get(boolean ascending) {
		if (ascending) {
			return _ASCENDING;
		}

		return _DESCENDING;
	}

	@Override
	public int compare(
		StyleBookEntry styleBookEntry1, StyleBookEntry styleBookEntry2) {

		String name1 = StringUtil.toLowerCase(styleBookEntry1.getName());
		String name2 = StringUtil.toLowerCase(styleBookEntry2.getName());

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

	private StyleBookEntryNameComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final StyleBookEntryNameComparator _ASCENDING =
		new StyleBookEntryNameComparator(true);

	private static final StyleBookEntryNameComparator _DESCENDING =
		new StyleBookEntryNameComparator(false);

	private static final String _ORDER_BY_ASC = "StyleBookEntry.name ASC";

	private static final String _ORDER_BY_DESC = "StyleBookEntry.name DESC";

	private static final String[] _ORDER_BY_FIELDS = {"name"};

	private final boolean _ascending;

}