/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Shuyang Zhou
 */
public class DDMFormInstanceRecordVersionIdComparator
	extends OrderByComparator<DDMFormInstanceRecordVersion> {

	public static DDMFormInstanceRecordVersionIdComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return _INSTANCE_ASCENDING;
		}

		return _INSTANCE_DESCENDING;
	}

	@Override
	public int compare(
		DDMFormInstanceRecordVersion recordVersion1,
		DDMFormInstanceRecordVersion recordVersion2) {

		int value = Long.compare(
			recordVersion1.getFormInstanceRecordVersionId(),
			recordVersion2.getFormInstanceRecordVersionId());

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

	private DDMFormInstanceRecordVersionIdComparator(boolean ascending) {
		_ascending = ascending;
	}

	private static final DDMFormInstanceRecordVersionIdComparator
		_INSTANCE_ASCENDING = new DDMFormInstanceRecordVersionIdComparator(
			true);

	private static final DDMFormInstanceRecordVersionIdComparator
		_INSTANCE_DESCENDING = new DDMFormInstanceRecordVersionIdComparator(
			false);

	private static final String _ORDER_BY_ASC =
		"DDMFormInstanceRecordVersion.formInstanceRecordVersionId ASC";

	private static final String _ORDER_BY_DESC =
		"DDMFormInstanceRecordVersion.formInstanceRecordVersionId DESC";

	private static final String[] _ORDER_BY_FIELDS = {
		"formInstanceRecordVersionId"
	};

	private final boolean _ascending;

}