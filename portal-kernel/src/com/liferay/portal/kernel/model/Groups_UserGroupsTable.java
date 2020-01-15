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
 * The table class for the Groups_UserGroups.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class Groups_UserGroupsTable extends Table<Groups_UserGroupsTable> {

	public static final Groups_UserGroupsTable INSTANCE =
		new Groups_UserGroupsTable();

	public final Column<Groups_UserGroupsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<Groups_UserGroupsTable, Long> groupId = createColumn(
		"groupId", Long.class, Types.BIGINT);
	public final Column<Groups_UserGroupsTable, Long> userGroupId =
		createColumn("userGroupId", Long.class, Types.BIGINT);

	private Groups_UserGroupsTable() {
		super("Groups_UserGroups", Groups_UserGroupsTable::new);
	}

}