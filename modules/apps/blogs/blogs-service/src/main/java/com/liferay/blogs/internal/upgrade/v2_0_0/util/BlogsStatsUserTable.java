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

package com.liferay.blogs.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BlogsStatsUserTable {

	public static final String TABLE_NAME = "BlogsStatsUser";

	public static final Object[][] TABLE_COLUMNS = {
		{"statsUserId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"entryCount", Types.INTEGER},
		{"lastPostDate", Types.TIMESTAMP},
		{"ratingsTotalEntries", Types.INTEGER},
		{"ratingsTotalScore", Types.DOUBLE},
		{"ratingsAverageScore", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("statsUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("entryCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPostDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("ratingsTotalEntries", Types.INTEGER);

TABLE_COLUMNS_MAP.put("ratingsTotalScore", Types.DOUBLE);

TABLE_COLUMNS_MAP.put("ratingsAverageScore", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE = "create table BlogsStatsUser (statsUserId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,entryCount INTEGER,lastPostDate DATE null,ratingsTotalEntries INTEGER,ratingsTotalScore DOUBLE,ratingsAverageScore DOUBLE)";

	public static final String TABLE_SQL_DROP = "drop table BlogsStatsUser";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_90CDA39A on BlogsStatsUser (companyId, entryCount)",
		"create index IX_28C78D5C on BlogsStatsUser (groupId, entryCount)",
		"create unique index IX_82254C25 on BlogsStatsUser (groupId, userId)",
		"create index IX_507BA031 on BlogsStatsUser (userId, lastPostDate)"
	};

}