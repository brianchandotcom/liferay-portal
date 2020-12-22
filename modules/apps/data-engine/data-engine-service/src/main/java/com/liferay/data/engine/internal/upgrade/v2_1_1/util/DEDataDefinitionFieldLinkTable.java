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

package com.liferay.data.engine.internal.upgrade.v2_1_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DEDataDefinitionFieldLinkTable {

	public static final String TABLE_NAME = "DEDataDefinitionFieldLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"deDataDefinitionFieldLinkId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"ddmStructureId", Types.BIGINT}, {"fieldName", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("deDataDefinitionFieldLinkId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ddmStructureId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fieldName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DEDataDefinitionFieldLink (uuid_ VARCHAR(75) null,deDataDefinitionFieldLinkId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,ddmStructureId LONG,fieldName VARCHAR(75) null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table DEDataDefinitionFieldLink";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_2CEE588F on DEDataDefinitionFieldLink (classNameId, classPK, ddmStructureId, fieldName[$COLUMN_LENGTH:75$])",
		"create index IX_7BAE5B6E on DEDataDefinitionFieldLink (classNameId, ddmStructureId, fieldName[$COLUMN_LENGTH:75$])",
		"create index IX_E931B304 on DEDataDefinitionFieldLink (ddmStructureId, fieldName[$COLUMN_LENGTH:75$])",
		"create index IX_5145BB70 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_AAE65DF2 on DEDataDefinitionFieldLink (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}