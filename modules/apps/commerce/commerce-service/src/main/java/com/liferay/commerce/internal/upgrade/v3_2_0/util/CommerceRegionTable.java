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

package com.liferay.commerce.internal.upgrade.v3_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CommerceRegionTable {

	public static final String TABLE_NAME = "CommerceRegion";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"commerceRegionId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"commerceCountryId", Types.BIGINT},
		{"name", Types.VARCHAR}, {"code_", Types.VARCHAR},
		{"priority", Types.DOUBLE}, {"active_", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("commerceRegionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("commerceCountryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("code_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CommerceRegion (uuid_ VARCHAR(75) null,commerceRegionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,commerceCountryId LONG,name VARCHAR(75) null,code_ VARCHAR(75) null,priority DOUBLE,active_ BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table CommerceRegion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_49C93338 on CommerceRegion (commerceCountryId, active_)",
		"create unique index IX_2D8BEE31 on CommerceRegion (commerceCountryId, code_[$COLUMN_LENGTH:75$])",
		"create index IX_3BC85C89 on CommerceRegion (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}