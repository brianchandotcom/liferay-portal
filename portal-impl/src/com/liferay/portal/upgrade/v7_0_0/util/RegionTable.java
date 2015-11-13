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

package com.liferay.portal.upgrade.v7_0_0.util;

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
		{"mvccVersion", Types.BIGINT},
		{"regionId", Types.BIGINT},
		{"countryId", Types.BIGINT},
		{"regionCode", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("regionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("countryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("regionCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table Region (mvccVersion LONG default 0,regionId LONG not null primary key,countryId LONG,regionCode VARCHAR(75) null,name VARCHAR(75) null,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Region";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_2D9A426F on Region (active_)",
		"create index IX_11FB3E42 on Region (countryId, active_)",
		"create unique index IX_A2635F5C on Region (countryId, regionCode[$COLUMN_LENGTH:75$])"
	};

}