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
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;DLFolder&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see DLFolder
 * @generated
 */
public class DLFolderTable extends BaseTable<DLFolderTable> {

	public static final DLFolderTable INSTANCE = new DLFolderTable();

	public final Column<DLFolderTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<DLFolderTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFolderTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> folderId = createColumn(
		"folderId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<DLFolderTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> repositoryId = createColumn(
		"repositoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Boolean> mountPoint = createColumn(
		"mountPoint", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> parentFolderId = createColumn(
		"parentFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Date> lastPostDate = createColumn(
		"lastPostDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> defaultFileEntryTypeId =
		createColumn(
			"defaultFileEntryTypeId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Boolean> hidden = createColumn(
		"hidden_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Integer> restrictionType = createColumn(
		"restrictionType", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, String> statusByUserName = createColumn(
		"statusByUserName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<DLFolderTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private DLFolderTable() {
		super("DLFolder", DLFolderTable::new);
	}

}