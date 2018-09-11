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
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class UserTrackerTable {

	public static final String TABLE_NAME = "UserTracker";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"userTrackerId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"modifiedDate", Types.TIMESTAMP},
		{"sessionId", Types.VARCHAR},
		{"remoteAddr", Types.VARCHAR},
		{"remoteHost", Types.VARCHAR},
		{"userAgent", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userTrackerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("sessionId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("remoteAddr", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("remoteHost", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("userAgent", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table UserTracker (mvccVersion LONG default 0 not null,userTrackerId LONG not null primary key,companyId LONG,userId LONG,modifiedDate DATE null,sessionId VARCHAR(200) null,remoteAddr VARCHAR(75) null,remoteHost VARCHAR(75) null,userAgent VARCHAR(200) null)";

	public static final String TABLE_SQL_DROP = "drop table UserTracker";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_29BA1CF5 on UserTracker (companyId)",
		"create index IX_46B0AE8E on UserTracker (sessionId[$COLUMN_LENGTH:200$])",
		"create index IX_E4EFBA8D on UserTracker (userId)"
	};

}