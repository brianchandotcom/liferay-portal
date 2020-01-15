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

package com.liferay.data.engine.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DEDataListView.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DEDataListViewTable extends Table<DEDataListViewTable> {

	public static final DEDataListViewTable INSTANCE =
		new DEDataListViewTable();

	public final Column<DEDataListViewTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<DEDataListViewTable, Long> deDataListViewId =
		createColumn("deDataListViewId", Long.class, Types.BIGINT);
	public final Column<DEDataListViewTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DEDataListViewTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DEDataListViewTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<DEDataListViewTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<DEDataListViewTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<DEDataListViewTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<DEDataListViewTable, Clob> appliedFilters =
		createColumn("appliedFilters", Clob.class, Types.CLOB);
	public final Column<DEDataListViewTable, Long> ddmStructureId =
		createColumn("ddmStructureId", Long.class, Types.BIGINT);
	public final Column<DEDataListViewTable, Clob> fieldNames = createColumn(
		"fieldNames", Clob.class, Types.CLOB);
	public final Column<DEDataListViewTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<DEDataListViewTable, String> sortField = createColumn(
		"sortField", String.class, Types.VARCHAR);

	private DEDataListViewTable() {
		super("DEDataListView", DEDataListViewTable::new);
	}

}