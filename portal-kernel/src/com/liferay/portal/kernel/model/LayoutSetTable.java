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
 * The table class for the &quot;LayoutSet&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSet
 * @generated
 */
public class LayoutSetTable extends BaseTable<LayoutSetTable> {

	public static final LayoutSetTable INSTANCE = new LayoutSetTable();

	public final Column<LayoutSetTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutSetTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutSetTable, Long> layoutSetId = createColumn(
		"layoutSetId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutSetTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Boolean> privateLayout = createColumn(
		"privateLayout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Long> logoId = createColumn(
		"logoId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, String> themeId = createColumn(
		"themeId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, String> colorSchemeId = createColumn(
		"colorSchemeId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Long> faviconFileEntryId = createColumn(
		"faviconFileEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Clob> css = createColumn(
		"css", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Clob> settings = createColumn(
		"settings_", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, String> layoutSetPrototypeUuid =
		createColumn(
			"layoutSetPrototypeUuid", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<LayoutSetTable, Boolean> layoutSetPrototypeLinkEnabled =
		createColumn(
			"layoutSetPrototypeLinkEnabled", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);

	private LayoutSetTable() {
		super("LayoutSet", LayoutSetTable::new);
	}

}