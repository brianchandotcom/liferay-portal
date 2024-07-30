/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saved.content.util.comparator;

import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.saved.content.model.SavedContentEntry;

/**
 * @author Alicia García
 */
public class SavedContentEntryClassNameIdComparator
	extends OrderByComparator<SavedContentEntry> {

	public static final String ORDER_BY_ASC =
		"SavedContentEntry.classNameId ASC";

	public static final String ORDER_BY_DESC =
		"SavedContentEntry.classNameId DESC";

	public static final String[] ORDER_BY_FIELDS = {"classNameId"};

	public static SavedContentEntryClassNameIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		SavedContentEntry savedContentEntry1,
		SavedContentEntry savedContentEntry2) {

		int value = 0;

		if (savedContentEntry1.getClassNameId() <
				savedContentEntry2.getClassNameId()) {

			value = -1;
		}

		if (savedContentEntry1.getClassNameId() >
				savedContentEntry2.getClassNameId()) {

			value = 1;
		}

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

	private SavedContentEntryClassNameIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final SavedContentEntryClassNameIdComparator
		_INSTANCE_ASCENDING = new SavedContentEntryClassNameIdComparator(true);

	private static final SavedContentEntryClassNameIdComparator
		_INSTANCE_DESCENDING = new SavedContentEntryClassNameIdComparator(
			false);

	private final boolean _ascending;

}