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

package com.liferay.microblogs.internal.upgrade.v1_0_2.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class MicroblogsEntryTable {

	public static final String TABLE_NAME = "MicroblogsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"microblogsEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"creatorClassNameId", Types.BIGINT}, {"creatorClassPK", Types.BIGINT},
		{"content", Types.VARCHAR}, {"type_", Types.INTEGER},
		{"parentMicroblogsEntryId", Types.BIGINT},
		{"socialRelationType", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("microblogsEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("creatorClassNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("creatorClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("content", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("parentMicroblogsEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("socialRelationType", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table MicroblogsEntry (microblogsEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,creatorClassNameId LONG,creatorClassPK LONG,content STRING null,type_ INTEGER,parentMicroblogsEntryId LONG,socialRelationType INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table MicroblogsEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_1D8CE137 on MicroblogsEntry (companyId, creatorClassNameId, creatorClassPK, type_)",
		"create index IX_CA299EF2 on MicroblogsEntry (companyId, creatorClassNameId, type_)",
		"create index IX_14ACFA9 on MicroblogsEntry (creatorClassNameId, creatorClassPK, type_)",
		"create index IX_6AA6B164 on MicroblogsEntry (creatorClassNameId, type_)",
		"create index IX_6BD29B9C on MicroblogsEntry (type_, parentMicroblogsEntryId)",
		"create index IX_8F04FC09 on MicroblogsEntry (userId, createDate, type_, socialRelationType)",
		"create index IX_92BA6F0 on MicroblogsEntry (userId, type_)"
	};

}