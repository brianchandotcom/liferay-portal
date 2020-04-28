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
 * The table class for the &quot;UserGroupGroupRole&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupGroupRole
 * @generated
 */
public class UserGroupGroupRoleTable
	extends BaseTable<UserGroupGroupRoleTable> {

	public static final UserGroupGroupRoleTable INSTANCE =
		new UserGroupGroupRoleTable();

	public final Column<UserGroupGroupRoleTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserGroupGroupRoleTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserGroupGroupRoleTable, Long> userGroupGroupRoleId =
		createColumn(
			"userGroupGroupRoleId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<UserGroupGroupRoleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserGroupGroupRoleTable, Long> userGroupId =
		createColumn(
			"userGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserGroupGroupRoleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserGroupGroupRoleTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private UserGroupGroupRoleTable() {
		super("UserGroupGroupRole", UserGroupGroupRoleTable::new);
	}

}