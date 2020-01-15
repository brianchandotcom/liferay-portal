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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DDMFormInstanceRecordVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceRecordVersionTable
	extends Table<DDMFormInstanceRecordVersionTable> {

	public static final DDMFormInstanceRecordVersionTable INSTANCE =
		new DDMFormInstanceRecordVersionTable();

	public final Column<DDMFormInstanceRecordVersionTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, Long>
		formInstanceRecordVersionId = createColumn(
			"formInstanceRecordVersionId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceRecordVersionTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<DDMFormInstanceRecordVersionTable, Long>
		formInstanceId = createColumn(
			"formInstanceId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, String>
		formInstanceVersion = createColumn(
			"formInstanceVersion", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceRecordVersionTable, Long>
		formInstanceRecordId = createColumn(
			"formInstanceRecordId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, String> version =
		createColumn("version", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceRecordVersionTable, Long> storageId =
		createColumn("storageId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, Integer> status =
		createColumn("status", Integer.class, Types.INTEGER);
	public final Column<DDMFormInstanceRecordVersionTable, Long>
		statusByUserId = createColumn(
			"statusByUserId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceRecordVersionTable, String>
		statusByUserName = createColumn(
			"statusByUserName", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceRecordVersionTable, Date> statusDate =
		createColumn("statusDate", Date.class, Types.TIMESTAMP);

	private DDMFormInstanceRecordVersionTable() {
		super(
			"DDMFormInstanceRecordVersion",
			DDMFormInstanceRecordVersionTable::new);
	}

}