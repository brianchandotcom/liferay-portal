/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MBCategory&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see MBCategory
 * @generated
 */
public class MBCategoryTable extends BaseTable<MBCategoryTable> {

	public static final MBCategoryTable INSTANCE = new MBCategoryTable();

	public final Column<MBCategoryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MBCategoryTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBCategoryTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Long> categoryId = createColumn(
		"categoryId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<MBCategoryTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Long> parentCategoryId = createColumn(
		"parentCategoryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, String> displayStyle = createColumn(
		"displayStyle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, String> friendlyURL = createColumn(
		"friendlyURL", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Date> lastPublishDate = createColumn(
		"lastPublishDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Long> statusByUserId = createColumn(
		"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<MBCategoryTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private MBCategoryTable() {
		super("MBCategory", MBCategoryTable::new);
	}

}