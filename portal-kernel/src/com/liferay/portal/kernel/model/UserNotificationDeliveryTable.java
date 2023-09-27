/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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