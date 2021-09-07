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
public class LayoutTable {

	public static final String TABLE_NAME = "Layout";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"plid", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"parentPlid", Types.BIGINT}, {"privateLayout", Types.BOOLEAN},
		{"layoutId", Types.BIGINT}, {"parentLayoutId", Types.BIGINT},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"name", Types.VARCHAR}, {"title", Types.CLOB},
		{"description", Types.CLOB}, {"keywords", Types.VARCHAR},
		{"robots", Types.VARCHAR}, {"type_", Types.VARCHAR},
		{"typeSettings", Types.CLOB}, {"hidden_", Types.BOOLEAN},
		{"system_", Types.BOOLEAN}, {"friendlyURL", Types.VARCHAR},
		{"iconImageId", Types.BIGINT}, {"themeId", Types.VARCHAR},
		{"colorSchemeId", Types.VARCHAR}, {"styleBookEntryId", Types.BIGINT},
		{"css", Types.CLOB}, {"priority", Types.INTEGER},
		{"masterLayoutPlid", Types.BIGINT},
		{"layoutPrototypeUuid", Types.VARCHAR},
		{"layoutPrototypeLinkEnabled", Types.BOOLEAN},
		{"sourcePrototypeLayoutUuid", Types.VARCHAR},
		{"publishDate", Types.TIMESTAMP}, {"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentPlid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("layoutId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentLayoutId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.CLOB);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("keywords", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("robots", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("typeSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("hidden_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("friendlyURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("iconImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("themeId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("colorSchemeId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("styleBookEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

TABLE_COLUMNS_MAP.put("masterLayoutPlid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("layoutPrototypeUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutPrototypeLinkEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("sourcePrototypeLayoutUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("publishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table Layout (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,plid LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentPlid LONG,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,classNameId LONG,classPK LONG,name STRING null,title TEXT null,description TEXT null,keywords STRING null,robots STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,system_ BOOLEAN,friendlyURL VARCHAR(255) null,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,styleBookEntryId LONG,css TEXT null,priority INTEGER,masterLayoutPlid LONG,layoutPrototypeUuid VARCHAR(75) null,layoutPrototypeLinkEnabled BOOLEAN,sourcePrototypeLayoutUuid VARCHAR(75) null,publishDate DATE null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (plid, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table Layout";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_31B45343 on Layout (classNameId, classPK, ctCollectionId)",
		"create index IX_4B906FF6 on Layout (companyId, ctCollectionId)",
		"create index IX_8F868C29 on Layout (companyId, layoutPrototypeUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_FD5AF6EE on Layout (ctCollectionId)",
		"create index IX_34D93878 on Layout (groupId, ctCollectionId)",
		"create index IX_12770E8F on Layout (groupId, masterLayoutPlid, ctCollectionId)",
		"create index IX_7BFE8B01 on Layout (groupId, privateLayout, ctCollectionId)",
		"create unique index IX_B556968F on Layout (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create unique index IX_CF5120DA on Layout (groupId, privateLayout, layoutId, ctCollectionId)",
		"create index IX_52D89564 on Layout (groupId, privateLayout, parentLayoutId, ctCollectionId)",
		"create index IX_1E4451FD on Layout (groupId, privateLayout, parentLayoutId, hidden_, ctCollectionId)",
		"create index IX_989E917C on Layout (groupId, privateLayout, parentLayoutId, priority, ctCollectionId)",
		"create index IX_18D0C537 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_A73CEAE7 on Layout (groupId, privateLayout, status, ctCollectionId)",
		"create index IX_A1FC5430 on Layout (groupId, privateLayout, type_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_94E0E2D9 on Layout (groupId, type_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_E7B06BDB on Layout (iconImageId, ctCollectionId)",
		"create index IX_11389031 on Layout (layoutPrototypeUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_7F60B703 on Layout (parentPlid, ctCollectionId)",
		"create index IX_C95F601E on Layout (privateLayout, iconImageId, ctCollectionId)",
		"create index IX_ED8D4D2A on Layout (sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_24AA0CE2 on Layout (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_5AA23582 on Layout (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_52D84D95 on Layout (uuid_[$COLUMN_LENGTH:75$], groupId, privateLayout, ctCollectionId)"
	};

}