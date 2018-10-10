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

package com.liferay.asset.display.page.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetDisplayPageEntryTable {

	public static final String TABLE_NAME = "AssetDisplayPageEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"assetDisplayPageEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"layoutPageTemplateEntryId", Types.BIGINT},
		{"type_", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assetDisplayPageEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("layoutPageTemplateEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table AssetDisplayPageEntry (uuid_ VARCHAR(75) null,assetDisplayPageEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,layoutPageTemplateEntryId LONG,type_ INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table AssetDisplayPageEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_31FA120C on AssetDisplayPageEntry (classNameId, classPK)",
		"create unique index IX_A21FC9A8 on AssetDisplayPageEntry (groupId, classNameId, classPK)",
		"create index IX_BFB8A913 on AssetDisplayPageEntry (layoutPageTemplateEntryId)",
		"create index IX_1DA6952B on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_DB986A6D on AssetDisplayPageEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}