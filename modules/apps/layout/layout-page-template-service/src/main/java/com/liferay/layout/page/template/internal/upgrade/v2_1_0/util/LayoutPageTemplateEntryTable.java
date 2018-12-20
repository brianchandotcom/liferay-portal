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

package com.liferay.layout.page.template.internal.upgrade.v2_1_0.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutPageTemplateEntryTable {

	public static final String TABLE_NAME = "LayoutPageTemplateEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"layoutPageTemplateEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"layoutPageTemplateCollectionId", Types.BIGINT},
		{"classNameId", Types.BIGINT},
		{"classTypeId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"type_", Types.INTEGER},
		{"previewFileEntryId", Types.BIGINT},
		{"defaultTemplate", Types.BOOLEAN},
		{"layoutPrototypeId", Types.BIGINT},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP},
		{"plid", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutPageTemplateEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("layoutPageTemplateCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("previewFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("defaultTemplate", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("layoutPrototypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table LayoutPageTemplateEntry (uuid_ VARCHAR(75) null,layoutPageTemplateEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,layoutPageTemplateCollectionId LONG,classNameId LONG,classTypeId LONG,name VARCHAR(75) null,type_ INTEGER,previewFileEntryId LONG,defaultTemplate BOOLEAN,layoutPrototypeId LONG,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,plid LONG)";

	public static final String TABLE_SQL_DROP = "drop table LayoutPageTemplateEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_957F6C5D on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, defaultTemplate, status)",
		"create index IX_E2488048 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, name[$COLUMN_LENGTH:75$], type_, status)",
		"create index IX_227636E7 on LayoutPageTemplateEntry (groupId, classNameId, classTypeId, type_, status)",
		"create index IX_1736F4A2 on LayoutPageTemplateEntry (groupId, classNameId, defaultTemplate)",
		"create index IX_CD171EDF on LayoutPageTemplateEntry (groupId, classNameId, type_, defaultTemplate)",
		"create index IX_4C3A286A on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, name[$COLUMN_LENGTH:75$], status)",
		"create index IX_A4733F6B on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, status)",
		"create index IX_4BCAC4B0 on LayoutPageTemplateEntry (groupId, layoutPageTemplateCollectionId, type_)",
		"create index IX_6120EE7E on LayoutPageTemplateEntry (groupId, layoutPrototypeId)",
		"create unique index IX_A075DAA4 on LayoutPageTemplateEntry (groupId, name[$COLUMN_LENGTH:75$])",
		"create index IX_1F1BEA76 on LayoutPageTemplateEntry (groupId, type_, status)",
		"create index IX_A185457E on LayoutPageTemplateEntry (layoutPrototypeId)",
		"create index IX_CEC0A659 on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_34C0EF1B on LayoutPageTemplateEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}