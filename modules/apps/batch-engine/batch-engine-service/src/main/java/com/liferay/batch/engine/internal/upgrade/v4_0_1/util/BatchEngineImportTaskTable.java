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

package com.liferay.batch.engine.internal.upgrade.v4_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BatchEngineImportTaskTable {

	public static final String TABLE_NAME = "BatchEngineImportTask";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"batchEngineImportTaskId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"batchSize", Types.BIGINT},
		{"callbackURL", Types.VARCHAR}, {"className", Types.VARCHAR},
		{"content", Types.BLOB}, {"contentType", Types.VARCHAR},
		{"endTime", Types.TIMESTAMP}, {"errorMessage", Types.VARCHAR},
		{"executeStatus", Types.VARCHAR}, {"fieldNameMapping", Types.CLOB},
		{"operation", Types.VARCHAR}, {"parameters", Types.CLOB},
		{"startTime", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("batchEngineImportTaskId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("batchSize", Types.BIGINT);

TABLE_COLUMNS_MAP.put("callbackURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("content", Types.BLOB);

TABLE_COLUMNS_MAP.put("contentType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("endTime", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("errorMessage", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("executeStatus", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fieldNameMapping", Types.CLOB);

TABLE_COLUMNS_MAP.put("operation", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("parameters", Types.CLOB);

TABLE_COLUMNS_MAP.put("startTime", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table BatchEngineImportTask (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,batchEngineImportTaskId LONG not null primary key,companyId LONG,userId LONG,createDate DATE null,modifiedDate DATE null,batchSize LONG,callbackURL VARCHAR(75) null,className VARCHAR(255) null,content BLOB,contentType VARCHAR(75) null,endTime DATE null,errorMessage VARCHAR(1000) null,executeStatus VARCHAR(75) null,fieldNameMapping TEXT null,operation VARCHAR(75) null,parameters TEXT null,startTime DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table BatchEngineImportTask";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_ABC8050B on BatchEngineImportTask (executeStatus[$COLUMN_LENGTH:75$])",
		"create index IX_BE725720 on BatchEngineImportTask (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}