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

package com.liferay.frontend.view.state.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;FVSCustomEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see FVSCustomEntry
 * @generated
 */
public class FVSCustomEntryTable extends BaseTable<FVSCustomEntryTable> {

	public static final FVSCustomEntryTable INSTANCE =
		new FVSCustomEntryTable();

	public final Column<FVSCustomEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FVSCustomEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, Long> fvsCustomEntryId =
		createColumn(
			"fvsCustomEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<FVSCustomEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, String> entityId = createColumn(
		"entityId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, Long> fvsEntryId = createColumn(
		"fvsEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FVSCustomEntryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private FVSCustomEntryTable() {
		super("FVSCustomEntry", FVSCustomEntryTable::new);
	}

}