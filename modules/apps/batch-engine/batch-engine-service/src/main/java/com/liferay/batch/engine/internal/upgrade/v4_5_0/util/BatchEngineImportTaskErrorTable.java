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

package com.liferay.batch.engine.internal.upgrade.v4_5_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BatchEngineImportTaskErrorTable {

	public static final String TABLE_NAME = "BatchEngineImportTaskError";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"batchEngineImportTaskErrorId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"batchEngineImportTaskId", Types.BIGINT}, {"item", Types.CLOB},
		{"itemIndex", Types.INTEGER}, {"message", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("batchEngineImportTaskErrorId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("batchEngineImportTaskId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("item", Types.CLOB);

TABLE_COLUMNS_MAP.put("itemIndex", Types.INTEGER);

TABLE_COLUMNS_MAP.put("message", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE =
"create table BatchEngineImportTaskError (mvccVersion LONG default 0 not null,batchEngineImportTaskErrorId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,batchEngineImportTaskId LONG,item TEXT null,itemIndex INTEGER,message TEXT null)";

	public static final String TABLE_SQL_DROP =
"drop table BatchEngineImportTaskError";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_863EDEA9 on BatchEngineImportTaskError (batchEngineImportTaskId)"
	};

}