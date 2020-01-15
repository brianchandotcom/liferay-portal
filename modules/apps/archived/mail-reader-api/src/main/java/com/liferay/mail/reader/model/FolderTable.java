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

package com.liferay.mail.reader.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the Folder.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FolderTable extends Table<FolderTable> {

	public static final FolderTable INSTANCE = new FolderTable();

	public final Column<FolderTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT);
	public final Column<FolderTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<FolderTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<FolderTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR);
	public final Column<FolderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP);
	public final Column<FolderTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP);
	public final Column<FolderTable, Long> accountId = createColumn(
		"accountId", Long.class, Types.BIGINT);
	public final Column<FolderTable, String> fullName = createColumn(
		"fullName", String.class, Types.VARCHAR);
	public final Column<FolderTable, String> displayName = createColumn(
		"displayName", String.class, Types.VARCHAR);
	public final Column<FolderTable, Integer> remoteMessageCount = createColumn(
		"remoteMessageCount", Integer.class, Types.INTEGER);

	private FolderTable() {
		super("Mail_Folder", FolderTable::new);
	}

}