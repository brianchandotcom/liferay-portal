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

package com.liferay.powwow.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;PowwowMeeting&quot; database table.
 *
 * @author Shinn Lok
 * @see PowwowMeeting
 * @generated
 */
public class PowwowMeetingTable extends BaseTable<PowwowMeetingTable> {

	public static final PowwowMeetingTable INSTANCE = new PowwowMeetingTable();

	public final Column<PowwowMeetingTable, Long> powwowMeetingId =
		createColumn(
			"powwowMeetingId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PowwowMeetingTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Long> powwowServerId = createColumn(
		"powwowServerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, String> providerType = createColumn(
		"providerType", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, String> providerTypeMetadata =
		createColumn(
			"providerTypeMetadata", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Long> calendarBookingId =
		createColumn(
			"calendarBookingId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowMeetingTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private PowwowMeetingTable() {
		super("PowwowMeeting", PowwowMeetingTable::new);
	}

}