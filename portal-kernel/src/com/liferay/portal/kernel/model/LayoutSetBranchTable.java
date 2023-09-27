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
 * The table class for the &quot;LayoutSetBranch&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranch
 * @generated
 */
public class LayoutSetBranchTable extends BaseTable<LayoutSetBranchTable> {

	public static final LayoutSetBranchTable INSTANCE =
		new LayoutSetBranchTable();

	public final Column<LayoutSetBranchTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutSetBranchTable, Long> layoutSetBranchId =
		createColumn(
			"layoutSetBranchId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutSetBranchTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Boolean> privateLayout =
		createColumn(
			"privateLayout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, String> description =
		createColumn(
			"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Boolean> master = createColumn(
		"master", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, String> themeId = createColumn(
		"themeId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, String> colorSchemeId =
		createColumn(
			"colorSchemeId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Clob> css = createColumn(
		"css", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Clob> settings = createColumn(
		"settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, String> layoutSetPrototypeUuid =
		createColumn(
			"layoutSetPrototypeUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSetBranchTable, Boolean>
		layoutSetPrototypeLinkEnabled = createColumn(
			"layoutSetPrototypeLinkEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);

	private LayoutSetBranchTable() {
		super("LayoutSetBranch", LayoutSetBranchTable::new);
	}

}