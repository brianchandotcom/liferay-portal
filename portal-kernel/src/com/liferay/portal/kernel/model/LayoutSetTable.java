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