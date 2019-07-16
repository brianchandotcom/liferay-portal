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

package com.liferay.document.library.opener.internal.upgrade.v1_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLOpenerFileEntryReferenceTable {

	public static final String TABLE_NAME = "DLOpenerFileEntryReference";

	public static final Object[][] TABLE_COLUMNS = {
		{"dlOpenerFileEntryReferenceId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"referenceKey", Types.VARCHAR}, {"referenceType", Types.VARCHAR},
		{"fileEntryId", Types.BIGINT}, {"type_", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("dlOpenerFileEntryReferenceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("referenceKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("referenceType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table DLOpenerFileEntryReference (dlOpenerFileEntryReferenceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,referenceKey VARCHAR(75) null,referenceType VARCHAR(75) null,fileEntryId LONG,type_ INTEGER)";

	public static final String TABLE_SQL_DROP =
"drop table DLOpenerFileEntryReference";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_54148667 on DLOpenerFileEntryReference (fileEntryId)",
		"create unique index IX_6B9E4A66 on DLOpenerFileEntryReference (referenceType[$COLUMN_LENGTH:75$], fileEntryId)"
	};

}