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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class ExpandoValueTable {

	public static final String TABLE_NAME = "ExpandoValue";

	public static final Object[][] TABLE_COLUMNS = {
		{"valueId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"tableId", Types.BIGINT},
		{"columnId", Types.BIGINT},
		{"rowId_", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"data_", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("valueId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("tableId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("columnId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("rowId_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("data_", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table ExpandoValue (valueId LONG not null primary key,companyId LONG,tableId LONG,columnId LONG,rowId_ LONG,classNameId LONG,classPK LONG,data_ TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table ExpandoValue";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B29FEF17 on ExpandoValue (classNameId, classPK)",
		"create unique index IX_9DDD21E5 on ExpandoValue (columnId, rowId_)",
		"create index IX_9112A7A0 on ExpandoValue (rowId_)",
		"create index IX_1BD3F4C on ExpandoValue (tableId, classPK)",
		"create unique index IX_D27B03E7 on ExpandoValue (tableId, columnId, classPK)",
		"create index IX_B71E92D5 on ExpandoValue (tableId, rowId_)"
	};

}