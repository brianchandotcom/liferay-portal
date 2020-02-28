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