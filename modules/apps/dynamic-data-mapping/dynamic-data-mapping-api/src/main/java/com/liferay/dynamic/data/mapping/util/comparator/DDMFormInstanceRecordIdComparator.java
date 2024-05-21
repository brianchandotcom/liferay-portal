/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Rafael Praxedes
 */
public class DDMFormInstanceRecordIdComparator
	extends OrderByComparator<DDMFormInstanceRecord> {

	public static DDMFormInstanceRecordIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _ASCENDING;
		}

		return _DESCENDING;
	}

	@Override
	public int compare(
		DDMFormInstanceRecord record1, DDMFormInstanceRecord record2) {

		int value = Long.compare(
			record1.getFormInstanceRecordId(),
			record2.getFormInstanceRecordId());

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

	private DDMFormInstanceRecordIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DDMFormInstanceRecordIdComparator _ASCENDING =
		new DDMFormInstanceRecordIdComparator(true);

	private static final DDMFormInstanceRecordIdComparator _DESCENDING =
		new DDMFormInstanceRecordIdComparator(false);

	private static final String _ORDER_BY_ASC =
		"DDMFormInstanceRecord.formInstanceRecordId ASC";

	private static final String _ORDER_BY_DESC =
		"DDMFormInstanceRecord.formInstanceRecordId DESC";

	private static final String[] _ORDER_BY_FIELDS = {"formInstanceRecordId"};

	private final boolean _ascending;

}