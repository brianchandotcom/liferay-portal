/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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