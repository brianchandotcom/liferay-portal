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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDMFormInstanceVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersion
 * @generated
 */
public class DDMFormInstanceVersionTable
	extends BaseTable<DDMFormInstanceVersionTable> {

	public static final DDMFormInstanceVersionTable INSTANCE =
		new DDMFormInstanceVersionTable();

	public final Column<DDMFormInstanceVersionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMFormInstanceVersionTable, Long>
		formInstanceVersionId = createColumn(
			"formInstanceVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDMFormInstanceVersionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Long> formInstanceId =
		createColumn(
			"formInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Long> structureVersionId =
		createColumn(
			"structureVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Clob> settings =
		createColumn("settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, String> version =
		createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceVersionTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDMFormInstanceVersionTable() {
		super("DDMFormInstanceVersion", DDMFormInstanceVersionTable::new);
	}

}