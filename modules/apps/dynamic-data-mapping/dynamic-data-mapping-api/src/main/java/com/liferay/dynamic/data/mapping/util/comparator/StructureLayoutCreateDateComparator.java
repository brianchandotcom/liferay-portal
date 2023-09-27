/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

/**
 * @author Gabriel Albuquerque
 */
public class StructureLayoutCreateDateComparator
	extends OrderByComparator<DDMStructureLayout> {

	public static final String ORDER_BY_ASC =
		"DDMStructureLayout.createDate ASC";

	public static final String ORDER_BY_DESC =
		"DDMStructureLayout.createDate DESC";

	public static final String[] ORDER_BY_FIELDS = {"createDate"};

	public StructureLayoutCreateDateComparator() {
		this(false);
	}

	public StructureLayoutCreateDateComparator(boolean ascending) {
		_ascending = ascending;
	}

	@Override
	public int compare(
		DDMStructureLayout structureLayout1,
		DDMStructureLayout structureLayout2) {

		int value = DateUtil.compareTo(
			structureLayout1.getCreateDate(), structureLayout2.getCreateDate());

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