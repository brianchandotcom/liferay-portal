/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Clob;
import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;LayoutRevision&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevision
 * @generated
 */
public class LayoutRevisionTable extends BaseTable<LayoutRevisionTable> {

	public static final LayoutRevisionTable INSTANCE =
		new LayoutRevisionTable();

	public final Column<LayoutRevisionTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutRevisionTable, Long> layoutRevisionId =
		createColumn(
			"layoutRevisionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutRevisionTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> layoutSetBranchId =
		createColumn(
			"layoutSetBranchId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> layoutBranchId =
		createColumn(
			"layoutBranchId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> parentLayoutRevisionId =
		createColumn(
			"parentLayoutRevisionId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Boolean> head = createColumn(
		"head", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Boolean> major = createColumn(
		"major", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> plid = createColumn(
		"plid", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Boolean> privateLayout =
		createColumn(
			"privateLayout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> keywords = createColumn(
		"keywords", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> robots = createColumn(
		"robots", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Clob> typeSettings = createColumn(
		"typeSettings", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> iconImageId = createColumn(
		"iconImageId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> themeId = createColumn(
		"themeId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> colorSchemeId =
		createColumn(
			"colorSchemeId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Clob> css = createColumn(
		"css", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Long> statusByUserId =
		createColumn(
			"statusByUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, String> statusByUserName =
		createColumn(
			"statusByUserName", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutRevisionTable, Date> statusDate = createColumn(
		"statusDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);

	private LayoutRevisionTable() {
		super("LayoutRevision", LayoutRevisionTable::new);
	}

}