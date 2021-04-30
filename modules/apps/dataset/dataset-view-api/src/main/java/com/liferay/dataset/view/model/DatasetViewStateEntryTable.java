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
 * The table class for the &quot;DatasetViewStateEntry&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DatasetViewStateEntry
 * @generated
 */
public class DatasetViewStateEntryTable
	extends BaseTable<DatasetViewStateEntryTable> {

	public static final DatasetViewStateEntryTable INSTANCE =
		new DatasetViewStateEntryTable();

	public final Column<DatasetViewStateEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DatasetViewStateEntryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetViewStateEntryTable, Long>
		datasetViewStateEntryId = createColumn(
			"datasetViewStateEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DatasetViewStateEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetViewStateEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DatasetViewStateEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DatasetViewStateEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DatasetViewStateEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DatasetViewStateEntryTable, String> viewState =
		createColumn(
			"viewState", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private DatasetViewStateEntryTable() {
		super("DatasetViewStateEntry", DatasetViewStateEntryTable::new);
	}

}