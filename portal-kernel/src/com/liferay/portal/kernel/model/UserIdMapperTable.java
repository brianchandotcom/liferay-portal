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
 * The table class for the UserIdMapper.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class UserIdMapperTable extends Table<UserIdMapperTable> {

	public static final UserIdMapperTable INSTANCE = new UserIdMapperTable();

	public final Column<UserIdMapperTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT);
	public final Column<UserIdMapperTable, Long> userIdMapperId = createColumn(
		"userIdMapperId", Long.class, Types.BIGINT);
	public final Column<UserIdMapperTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT);
	public final Column<UserIdMapperTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT);
	public final Column<UserIdMapperTable, String> type = createColumn(
		"type_", String.class, Types.VARCHAR);
	public final Column<UserIdMapperTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR);
	public final Column<UserIdMapperTable, String> externalUserId =
		createColumn("externalUserId", String.class, Types.VARCHAR);

	private UserIdMapperTable() {
		super("UserIdMapper", UserIdMapperTable::new);
	}

}