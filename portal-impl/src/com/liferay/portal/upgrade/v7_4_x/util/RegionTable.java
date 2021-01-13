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

package com.liferay.portal.upgrade.v7_4_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class RegionTable {

	public static final String TABLE_NAME = "Region";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"defaultLanguageId", Types.VARCHAR}, {"regionId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"countryId", Types.BIGINT},
		{"active_", Types.BOOLEAN}, {"name", Types.VARCHAR},
		{"position", Types.DOUBLE}, {"regionCode", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("defaultLanguageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("regionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("position", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("regionCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table Region (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,defaultLanguageId VARCHAR(75) null,regionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,countryId LONG,active_ BOOLEAN,name VARCHAR(75) null,position DOUBLE,regionCode VARCHAR(75) null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Region";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_2D9A426F on Region (active_)",
		"create index IX_11FB3E42 on Region (countryId, active_)",
		"create unique index IX_A2635F5C on Region (countryId, regionCode[$COLUMN_LENGTH:75$])",
		"create index IX_60C0214E on Region (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}