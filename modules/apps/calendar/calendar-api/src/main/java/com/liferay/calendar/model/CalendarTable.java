/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.calendar.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Calendar&quot; database table.
 *
 * @author Eduardo Lundgren
 * @see Calendar
 * @generated
 */
public class CalendarTable extends BaseTable<CalendarTable> {

	public static final CalendarTable INSTANCE = new CalendarTable();

	public final Column<CalendarTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CalendarTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CalendarTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Long> calendarId = createColumn(
		"calendarId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CalendarTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Long> calendarResourceId = createColumn(
		"calendarResourceId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, String> timeZoneId = createColumn(
		"timeZoneId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Integer> color = createColumn(
		"color", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Boolean> defaultCalendar = createColumn(
		"defaultCalendar", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Boolean> enableComments = createColumn(
		"enableComments", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Boolean> enableRatings = createColumn(
		"enableRatings", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<CalendarTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private CalendarTable() {
		super("Calendar", CalendarTable::new);
	}

}