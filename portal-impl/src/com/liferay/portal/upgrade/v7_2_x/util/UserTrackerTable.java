/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		{"mvccVersion", Types.BIGINT}, {"userTrackerId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"modifiedDate", Types.TIMESTAMP}, {"sessionId", Types.VARCHAR},
		{"remoteAddr", Types.VARCHAR}, {"remoteHost", Types.VARCHAR},
		{"userAgent", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

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
	public static final String TABLE_SQL_CREATE =
"create table UserTracker (mvccVersion LONG default 0 not null,userTrackerId LONG not null primary key,companyId LONG,userId LONG,modifiedDate DATE null,sessionId VARCHAR(200) null,remoteAddr VARCHAR(75) null,remoteHost VARCHAR(75) null,userAgent VARCHAR(200) null)";

	public static final String TABLE_SQL_DROP = "drop table UserTracker";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_29BA1CF5 on UserTracker (companyId)",
		"create index IX_46B0AE8E on UserTracker (sessionId[$COLUMN_LENGTH:200$])",
		"create index IX_E4EFBA8D on UserTracker (userId)"
	};

}