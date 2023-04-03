/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

	public final Column<FaroNotificationTable, Long> faroNotificationId =
		createColumn(
			"faroNotificationId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<FaroNotificationTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
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