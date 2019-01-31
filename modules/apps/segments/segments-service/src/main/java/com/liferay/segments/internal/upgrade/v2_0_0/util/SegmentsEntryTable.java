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

package com.liferay.segments.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Tina Tian
 * @generated
 */
public class SegmentsEntryTable {

	public static final String TABLE_NAME = "SegmentsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"segmentsEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR},
		{"description_", Types.VARCHAR},
		{"active_", Types.BOOLEAN},
		{"criteria", Types.CLOB},
		{"key_", Types.VARCHAR},
		{"source", Types.VARCHAR},
		{"type_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("segmentsEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("criteria", Types.CLOB);

TABLE_COLUMNS_MAP.put("key_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("source", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table SegmentsEntry (segmentsEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,name STRING null,description_ STRING null,active_ BOOLEAN,criteria TEXT null,key_ VARCHAR(75) null,source VARCHAR(75) null,type_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table SegmentsEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B4410600 on SegmentsEntry (active_, source[$COLUMN_LENGTH:75$])",
		"create index IX_5BFEEA84 on SegmentsEntry (active_, type_[$COLUMN_LENGTH:75$])",
		"create index IX_6A43394D on SegmentsEntry (groupId, active_, type_[$COLUMN_LENGTH:75$], source[$COLUMN_LENGTH:75$])",
		"create unique index IX_E72E3826 on SegmentsEntry (groupId, key_[$COLUMN_LENGTH:75$])",
		"create index IX_7BB6BCA6 on SegmentsEntry (groupId, type_[$COLUMN_LENGTH:75$], active_)",
		"create index IX_90AB04A7 on SegmentsEntry (source[$COLUMN_LENGTH:75$])",
		"create index IX_5296FAFD on SegmentsEntry (type_[$COLUMN_LENGTH:75$])"
	};

}