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

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

/**
 * The table class for the &quot;UserNotificationEvent&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEvent
 * @generated
 */
public class UserNotificationEventTable
	extends BaseTable<UserNotificationEventTable> {

	public static final UserNotificationEventTable INSTANCE =
		new UserNotificationEventTable();

	public final Column<UserNotificationEventTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserNotificationEventTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Long>
		userNotificationEventId = createColumn(
			"userNotificationEventId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<UserNotificationEventTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Long> timestamp =
		createColumn(
			"timestamp", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Integer> deliveryType =
		createColumn(
			"deliveryType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Long> deliverBy =
		createColumn(
			"deliverBy", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Boolean> delivered =
		createColumn(
			"delivered", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Clob> payload =
		createColumn("payload", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Boolean> actionRequired =
		createColumn(
			"actionRequired", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<UserNotificationEventTable, Boolean> archived =
		createColumn(
			"archived", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private UserNotificationEventTable() {
		super("UserNotificationEvent", UserNotificationEventTable::new);
	}

}