/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;UserIdMapper&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapper
 * @generated
 */
public class UserIdMapperTable extends BaseTable<UserIdMapperTable> {

	public static final UserIdMapperTable INSTANCE = new UserIdMapperTable();

	public final Column<UserIdMapperTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserIdMapperTable, Long> userIdMapperId = createColumn(
		"userIdMapperId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserIdMapperTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserIdMapperTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserIdMapperTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserIdMapperTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserIdMapperTable, String> externalUserId =
		createColumn(
			"externalUserId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private UserIdMapperTable() {
		super("UserIdMapper", UserIdMapperTable::new);
	}

}