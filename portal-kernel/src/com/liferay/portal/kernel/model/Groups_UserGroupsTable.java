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
 * The table class for the &quot;Groups_UserGroups&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Group
 * @see UserGroup
 * @generated
 */
public class Groups_UserGroupsTable extends BaseTable<Groups_UserGroupsTable> {

	public static final Groups_UserGroupsTable INSTANCE =
		new Groups_UserGroupsTable();

	public final Column<Groups_UserGroupsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_UserGroupsTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_UserGroupsTable, Long> userGroupId =
		createColumn(
			"userGroupId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_UserGroupsTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Groups_UserGroupsTable, Boolean> ctChangeType =
		createColumn(
			"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private Groups_UserGroupsTable() {
		super("Groups_UserGroups", Groups_UserGroupsTable::new);
	}

}