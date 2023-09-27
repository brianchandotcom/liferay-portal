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

	public static final String ORDER_BY_ASC =
		"DDMFormInstanceRecord.formInstanceRecordId ASC";

	public static final String ORDER_BY_DESC =
		"DDMFormInstanceRecord.formInstanceRecordId DESC";

	public static final String[] ORDER_BY_FIELDS = {"formInstanceRecordId"};

	public DDMFormInstanceRecordIdComparator() {
		this(false);
	}

	public DDMFormInstanceRecordIdComparator(boolean ascending) {
		_ascending = ascending;
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