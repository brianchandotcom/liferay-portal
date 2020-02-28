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

import java.sql.Types;

/**
 * The table class for the &quot;UserNotificationDelivery&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationDelivery
 * @generated
 */
public class UserNotificationDeliveryTable
	extends BaseTable<UserNotificationDeliveryTable> {

	public static final UserNotificationDeliveryTable INSTANCE =
		new UserNotificationDeliveryTable();

	public final Column<UserNotificationDeliveryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserNotificationDeliveryTable, Long>
		userNotificationDeliveryId = createColumn(
			"userNotificationDeliveryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<UserNotificationDeliveryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationDeliveryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationDeliveryTable, String> portletId =
		createColumn(
			"portletId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserNotificationDeliveryTable, Long> classNameId =
		createColumn(
			"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserNotificationDeliveryTable, Integer>
		notificationType = createColumn(
			"notificationType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<UserNotificationDeliveryTable, Integer> deliveryType =
		createColumn(
			"deliveryType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<UserNotificationDeliveryTable, Boolean> deliver =
		createColumn(
			"deliver", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private UserNotificationDeliveryTable() {
		super("UserNotificationDelivery", UserNotificationDeliveryTable::new);
	}

}