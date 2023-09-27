/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Marketplace_App&quot; database table.
 *
 * @author Ryan Park
 * @see App
 * @generated
 */
public class AppTable extends BaseTable<AppTable> {

	public static final AppTable INSTANCE = new AppTable();

	public final Column<AppTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, Long> appId = createColumn(
		"appId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AppTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AppTable, Long> remoteAppId = createColumn(
		"remoteAppId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AppTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, String> category = createColumn(
		"category", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, String> iconURL = createColumn(
		"iconURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, String> version = createColumn(
		"version", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AppTable, Boolean> required = createColumn(
		"required", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private AppTable() {
		super("Marketplace_App", AppTable::new);
	}

}