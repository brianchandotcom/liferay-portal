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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DDLRecordSet.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDLRecordSetTable extends Table<DDLRecordSetTable> {

	public static final DDLRecordSetTable INSTANCE = new DDLRecordSetTable();

	public final Column<DDLRecordSetTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, Long> recordSetId = createColumn(
		"recordSetId", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, Long> versionUserId = createColumn(
		"versionUserId", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, String> versionUserName =
		createColumn("versionUserName", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<DDLRecordSetTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<DDLRecordSetTable, Long> DDMStructureId = createColumn(
		"DDMStructureId", Long.class, Types.BIGINT);
	public final Column<DDLRecordSetTable, String> recordSetKey = createColumn(
		"recordSetKey", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<DDLRecordSetTable, Integer> minDisplayRows =
		createColumn("minDisplayRows", Integer.class, Types.INTEGER);
	public final Column<DDLRecordSetTable, Integer> scope = createColumn(
		"scope", Integer.class, Types.INTEGER);
	public final Column<DDLRecordSetTable, Clob> settings = createColumn(
		"settings_", Clob.class, Types.CLOB);
	public final Column<DDLRecordSetTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);

	private DDLRecordSetTable() {
		super("DDLRecordSet", DDLRecordSetTable::new);
	}

}