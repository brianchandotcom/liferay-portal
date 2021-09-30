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

package com.liferay.marketplace.internal.upgrade.v2_0_3.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Tina Tian
 * @generated
 */
public class AppTable {

	public static final String TABLE_NAME = "Marketplace_App";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"appId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"remoteAppId", Types.BIGINT},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"category", Types.VARCHAR}, {"iconURL", Types.VARCHAR},
		{"version", Types.VARCHAR}, {"required", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("appId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("remoteAppId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("category", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("iconURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("required", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table Marketplace_App (uuid_ VARCHAR(75) null,appId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,remoteAppId LONG,title VARCHAR(255) null,description STRING null,category VARCHAR(75) null,iconURL STRING null,version VARCHAR(75) null,required BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Marketplace_App";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}