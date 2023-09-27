/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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