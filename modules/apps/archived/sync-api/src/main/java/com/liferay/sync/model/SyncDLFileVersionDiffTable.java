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

package com.liferay.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SyncDLFileVersionDiff&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDLFileVersionDiff
 * @generated
 */
public class SyncDLFileVersionDiffTable
	extends BaseTable<SyncDLFileVersionDiffTable> {

	public static final SyncDLFileVersionDiffTable INSTANCE =
		new SyncDLFileVersionDiffTable();

	public final Column<SyncDLFileVersionDiffTable, Long>
		syncDLFileVersionDiffId = createColumn(
			"syncDLFileVersionDiffId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<SyncDLFileVersionDiffTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> fileEntryId =
		createColumn(
			"fileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> sourceFileVersionId =
		createColumn(
			"sourceFileVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> targetFileVersionId =
		createColumn(
			"targetFileVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> dataFileEntryId =
		createColumn(
			"dataFileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Long> size = createColumn(
		"size_", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDLFileVersionDiffTable, Date> expirationDate =
		createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private SyncDLFileVersionDiffTable() {
		super("SyncDLFileVersionDiff", SyncDLFileVersionDiffTable::new);
	}

}