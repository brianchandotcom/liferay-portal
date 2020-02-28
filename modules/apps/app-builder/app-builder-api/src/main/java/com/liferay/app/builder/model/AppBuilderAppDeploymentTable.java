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

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;AppBuilderAppDeployment&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDeployment
 * @generated
 */
public class AppBuilderAppDeploymentTable
	extends BaseTable<AppBuilderAppDeploymentTable> {

	public static final AppBuilderAppDeploymentTable INSTANCE =
		new AppBuilderAppDeploymentTable();

	public final Column<AppBuilderAppDeploymentTable, Long>
		appBuilderAppDeploymentId = createColumn(
			"appBuilderAppDeploymentId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppDeploymentTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDeploymentTable, Long> appBuilderAppId =
		createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDeploymentTable, Clob> settings =
		createColumn("settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDeploymentTable, String> type =
		createColumn("type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private AppBuilderAppDeploymentTable() {
		super("AppBuilderAppDeployment", AppBuilderAppDeploymentTable::new);
	}

}