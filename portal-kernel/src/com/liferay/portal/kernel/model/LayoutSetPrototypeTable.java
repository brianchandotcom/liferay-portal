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
 * The table class for the &quot;LayoutSetPrototype&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetPrototype
 * @generated
 */
public class LayoutSetPrototypeTable
	extends BaseTable<LayoutSetPrototypeTable> {

	public static final LayoutSetPrototypeTable INSTANCE =
		new LayoutSetPrototypeTable();

	public final Column<LayoutSetPrototypeTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<LayoutSetPrototypeTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Long> layoutSetPrototypeId =
		createColumn(
			"layoutSetPrototypeId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<LayoutSetPrototypeTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Clob> name = createColumn(
		"name", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Clob> description =
		createColumn(
			"description", Clob.class, Types.CLOB, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, String> settings =
		createColumn(
			"settings_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<LayoutSetPrototypeTable, Boolean> active = createColumn(
		"active_", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private LayoutSetPrototypeTable() {
		super("LayoutSetPrototype", LayoutSetPrototypeTable::new);
	}

}