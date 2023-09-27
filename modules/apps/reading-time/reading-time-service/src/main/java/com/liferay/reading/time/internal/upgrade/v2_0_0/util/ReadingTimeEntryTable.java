/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.reading.time.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ReadingTimeEntryTable {

	public static final String TABLE_NAME = "ReadingTimeEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"readingTimeEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"readingTime", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("readingTimeEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("readingTime", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table ReadingTimeEntry (uuid_ VARCHAR(75) null,readingTimeEntryId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,readingTime LONG)";

	public static final String TABLE_SQL_DROP = "drop table ReadingTimeEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_73B13580 on ReadingTimeEntry (groupId, classNameId, classPK)",
		"create index IX_29FACA53 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_D647C995 on ReadingTimeEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}