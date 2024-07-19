/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * Used to order records according their IDs during listing operations. The
 * order can be ascending or descending and is defined by the value specified in
 * the class constructor.
 *
 * @author Leonardo Barros
 * @see    com.liferay.dynamic.data.lists.service.DDLRecordLocalService#getRecords(
 *         long, int, int, int, OrderByComparator)
 */
public class DDLRecordIdComparator extends OrderByComparator<DDLRecord> {

	public static final String ORDER_BY_ASC = "DDLRecord.recordId ASC";

	public static final String ORDER_BY_DESC = "DDLRecord.recordId DESC";

	public static final String[] ORDER_BY_FIELDS = {"recordId"};

	public static DDLRecordIdComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(DDLRecord record1, DDLRecord record2) {
		int value = Long.compare(record1.getRecordId(), record2.getRecordId());

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

	private DDLRecordIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DDLRecordIdComparator _INSTANCE_ASCENDING =
		new DDLRecordIdComparator(true);

	private static final DDLRecordIdComparator _INSTANCE_DESCENDING =
		new DDLRecordIdComparator(false);

	private final boolean _ascending;

}