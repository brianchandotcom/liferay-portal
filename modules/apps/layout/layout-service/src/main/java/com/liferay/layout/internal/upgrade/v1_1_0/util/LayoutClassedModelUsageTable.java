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

package com.liferay.layout.internal.upgrade.v1_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Pavel Savinov
 * @generated
 */
public class LayoutClassedModelUsageTable {

	public static final String TABLE_NAME = "LayoutClassedModelUsage";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"layoutClassedModelUsageId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"containerKey", Types.VARCHAR},
		{"containerType", Types.BIGINT}, {"plid", Types.BIGINT},
		{"type_", Types.INTEGER}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutClassedModelUsageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("containerKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("containerType", Types.BIGINT);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table LayoutClassedModelUsage (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,layoutClassedModelUsageId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,containerKey VARCHAR(200) null,containerType LONG,plid LONG,type_ INTEGER,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table LayoutClassedModelUsage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_FA38EE24 on LayoutClassedModelUsage (classNameId, classPK, containerKey[$COLUMN_LENGTH:200$], containerType, plid)",
		"create index IX_B041F1F5 on LayoutClassedModelUsage (classNameId, classPK, type_)",
		"create index IX_DF750659 on LayoutClassedModelUsage (containerKey[$COLUMN_LENGTH:200$], containerType, plid)",
		"create index IX_19448DD6 on LayoutClassedModelUsage (plid)",
		"create unique index IX_694CA341 on LayoutClassedModelUsage (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}