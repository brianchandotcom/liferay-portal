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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDLRecord&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecord
 * @generated
 */
public class DDLRecordTable extends BaseTable<DDLRecordTable> {

	public static final DDLRecordTable INSTANCE = new DDLRecordTable();

	public final Column<DDLRecordTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDLRecordTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> recordId = createColumn(
		"recordId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDLRecordTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> versionUserId = createColumn(
		"versionUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, String> versionUserName = createColumn(
		"versionUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> DDMStorageId = createColumn(
		"DDMStorageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> recordSetId = createColumn(
		"recordSetId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, String> recordSetVersion = createColumn(
		"recordSetVersion", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, String> className = createColumn(
		"className", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Integer> displayIndex = createColumn(
		"displayIndex", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DDLRecordTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DDLRecordTable() {
		super("DDLRecord", DDLRecordTable::new);
	}

}