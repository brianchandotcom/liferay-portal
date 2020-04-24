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
 * The table class for the &quot;DDMDataProviderInstance&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstance
 * @generated
 */
public class DDMDataProviderInstanceTable
	extends BaseTable<DDMDataProviderInstanceTable> {

	public static final DDMDataProviderInstanceTable INSTANCE =
		new DDMDataProviderInstanceTable();

	public final Column<DDMDataProviderInstanceTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DDMDataProviderInstanceTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Long>
		dataProviderInstanceId = createColumn(
			"dataProviderInstanceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<DDMDataProviderInstanceTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, String> name =
		createColumn("name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Clob> definition =
		createColumn("definition", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DDMDataProviderInstanceTable, Date> lastPublishDate =
		createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private DDMDataProviderInstanceTable() {
		super("DDMDataProviderInstance", DDMDataProviderInstanceTable::new);
	}

}