/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;Users_Teams&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see Team
 * @see User
 * @generated
 */
public class Users_TeamsTable extends BaseTable<Users_TeamsTable> {

	public static final Users_TeamsTable INSTANCE = new Users_TeamsTable();

	public final Column<Users_TeamsTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Users_TeamsTable, Long> teamId = createColumn(
		"teamId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Users_TeamsTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Users_TeamsTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<Users_TeamsTable, Boolean> ctChangeType = createColumn(
		"ctChangeType", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);

	private Users_TeamsTable() {
		super("Users_Teams", Users_TeamsTable::new);
	}

}