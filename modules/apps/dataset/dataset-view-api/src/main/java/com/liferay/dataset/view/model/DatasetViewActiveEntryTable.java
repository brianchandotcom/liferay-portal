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

package com.liferay.dataset.view.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DatasetViewActiveEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewActiveEntry
 * @generated
 */
public class DatasetViewActiveEntryTable
	extends BaseTable<DatasetViewActiveEntryTable> {

	public static final DatasetViewActiveEntryTable INSTANCE =
		new DatasetViewActiveEntryTable();

	public final Column<DatasetViewActiveEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DatasetViewActiveEntryTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, Long>
		datasetViewActiveEntryId = createColumn(
			"datasetViewActiveEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DatasetViewActiveEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, String> datasetDisplayId =
		createColumn(
			"datasetDisplayId", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, Long>
		datasetViewStateEntryId = createColumn(
			"datasetViewStateEntryId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetViewActiveEntryTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private DatasetViewActiveEntryTable() {
		super("DatasetViewActiveEntry", DatasetViewActiveEntryTable::new);
	}

}