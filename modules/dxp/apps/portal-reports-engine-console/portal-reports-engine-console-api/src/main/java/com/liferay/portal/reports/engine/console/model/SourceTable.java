/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Reports_Source&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Source
 * @generated
 */
public class SourceTable extends BaseTable<SourceTable> {

	public static final SourceTable INSTANCE = new SourceTable();

	public final Column<SourceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SourceTable, Long> sourceId = createColumn(
		"sourceId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SourceTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SourceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SourceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SourceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SourceTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SourceTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SourceTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SourceTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SourceTable, String> driverClassName = createColumn(
		"driverClassName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SourceTable, String> driverUrl = createColumn(
		"driverUrl", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SourceTable, String> driverUserName = createColumn(
		"driverUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SourceTable, String> driverPassword = createColumn(
		"driverPassword", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private SourceTable() {
		super("Reports_Source", SourceTable::new);
	}

}