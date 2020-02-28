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

package com.liferay.push.notifications.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;PushNotificationsDevice&quot; database table.
 *
 * @author Bruno Farache
 * @see PushNotificationsDevice
 * @generated
 */
public class PushNotificationsDeviceTable
	extends BaseTable<PushNotificationsDeviceTable> {

	public static final PushNotificationsDeviceTable INSTANCE =
		new PushNotificationsDeviceTable();

	public final Column<PushNotificationsDeviceTable, Long>
		pushNotificationsDeviceId = createColumn(
			"pushNotificationsDeviceId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PushNotificationsDeviceTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PushNotificationsDeviceTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PushNotificationsDeviceTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PushNotificationsDeviceTable, String> platform =
		createColumn(
			"platform", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PushNotificationsDeviceTable, String> token =
		createColumn("token", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private PushNotificationsDeviceTable() {
		super("PushNotificationsDevice", PushNotificationsDeviceTable::new);
	}

}