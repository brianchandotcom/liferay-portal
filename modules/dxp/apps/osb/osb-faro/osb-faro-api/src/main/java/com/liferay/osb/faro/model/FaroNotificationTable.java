/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;OSBFaro_FaroNotification&quot; database table.
 *
 * @author Matthew Kong
 * @see FaroNotification
 * @generated
 */
public class FaroNotificationTable extends BaseTable<FaroNotificationTable> {

	public static final FaroNotificationTable INSTANCE =
		new FaroNotificationTable();

	public final Column<FaroNotificationTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<FaroNotificationTable, Long> faroNotificationId =
		createColumn(
			"faroNotificationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FaroNotificationTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, Long> createTime = createColumn(
		"createTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, Long> modifiedTime =
		createColumn(
			"modifiedTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, Long> ownerId = createColumn(
		"ownerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, String> scope = createColumn(
		"scope", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, Boolean> read = createColumn(
		"read_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<FaroNotificationTable, String> subtype = createColumn(
		"subtype", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private FaroNotificationTable() {
		super("OSBFaro_FaroNotification", FaroNotificationTable::new);
	}

}