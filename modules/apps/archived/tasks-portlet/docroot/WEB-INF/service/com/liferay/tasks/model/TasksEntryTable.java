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

package com.liferay.tasks.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;TMS_TasksEntry&quot; database table.
 *
 * @author Ryan Park
 * @see TasksEntry
 * @generated
 */
public class TasksEntryTable extends BaseTable<TasksEntryTable> {

	public static final TasksEntryTable INSTANCE = new TasksEntryTable();

	public final Column<TasksEntryTable, Long> tasksEntryId = createColumn(
		"tasksEntryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<TasksEntryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Integer> priority = createColumn(
		"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Long> assigneeUserId = createColumn(
		"assigneeUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Long> resolverUserId = createColumn(
		"resolverUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Date> dueDate = createColumn(
		"dueDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Date> finishDate = createColumn(
		"finishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<TasksEntryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private TasksEntryTable() {
		super("TMS_TasksEntry", TasksEntryTable::new);
	}

}