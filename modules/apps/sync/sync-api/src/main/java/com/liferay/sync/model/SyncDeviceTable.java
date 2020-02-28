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

package com.liferay.sync.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;SyncDevice&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see SyncDevice
 * @generated
 */
public class SyncDeviceTable extends BaseTable<SyncDeviceTable> {

	public static final SyncDeviceTable INSTANCE = new SyncDeviceTable();

	public final Column<SyncDeviceTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Long> syncDeviceId = createColumn(
		"syncDeviceId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<SyncDeviceTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Long> buildNumber = createColumn(
		"buildNumber", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Integer> featureSet = createColumn(
		"featureSet", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, String> hostname = createColumn(
		"hostname", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<SyncDeviceTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private SyncDeviceTable() {
		super("SyncDevice", SyncDeviceTable::new);
	}

}