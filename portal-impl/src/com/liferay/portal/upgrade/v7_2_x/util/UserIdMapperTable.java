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

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Tina Tian
 * @generated
 */
public class UserIdMapperTable {

	public static final String TABLE_NAME = "UserIdMapper";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"userIdMapperId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"type_", Types.VARCHAR},
		{"description_", Types.VARCHAR},
		{"externalUserId", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userIdMapperId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalUserId", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table UserIdMapper (mvccVersion LONG default 0 not null,userIdMapperId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(75) null,description_ VARCHAR(75) null,externalUserId VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table UserIdMapper";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_41A32E0D on UserIdMapper (type_[$COLUMN_LENGTH:75$], externalUserId[$COLUMN_LENGTH:75$])",
		"create unique index IX_D1C44A6E on UserIdMapper (userId, type_[$COLUMN_LENGTH:75$])"
	};

}