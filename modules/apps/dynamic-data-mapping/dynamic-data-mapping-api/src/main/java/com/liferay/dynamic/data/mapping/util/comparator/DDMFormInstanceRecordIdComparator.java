/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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