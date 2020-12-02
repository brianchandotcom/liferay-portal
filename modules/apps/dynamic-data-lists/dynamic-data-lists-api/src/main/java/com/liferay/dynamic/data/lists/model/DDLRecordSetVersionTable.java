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

package com.liferay.dynamic.data.lists.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDLRecordSetVersion&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersion
 * @generated
 */
public class DDLRecordSetVersionTable
	extends BaseTable<DDLRecordSetVersionTable> {

	public static final DDLRecordSetVersionTable INSTANCE =
		new DDLRecordSetVersionTable();

	public final Column<DDLRecordSetVersionTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDLRecordSetVersionTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordSetVersionTable, Long> recordSetVersionId =
		createColumn(
			"recordSetVersionId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDLRecordSetVersionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Long> recordSetId =
		createColumn(
			"recordSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Long> DDMStructureVersionId =
		createColumn(
			"DDMStructureVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Clob> settings = createColumn(
		"settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, String> version =
		createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Integer> status =
		createColumn(
			"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDLRecordSetVersionTable, Date> statusDate =
		createColumn(
			"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDLRecordSetVersionTable() {
		super("DDLRecordSetVersion", DDLRecordSetVersionTable::new);
	}

}