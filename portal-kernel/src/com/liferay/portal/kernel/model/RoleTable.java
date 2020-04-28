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

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;Role_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Role
 * @generated
 */
public class RoleTable extends BaseTable<RoleTable> {

	public static final RoleTable INSTANCE = new RoleTable();

	public final Column<RoleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<RoleTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RoleTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<RoleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RoleTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<RoleTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RoleTable, String> title = createColumn(
		"title", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RoleTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<RoleTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<RoleTable, String> subtype = createColumn(
		"subtype", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private RoleTable() {
		super("Role_", RoleTable::new);
	}

}