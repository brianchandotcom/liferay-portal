/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class MBStatsUserTable {

	public static final String TABLE_NAME = "MBStatsUser";

	public static final Object[][] TABLE_COLUMNS = {
		{"statsUserId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"messageCount", Types.INTEGER},
		{"lastPostDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("statsUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("messageCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPostDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table MBStatsUser (statsUserId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,messageCount INTEGER,lastPostDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table MBStatsUser";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_9168E2C9 on MBStatsUser (groupId, userId)",
		"create index IX_847F92B5 on MBStatsUser (userId)"
	};

}