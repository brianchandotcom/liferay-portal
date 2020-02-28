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

package com.liferay.app.builder.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AppBuilderApp&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderApp
 * @generated
 */
public class AppBuilderAppTable extends BaseTable<AppBuilderAppTable> {

	public static final AppBuilderAppTable INSTANCE = new AppBuilderAppTable();

	public final Column<AppBuilderAppTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> ddmStructureId = createColumn(
		"ddmStructureId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> ddmStructureLayoutId =
		createColumn(
			"ddmStructureLayoutId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Long> deDataListViewId =
		createColumn(
			"deDataListViewId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private AppBuilderAppTable() {
		super("AppBuilderApp", AppBuilderAppTable::new);
	}

}