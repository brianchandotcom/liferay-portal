/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AnnouncementsDelivery&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsDelivery
 * @generated
 */
public class AnnouncementsDeliveryTable
	extends BaseTable<AnnouncementsDeliveryTable> {

	public static final AnnouncementsDeliveryTable INSTANCE =
		new AnnouncementsDeliveryTable();

	public final Column<AnnouncementsDeliveryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AnnouncementsDeliveryTable, Long> deliveryId =
		createColumn(
			"deliveryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AnnouncementsDeliveryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsDeliveryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsDeliveryTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsDeliveryTable, Boolean> email =
		createColumn(
			"email", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsDeliveryTable, Boolean> sms = createColumn(
		"sms", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<AnnouncementsDeliveryTable, Boolean> website =
		createColumn(
			"website", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private AnnouncementsDeliveryTable() {
		super("AnnouncementsDelivery", AnnouncementsDeliveryTable::new);
	}

}