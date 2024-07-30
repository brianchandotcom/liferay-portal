/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.web.internal.util.comparator;

import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsEntry;

/**
 * @author Eduardo García
 */
public class SegmentsEntryModifiedDateComparator
	extends OrderByComparator<SegmentsEntry> {

	public static final String ORDER_BY_ASC = "SegmentsEntry.modifiedDate ASC";

	public static final String ORDER_BY_DESC =
		"SegmentsEntry.modifiedDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"modifiedDate"};

	public static SegmentsEntryModifiedDateComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(SegmentsEntry page1, SegmentsEntry page2) {
		int value = DateUtil.compareTo(
			page1.getModifiedDate(), page2.getModifiedDate());

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

	private SegmentsEntryModifiedDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final SegmentsEntryModifiedDateComparator
		_INSTANCE_ASCENDING = new SegmentsEntryModifiedDateComparator(true);

	private static final SegmentsEntryModifiedDateComparator
		_INSTANCE_DESCENDING = new SegmentsEntryModifiedDateComparator(false);

	private final boolean _ascending;

}