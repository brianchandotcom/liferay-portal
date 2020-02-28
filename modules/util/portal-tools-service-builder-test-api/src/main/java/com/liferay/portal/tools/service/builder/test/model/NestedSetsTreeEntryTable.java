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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;NestedSetsTreeEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see NestedSetsTreeEntry
 * @generated
 */
public class NestedSetsTreeEntryTable
	extends BaseTable<NestedSetsTreeEntryTable> {

	public static final NestedSetsTreeEntryTable INSTANCE =
		new NestedSetsTreeEntryTable();

	public final Column<NestedSetsTreeEntryTable, Long> nestedSetsTreeEntryId =
		createColumn(
			"nestedSetsTreeEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<NestedSetsTreeEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<NestedSetsTreeEntryTable, Long>
		parentNestedSetsTreeEntryId = createColumn(
			"parentNestedSetsTreeEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NestedSetsTreeEntryTable, Long>
		leftNestedSetsTreeEntryId = createColumn(
			"leftNestedSetsTreeEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<NestedSetsTreeEntryTable, Long>
		rightNestedSetsTreeEntryId = createColumn(
			"rightNestedSetsTreeEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private NestedSetsTreeEntryTable() {
		super("NestedSetsTreeEntry", NestedSetsTreeEntryTable::new);
	}

}