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

package com.liferay.document.library.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the DLFileShortcut.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DLFileShortcutTable extends Table<DLFileShortcutTable> {

	public static final DLFileShortcutTable INSTANCE =
		new DLFileShortcutTable();

	public final Column<DLFileShortcutTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR);
	public final Column<DLFileShortcutTable, Long> fileShortcutId =
		createColumn("fileShortcutId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<DLFileShortcutTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<DLFileShortcutTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<DLFileShortcutTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, Long> toFileEntryId = createColumn(
		"toFileEntryId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR);
	public final Column<DLFileShortcutTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN);
	public final Column<DLFileShortcutTable, Date> lastPublishDate =
		createColumn("lastPublishDate", Date.class, Types.TIMESTAMP);
	public final Column<DLFileShortcutTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER);
	public final Column<DLFileShortcutTable, Long> statusByUserId =
		createColumn("statusByUserId", Long.class, Types.BIGINT);
	public final Column<DLFileShortcutTable, String> statusByUserName =
		createColumn("statusByUserName", String.class, Types.VARCHAR);
	public final Column<DLFileShortcutTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP);

	private DLFileShortcutTable() {
		super("DLFileShortcut", DLFileShortcutTable::new);
	}

}