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
 * The table class for the &quot;LVEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LVEntry
 * @generated
 */
public class LVEntryTable extends BaseTable<LVEntryTable> {

	public static final LVEntryTable INSTANCE = new LVEntryTable();

	public final Column<LVEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LVEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Long> headId = createColumn(
		"headId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, String> defaultLanguageId = createColumn(
		"defaultLanguageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Long> lvEntryId = createColumn(
		"lvEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LVEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LVEntryTable, String> uniqueGroupKey = createColumn(
		"uniqueGroupKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private LVEntryTable() {
		super("LVEntry", LVEntryTable::new);
	}

}