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

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #INSTANCE_DESCENDING}
	 */
	@Deprecated
	public StructureStructureKeyComparator() {
		this(false);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getInstance(boolean)}
	 */
	@Deprecated
	public StructureStructureKeyComparator(boolean ascending) {
		_ascending = ascending;
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