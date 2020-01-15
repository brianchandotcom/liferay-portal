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

package com.liferay.exportimport.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the ExportImportConfiguration.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ExportImportConfigurationTable
	extends Table<ExportImportConfigurationTable> {

	public static final ExportImportConfigurationTable INSTANCE =
		new ExportImportConfigurationTable();

	public final Column<ExportImportConfigurationTable, Long> mvccVersion =
		createColumn("mvccVersion", Long.class, Types.BIGINT);
	public final Column<ExportImportConfigurationTable, Long>
		exportImportConfigurationId = createColumn(
			"exportImportConfigurationId", Long.class, Types.BIGINT);
	public final Column<ExportImportConfigurationTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT);
	public final Column<ExportImportConfigurationTable, Long> companyId =
		createColumn("companyId", Long.class, Types.BIGINT);
	public final Column<ExportImportConfigurationTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT);
	public final Column<ExportImportConfigurationTable, String> userName =
		createColumn("userName", String.class, Types.VARCHAR);
	public final Column<ExportImportConfigurationTable, Date> createDate =
		createColumn("createDate", Date.class, Types.TIMESTAMP);
	public final Column<ExportImportConfigurationTable, Date> modifiedDate =
		createColumn("modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<ExportImportConfigurationTable, String> name =
		createColumn("name", String.class, Types.VARCHAR);
	public final Column<ExportImportConfigurationTable, String> description =
		createColumn("description", String.class, Types.VARCHAR);
	public final Column<ExportImportConfigurationTable, Integer> type =
		createColumn("type_", Integer.class, Types.INTEGER);
	public final Column<ExportImportConfigurationTable, Clob> settings =
		createColumn("settings_", Clob.class, Types.CLOB);
	public final Column<ExportImportConfigurationTable, Integer> status =
		createColumn("status", Integer.class, Types.INTEGER);
	public final Column<ExportImportConfigurationTable, Long> statusByUserId =
		createColumn("statusByUserId", Long.class, Types.BIGINT);
	public final Column<ExportImportConfigurationTable, String>
		statusByUserName = createColumn(
			"statusByUserName", String.class, Types.VARCHAR);
	public final Column<ExportImportConfigurationTable, Date> statusDate =
		createColumn("statusDate", Date.class, Types.TIMESTAMP);

	private ExportImportConfigurationTable() {
		super("ExportImportConfiguration", ExportImportConfigurationTable::new);
	}

}