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

package com.liferay.chat.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Chat_Status&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Status
 * @generated
 */
public class StatusTable extends BaseTable<StatusTable> {

	public static final StatusTable INSTANCE = new StatusTable();

	public final Column<StatusTable, Long> statusId = createColumn(
		"statusId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<StatusTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StatusTable, Long> modifiedDate = createColumn(
		"modifiedDate", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<StatusTable, Boolean> online = createColumn(
		"online_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<StatusTable, Boolean> awake = createColumn(
		"awake", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<StatusTable, String> activePanelIds = createColumn(
		"activePanelIds", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<StatusTable, String> message = createColumn(
		"message", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<StatusTable, Boolean> playSound = createColumn(
		"playSound", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private StatusTable() {
		super("Chat_Status", StatusTable::new);
	}

}