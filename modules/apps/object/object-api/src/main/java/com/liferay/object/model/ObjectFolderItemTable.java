/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;ObjectFolderItem&quot; database table.
 *
 * @author Marco Leo
 * @see ObjectFolderItem
 * @generated
 */
public class ObjectFolderItemTable extends BaseTable<ObjectFolderItemTable> {

	public static final ObjectFolderItemTable INSTANCE =
		new ObjectFolderItemTable();

	public final Column<ObjectFolderItemTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<ObjectFolderItemTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Long> objectFolderItemId =
		createColumn(
			"objectFolderItemId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<ObjectFolderItemTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Long> objectDefinitionId =
		createColumn(
			"objectDefinitionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Long> objectFolderId =
		createColumn(
			"objectFolderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Integer> positionX =
		createColumn(
			"positionX", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<ObjectFolderItemTable, Integer> positionY =
		createColumn(
			"positionY", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private ObjectFolderItemTable() {
		super("ObjectFolderItem", ObjectFolderItemTable::new);
	}

}