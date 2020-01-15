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

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DDMFormInstanceVersion.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMFormInstanceVersionTable
	extends Table<DDMFormInstanceVersionTable> {

	public static final DDMFormInstanceVersionTable INSTANCE =
		new DDMFormInstanceVersionTable();

	public final Column<DDMFormInstanceVersionTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, Long>
		formInstanceVersionId = createColumn(
			"formInstanceVersionId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceVersionTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<DDMFormInstanceVersionTable, Long> formInstanceId =
		createColumn("formInstanceId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, Long> structureVersionId =
		createColumn("structureVersionId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, String> name =
		createColumn("name", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceVersionTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceVersionTable, Clob> settings =
		createColumn("settings_", Clob.class, Types.CLOB);
	public final Column<DDMFormInstanceVersionTable, String> version =
		createColumn("version", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceVersionTable, Integer> status =
		createColumn("status", Integer.class, Types.INTEGER);
	public final Column<DDMFormInstanceVersionTable, Long> statusByUserId =
		createColumn("statusByUserId", Long.class, Types.BIGINT);
	public final Column<DDMFormInstanceVersionTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<DDMFormInstanceVersionTable, Date> statusDate =
		createColumn("statusDate", Date.class, Types.TIMESTAMP);

	private DDMFormInstanceVersionTable() {
		super("DDMFormInstanceVersion", DDMFormInstanceVersionTable::new);
	}

}