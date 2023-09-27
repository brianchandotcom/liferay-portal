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
public class AnnouncementsFlagTable {

	public static final String TABLE_NAME = "AnnouncementsFlag";

	public static final Object[][] TABLE_COLUMNS = {
		{"flagId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"entryId", Types.BIGINT}, {"value", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("flagId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("value", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table AnnouncementsFlag (flagId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,entryId LONG,value INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table AnnouncementsFlag";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_9C7EB9F on AnnouncementsFlag (entryId)",
		"create unique index IX_4539A99C on AnnouncementsFlag (userId, entryId, value)"
	};

}