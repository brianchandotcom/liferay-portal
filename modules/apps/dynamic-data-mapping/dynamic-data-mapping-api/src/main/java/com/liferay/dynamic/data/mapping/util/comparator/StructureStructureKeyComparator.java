/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.util.comparator;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Miguel Pastor
 */
public class StructureStructureKeyComparator
	extends OrderByComparator<DDMStructure> {

	public static final StructureStructureKeyComparator INSTANCE_ASCENDING =
		new StructureStructureKeyComparator(Boolean.TRUE);

	public static final StructureStructureKeyComparator INSTANCE_DESCENDING =
		new StructureStructureKeyComparator(Boolean.FALSE);

	public static final String ORDER_BY_ASC = "DDMStructure.structureKey ASC";

	public static final String ORDER_BY_DESC = "DDMStructure.structureKey DESC";

	public static final String[] ORDER_BY_FIELDS = {"structureKey"};

	public static StructureStructureKeyComparator getInstance(
		boolean ascending) {

		if (ascending) {
			return INSTANCE_ASCENDING;
		}

		return INSTANCE_DESCENDING;
	}

	@Override
	public int compare(DDMStructure structure1, DDMStructure structure2) {
		String structureKey1 = structure1.getStructureKey();

		structureKey1 = StringUtil.toLowerCase(structureKey1);

		String structureKey2 = structure2.getStructureKey();

		structureKey2 = StringUtil.toLowerCase(structureKey2);

		int value = structureKey1.compareTo(structureKey2);

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

	private StructureStructureKeyComparator(Boolean ascending) {
		_ascending = ascending;
	}

	private final boolean _ascending;

}