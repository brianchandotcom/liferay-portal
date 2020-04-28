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
 * The table class for the &quot;UserGroups_Teams&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Team
 * @see UserGroup
 * @generated
 */
public class UserGroups_TeamsTable extends BaseTable<UserGroups_TeamsTable> {

	public static final UserGroups_TeamsTable INSTANCE =
		new UserGroups_TeamsTable();

	public final Column<UserGroups_TeamsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserGroups_TeamsTable, Long> teamId = createColumn(
		"teamId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserGroups_TeamsTable, Long> userGroupId = createColumn(
		"userGroupId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserGroups_TeamsTable, Long> ctCollectionId =
		createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserGroups_TeamsTable, Boolean> ctChangeType =
		createColumn(
			"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private UserGroups_TeamsTable() {
		super("UserGroups_Teams", UserGroups_TeamsTable::new);
	}

}