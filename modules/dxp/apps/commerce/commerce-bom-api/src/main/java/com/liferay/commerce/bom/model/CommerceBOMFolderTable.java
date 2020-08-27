/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.bom.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceBOMFolder&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceBOMFolder
 * @generated
 */
public class CommerceBOMFolderTable extends BaseTable<CommerceBOMFolderTable> {

	public static final CommerceBOMFolderTable INSTANCE =
		new CommerceBOMFolderTable();

	public final Column<CommerceBOMFolderTable, Long> commerceBOMFolderId =
		createColumn(
			"commerceBOMFolderId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceBOMFolderTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Long>
		parentCommerceBOMFolderId = createColumn(
			"parentCommerceBOMFolderId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceBOMFolderTable, String> treePath = createColumn(
		"treePath", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CommerceBOMFolderTable() {
		super("CommerceBOMFolder", CommerceBOMFolderTable::new);
	}

}