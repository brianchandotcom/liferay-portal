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
 * The table class for the &quot;DDMStructureLayout&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayout
 * @generated
 */
public class DDMStructureLayoutTable
	extends BaseTable<DDMStructureLayoutTable> {

	public static final DDMStructureLayoutTable INSTANCE =
		new DDMStructureLayoutTable();

	public final Column<DDMStructureLayoutTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMStructureLayoutTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Long> structureLayoutId =
		createColumn(
			"structureLayoutId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMStructureLayoutTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, String> structureLayoutKey =
		createColumn(
			"structureLayoutKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Long> structureVersionId =
		createColumn(
			"structureVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Clob> name = createColumn(
		"name", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMStructureLayoutTable, Clob> definition =
		createColumn("definition", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);

	private DDMStructureLayoutTable() {
		super("DDMStructureLayout", DDMStructureLayoutTable::new);
	}

}