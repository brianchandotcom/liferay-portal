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

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the MBMessage.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class MBMessageTable extends Table<MBMessageTable> {

	public static final MBMessageTable INSTANCE = new MBMessageTable();

	public final Column<MBMessageTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<MBMessageTable, Long> messageId = createColumn(
		"messageId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<MBMessageTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<MBMessageTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<MBMessageTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> categoryId = createColumn(
		"categoryId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> threadId = createColumn(
		"threadId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> rootMessageId = createColumn(
		"rootMessageId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, Long> parentMessageId = createColumn(
		"parentMessageId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR);
	public final Column<MBMessageTable, String> subject = createColumn(
		"subject", String.class, Types.VARCHAR);
	public final Column<MBMessageTable, Clob> body = createColumn(
		"body", Clob.class, Types.CLOB);
	public final Column<MBMessageTable, String> format = createColumn(
		"format", String.class, Types.VARCHAR);
	public final Column<MBMessageTable, Boolean> anonymous = createColumn(
		"anonymous", Boolean.class, Types.BOOLEAN);
	public final Column<MBMessageTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE);
	public final Column<MBMessageTable, Boolean> allowPingbacks = createColumn(
		"allowPingbacks", Boolean.class, Types.BOOLEAN);
	public final Column<MBMessageTable, Boolean> answer = createColumn(
		"answer", Boolean.class, Types.BOOLEAN);
	public final Column<MBMessageTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<MBMessageTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<MBMessageTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT);
	public final Column<MBMessageTable, String> statusByUserName = createColumn(
		"statusByUserName", String.class, Types.VARCHAR);
	public final Column<MBMessageTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private MBMessageTable() {
		super("MBMessage", MBMessageTable::new);
	}

}