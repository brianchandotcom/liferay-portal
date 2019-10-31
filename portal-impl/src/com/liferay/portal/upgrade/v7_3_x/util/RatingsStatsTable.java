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

package com.liferay.portal.upgrade.v7_3_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class RatingsStatsTable {

	public static final String TABLE_NAME = "RatingsStats";

	public static final Object[][] TABLE_COLUMNS = {
		{"statsId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"totalEntries", Types.INTEGER}, {"totalScore", Types.DOUBLE},
		{"averageScore", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("statsId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("totalEntries", Types.INTEGER);

TABLE_COLUMNS_MAP.put("totalScore", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("averageScore", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE =
"create table RatingsStats (statsId LONG not null primary key,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,totalEntries INTEGER,totalScore DOUBLE,averageScore DOUBLE)";

	public static final String TABLE_SQL_DROP = "drop table RatingsStats";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_A6E99284 on RatingsStats (classNameId, classPK)",
		"create index IX_5EC6007D on RatingsStats (classNameId, createDate)",
		"create index IX_11A5584A on RatingsStats (classNameId, modifiedDate)"
	};

}