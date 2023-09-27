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
public class UserTrackerPathTable {

	public static final String TABLE_NAME = "UserTrackerPath";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"userTrackerPathId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userTrackerId", Types.BIGINT},
		{"path_", Types.VARCHAR}, {"pathDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userTrackerPathId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userTrackerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("path_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("pathDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table UserTrackerPath (mvccVersion LONG default 0 not null,userTrackerPathId LONG not null primary key,companyId LONG,userTrackerId LONG,path_ STRING null,pathDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table UserTrackerPath";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_14D8BCC0 on UserTrackerPath (userTrackerId)"
	};

}