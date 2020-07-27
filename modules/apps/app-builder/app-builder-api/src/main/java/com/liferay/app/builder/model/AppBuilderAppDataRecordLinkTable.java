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

/**
 * The table class for the &quot;AppBuilderAppDataRecordLink&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLink
 * @generated
 */
public class AppBuilderAppDataRecordLinkTable
	extends BaseTable<AppBuilderAppDataRecordLinkTable> {

	public static final AppBuilderAppDataRecordLinkTable INSTANCE =
		new AppBuilderAppDataRecordLinkTable();

	public final Column<AppBuilderAppDataRecordLinkTable, Long>
		appBuilderAppDataRecordLinkId = createColumn(
			"appBuilderAppDataRecordLinkId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AppBuilderAppDataRecordLinkTable, Long> groupId =
		createColumn("groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long>
		appBuilderAppId = createColumn(
			"appBuilderAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long>
		appBuilderAppVersionId = createColumn(
			"appBuilderAppVersionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<AppBuilderAppDataRecordLinkTable, Long> ddlRecordId =
		createColumn(
			"ddlRecordId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AppBuilderAppDataRecordLinkTable() {
		super(
			"AppBuilderAppDataRecordLink",
			AppBuilderAppDataRecordLinkTable::new);
	}

}