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
 * The table class for the &quot;PowwowParticipant&quot; database table.
 *
 * @author Shinn Lok
 * @see PowwowParticipant
 * @generated
 */
public class PowwowParticipantTable extends BaseTable<PowwowParticipantTable> {

	public static final PowwowParticipantTable INSTANCE =
		new PowwowParticipantTable();

	public final Column<PowwowParticipantTable, Long> powwowParticipantId =
		createColumn(
			"powwowParticipantId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<PowwowParticipantTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Long> powwowMeetingId =
		createColumn(
			"powwowMeetingId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Long> participantUserId =
		createColumn(
			"participantUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, String> emailAddress =
		createColumn(
			"emailAddress", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PowwowParticipantTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private PowwowParticipantTable() {
		super("PowwowParticipant", PowwowParticipantTable::new);
	}

}