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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MBThread&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MBThread
 * @generated
 */
public class MBThreadTable extends BaseTable<MBThreadTable> {

	public static final MBThreadTable INSTANCE = new MBThreadTable();

	public final Column<MBThreadTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> threadId = createColumn(
		"threadId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBThreadTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> categoryId = createColumn(
		"categoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> rootMessageId = createColumn(
		"rootMessageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> rootMessageUserId = createColumn(
		"rootMessageUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Integer> messageCount = createColumn(
		"messageCount", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> lastPostByUserId = createColumn(
		"lastPostByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Date> lastPostDate = createColumn(
		"lastPostDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Double> priority = createColumn(
		"priority", Double.class, Types.DOUBLE, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Boolean> question = createColumn(
		"question", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, String> statusByUserName = createColumn(
		"statusByUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBThreadTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MBThreadTable() {
		super("MBThread", MBThreadTable::new);
	}

}