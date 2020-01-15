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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Ticket.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class TicketTable extends Table<TicketTable> {

	public static final TicketTable INSTANCE = new TicketTable();

	public final Column<TicketTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<TicketTable, Long> ticketId = createColumn(
		"ticketId", Long.class, Types.BIGINT);
	public final Column<TicketTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<TicketTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<TicketTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<TicketTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<TicketTable, String> key = createColumn(
		"key_", String.class, Types.VARCHAR);
	public final Column<TicketTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER);
	public final Column<TicketTable, Clob> extraInfo = createColumn(
		"extraInfo", Clob.class, Types.CLOB);
	public final Column<TicketTable, Date> expirationDate = createColumn(
		"expirationDate", Date.class, Types.TIMESTAMP);

	private TicketTable() {
		super("Ticket", TicketTable::new);
	}

}