/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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