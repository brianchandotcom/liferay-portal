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

package com.liferay.layout.seo.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutSEOEntryTable {

	public static final String TABLE_NAME = "LayoutSEOEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"layoutSEOEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"privateLayout", Types.BOOLEAN},
		{"layoutId", Types.BIGINT}, {"canonicalURLEnabled", Types.BOOLEAN},
		{"canonicalURL", Types.VARCHAR},
		{"openGraphTitleEnabled", Types.BOOLEAN},
		{"openGraphTitle", Types.VARCHAR},
		{"openGraphDescriptionEnabled", Types.BOOLEAN},
		{"openGraphDescription", Types.VARCHAR},
		{"openGraphImageFileEntryId", Types.BIGINT},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutSEOEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("layoutId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("canonicalURLEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("canonicalURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("openGraphTitleEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("openGraphTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("openGraphDescriptionEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("openGraphDescription", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("openGraphImageFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table LayoutSEOEntry (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,layoutSEOEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,layoutId LONG,canonicalURLEnabled BOOLEAN,canonicalURL STRING null,openGraphTitleEnabled BOOLEAN,openGraphTitle STRING null,openGraphDescriptionEnabled BOOLEAN,openGraphDescription STRING null,openGraphImageFileEntryId LONG,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table LayoutSEOEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_371B1831 on LayoutSEOEntry (groupId, privateLayout, layoutId)",
		"create index IX_D9211E39 on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_429DDEFB on LayoutSEOEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}