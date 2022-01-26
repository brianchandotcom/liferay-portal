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

package com.liferay.dynamic.data.mapping.internal.upgrade.v5_1_3.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMFieldTable {

	public static final String TABLE_NAME = "DDMField";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"fieldId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"parentFieldId", Types.BIGINT}, {"storageId", Types.BIGINT},
		{"structureVersionId", Types.BIGINT}, {"fieldName", Types.CLOB},
		{"fieldType", Types.VARCHAR}, {"instanceId", Types.VARCHAR},
		{"localizable", Types.BOOLEAN}, {"priority", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fieldId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentFieldId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("storageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("structureVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fieldName", Types.CLOB);

TABLE_COLUMNS_MAP.put("fieldType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("instanceId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("localizable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table DDMField (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,fieldId LONG not null,companyId LONG,parentFieldId LONG,storageId LONG,structureVersionId LONG,fieldName TEXT null,fieldType VARCHAR(255) null,instanceId VARCHAR(75) null,localizable BOOLEAN,priority INTEGER,primary key (fieldId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table DDMField";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5378BAAD on DDMField (companyId, fieldType[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create index IX_582EBFF1 on DDMField (storageId, ctCollectionId)",
		"create unique index IX_1BB20E75 on DDMField (storageId, instanceId[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_5C0B8AE5 on DDMField (structureVersionId, ctCollectionId)"
	};

}