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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DDMFormInstanceRecord&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceRecord
 * @generated
 */
public class DDMFormInstanceRecordTable
	extends BaseTable<DDMFormInstanceRecordTable> {

	public static final DDMFormInstanceRecordTable INSTANCE =
		new DDMFormInstanceRecordTable();

	public final Column<DDMFormInstanceRecordTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMFormInstanceRecordTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DDMFormInstanceRecordTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Long> formInstanceRecordId =
		createColumn(
			"formInstanceRecordId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDMFormInstanceRecordTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Long> versionUserId =
		createColumn(
			"versionUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, String> versionUserName =
		createColumn(
			"versionUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Long> formInstanceId =
		createColumn(
			"formInstanceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, String>
		formInstanceVersion = createColumn(
			"formInstanceVersion", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Long> storageId =
		createColumn(
			"storageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, String> version =
		createColumn(
			"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, String> ipAddress =
		createColumn(
			"ipAddress", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMFormInstanceRecordTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private DDMFormInstanceRecordTable() {
		super("DDMFormInstanceRecord", DDMFormInstanceRecordTable::new);
	}

}