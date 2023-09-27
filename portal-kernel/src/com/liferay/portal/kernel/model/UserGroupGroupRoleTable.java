/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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