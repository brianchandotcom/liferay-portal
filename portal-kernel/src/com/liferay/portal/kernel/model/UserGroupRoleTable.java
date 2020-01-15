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
import com.liferay.petra.sql.dsl.Table;

import java.sql.Types;

/**
 * The table class for the UserGroupRole.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserGroupRoleTable extends Table<UserGroupRoleTable> {

	public static final UserGroupRoleTable INSTANCE = new UserGroupRoleTable();

	public final Column<UserGroupRoleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<UserGroupRoleTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<UserGroupRoleTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<UserGroupRoleTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT);
	public final Column<UserGroupRoleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);

	private UserGroupRoleTable() {
		super("UserGroupRole", UserGroupRoleTable::new);
	}

}