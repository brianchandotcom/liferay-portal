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

/**
 * The table class for the &quot;Groups_Roles&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @see Role
 * @generated
 */
public class Groups_RolesTable extends BaseTable<Groups_RolesTable> {

	public static final Groups_RolesTable INSTANCE = new Groups_RolesTable();

	public final Column<Groups_RolesTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_RolesTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_RolesTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_RolesTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_RolesTable, Boolean> ctChangeType = createColumn(
		"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private Groups_RolesTable() {
		super("Groups_Roles", Groups_RolesTable::new);
	}

}