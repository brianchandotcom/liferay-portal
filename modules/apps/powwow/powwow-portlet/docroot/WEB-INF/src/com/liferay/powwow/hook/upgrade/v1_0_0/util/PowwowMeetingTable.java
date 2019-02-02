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

package com.liferay.powwow.hook.upgrade.v1_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Tina Tian
 * @generated
 */
public class PowwowMeetingTable {

	public static final String TABLE_NAME = "PowwowMeeting";

	public static final Object[][] TABLE_COLUMNS = {
		{"powwowMeetingId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"powwowServerId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description_", Types.VARCHAR},
		{"providerType", Types.VARCHAR},
		{"providerTypeMetadata", Types.VARCHAR},
		{"languageId", Types.VARCHAR},
		{"calendarBookingId", Types.BIGINT},
		{"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("powwowMeetingId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("powwowServerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("providerType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("providerTypeMetadata", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("languageId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("calendarBookingId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table PowwowMeeting (powwowMeetingId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,powwowServerId LONG,name VARCHAR(75) null,description_ STRING null,providerType VARCHAR(75) null,providerTypeMetadata STRING null,languageId VARCHAR(75) null,calendarBookingId LONG,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table PowwowMeeting";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A9E3FA50 on PowwowMeeting (groupId)",
		"create index IX_790AD7D9 on PowwowMeeting (powwowServerId, status)",
		"create index IX_B1C56C80 on PowwowMeeting (status)",
		"create index IX_ADD9B0BA on PowwowMeeting (userId, status)"
	};

}