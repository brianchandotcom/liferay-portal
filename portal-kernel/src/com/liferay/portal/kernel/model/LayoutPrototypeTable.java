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
 * The table class for the &quot;LayoutPrototype&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPrototype
 * @generated
 */
public class LayoutPrototypeTable extends BaseTable<LayoutPrototypeTable> {

	public static final LayoutPrototypeTable INSTANCE =
		new LayoutPrototypeTable();

	public final Column<LayoutPrototypeTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutPrototypeTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Long> layoutPrototypeId =
		createColumn(
			"layoutPrototypeId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<LayoutPrototypeTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Clob> name = createColumn(
		"name", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Clob> description = createColumn(
		"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, String> settings = createColumn(
		"settings_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutPrototypeTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private LayoutPrototypeTable() {
		super("LayoutPrototype", LayoutPrototypeTable::new);
	}

}