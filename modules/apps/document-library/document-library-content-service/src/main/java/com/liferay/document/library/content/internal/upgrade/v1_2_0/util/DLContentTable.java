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

package com.liferay.document.library.content.internal.upgrade.v1_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLContentTable {

	public static final String TABLE_NAME = "DLContent";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"contentId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"repositoryId", Types.BIGINT}, {"path_", Types.VARCHAR},
		{"version", Types.VARCHAR}, {"data_", Types.BLOB},
		{"size_", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("contentId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("path_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("data_", Types.BLOB);

TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE =
"create table DLContent (mvccVersion LONG default 0 not null,contentId LONG not null primary key,groupId LONG,companyId LONG,repositoryId LONG,path_ VARCHAR(255) null,version VARCHAR(75) null,data_ BLOB,size_ LONG)";

	public static final String TABLE_SQL_DROP = "drop table DLContent";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_FDD1AAA8 on DLContent (companyId, repositoryId, path_[$COLUMN_LENGTH:255$], version[$COLUMN_LENGTH:75$])"
	};

}