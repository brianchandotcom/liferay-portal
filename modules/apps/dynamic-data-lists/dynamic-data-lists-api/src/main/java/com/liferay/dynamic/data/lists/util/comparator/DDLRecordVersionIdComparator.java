/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.util.comparator;

import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class DDLRecordVersionIdComparator
	extends OrderByComparator<DDLRecordVersion> {

	public static DDLRecordVersionIdComparator getInstance(boolean ascending) {
		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		DDLRecordVersion recordVersion1, DDLRecordVersion recordVersion2) {

		int value = Long.compare(
			recordVersion1.getRecordVersionId(),
			recordVersion2.getRecordVersionId());

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

	private DDLRecordVersionIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DDLRecordVersionIdComparator _INSTANCE_ASCENDING =
		new DDLRecordVersionIdComparator(true);

	private static final DDLRecordVersionIdComparator _INSTANCE_DESCENDING =
		new DDLRecordVersionIdComparator(false);

	private static final String _ORDER_BY_ASC =
		"DDLRecordVersion.recordVersionId ASC";

	private static final String _ORDER_BY_DESC =
		"DDLRecordVersion.recordVersionId DESC";

	private static final String[] _ORDER_BY_FIELDS = {"recordVersionId"};

	private final boolean _ascending;

}