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

package com.liferay.portal.upgrade.v7_2_x.util;

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
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"headId", Types.BIGINT}, {"head", Types.BOOLEAN},
		{"plid", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"parentPlid", Types.BIGINT},
		{"privateLayout", Types.BOOLEAN}, {"layoutId", Types.BIGINT},
		{"parentLayoutId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"name", Types.VARCHAR},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"keywords", Types.VARCHAR}, {"robots", Types.VARCHAR},
		{"type_", Types.VARCHAR}, {"typeSettings", Types.CLOB},
		{"hidden_", Types.BOOLEAN}, {"system_", Types.BOOLEAN},
		{"friendlyURL", Types.VARCHAR}, {"iconImageId", Types.BIGINT},
		{"themeId", Types.VARCHAR}, {"colorSchemeId", Types.VARCHAR},
		{"css", Types.CLOB}, {"priority", Types.INTEGER},
		{"layoutPrototypeUuid", Types.VARCHAR},
		{"layoutPrototypeLinkEnabled", Types.BOOLEAN},
		{"sourcePrototypeLayoutUuid", Types.VARCHAR},
		{"publishDate", Types.TIMESTAMP}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("headId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("head", Types.BOOLEAN);

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

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

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

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

TABLE_COLUMNS_MAP.put("layoutPrototypeUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutPrototypeLinkEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("sourcePrototypeLayoutUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("publishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table Layout (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,headId LONG,head BOOLEAN,plid LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentPlid LONG,privateLayout BOOLEAN,layoutId LONG,parentLayoutId LONG,classNameId LONG,classPK LONG,name STRING null,title STRING null,description STRING null,keywords STRING null,robots STRING null,type_ VARCHAR(75) null,typeSettings TEXT null,hidden_ BOOLEAN,system_ BOOLEAN,friendlyURL VARCHAR(255) null,iconImageId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,css TEXT null,priority INTEGER,layoutPrototypeUuid VARCHAR(75) null,layoutPrototypeLinkEnabled BOOLEAN,sourcePrototypeLayoutUuid VARCHAR(75) null,publishDate DATE null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table Layout";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B8E1E6E5 on Layout (classNameId, classPK)",
		"create index IX_881EABCB on Layout (companyId, layoutPrototypeUuid[$COLUMN_LENGTH:75$])",
		"create unique index IX_C1143B45 on Layout (groupId, privateLayout, friendlyURL[$COLUMN_LENGTH:255$], head)",
		"create unique index IX_D2DE1750 on Layout (groupId, privateLayout, layoutId, head)",
		"create index IX_7399B71E on Layout (groupId, privateLayout, parentLayoutId, priority)",
		"create index IX_8CE8C0D9 on Layout (groupId, privateLayout, sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$])",
		"create index IX_1A1B61D2 on Layout (groupId, privateLayout, type_[$COLUMN_LENGTH:75$])",
		"create index IX_6EDC627B on Layout (groupId, type_[$COLUMN_LENGTH:75$])",
		"create unique index IX_9D3FD85F on Layout (headId)",
		"create index IX_23922F7D on Layout (iconImageId)",
		"create index IX_B529BFD3 on Layout (layoutPrototypeUuid[$COLUMN_LENGTH:75$])",
		"create index IX_1D4DCAA5 on Layout (parentPlid)",
		"create index IX_3BC009C0 on Layout (privateLayout, iconImageId)",
		"create index IX_39A18ECC on Layout (sourcePrototypeLayoutUuid[$COLUMN_LENGTH:75$])",
		"create index IX_2CE4BE84 on Layout (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_92B7E1CB on Layout (uuid_[$COLUMN_LENGTH:75$], groupId, privateLayout, head)"
	};

}