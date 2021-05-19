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

package com.liferay.portal.upgrade.v7_4_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryTable {

	public static final String TABLE_NAME = "AssetEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"entryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"classUuid", Types.VARCHAR},
		{"classTypeId", Types.BIGINT}, {"listable", Types.BOOLEAN},
		{"visible", Types.BOOLEAN}, {"startDate", Types.TIMESTAMP},
		{"endDate", Types.TIMESTAMP}, {"publishDate", Types.TIMESTAMP},
		{"expirationDate", Types.TIMESTAMP}, {"mimeType", Types.VARCHAR},
		{"title", Types.CLOB}, {"description", Types.CLOB},
		{"summary", Types.CLOB}, {"url", Types.VARCHAR},
		{"layoutUuid", Types.VARCHAR}, {"height", Types.INTEGER},
		{"width", Types.INTEGER}, {"priority", Types.DOUBLE}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("classTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("listable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("visible", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("publishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.CLOB);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("summary", Types.CLOB);

TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("height", Types.INTEGER);

TABLE_COLUMNS_MAP.put("width", Types.INTEGER);

TABLE_COLUMNS_MAP.put("priority", Types.DOUBLE);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,entryId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,classUuid VARCHAR(75) null,classTypeId LONG,listable BOOLEAN,visible BOOLEAN,startDate DATE null,endDate DATE null,publishDate DATE null,expirationDate DATE null,mimeType VARCHAR(75) null,title TEXT null,description TEXT null,summary TEXT null,url STRING null,layoutUuid VARCHAR(75) null,height INTEGER,width INTEGER,priority DOUBLE,primary key (entryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table AssetEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_7BF8337B on AssetEntry (classNameId, classPK, ctCollectionId)",
		"create index IX_25F682BE on AssetEntry (companyId, ctCollectionId)",
		"create index IX_E504D126 on AssetEntry (ctCollectionId)",
		"create index IX_8839F457 on AssetEntry (expirationDate, ctCollectionId)",
		"create index IX_B516ADB0 on AssetEntry (groupId, classNameId, publishDate, expirationDate, ctCollectionId)",
		"create index IX_A62EE954 on AssetEntry (groupId, classNameId, visible, ctCollectionId)",
		"create index IX_683ADC7F on AssetEntry (groupId, classUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_D5B55D40 on AssetEntry (groupId, ctCollectionId)",
		"create index IX_5B55565F on AssetEntry (layoutUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_788964E3 on AssetEntry (publishDate, ctCollectionId)",
		"create index IX_5B6AC3B8 on AssetEntry (visible, ctCollectionId)"
	};

}