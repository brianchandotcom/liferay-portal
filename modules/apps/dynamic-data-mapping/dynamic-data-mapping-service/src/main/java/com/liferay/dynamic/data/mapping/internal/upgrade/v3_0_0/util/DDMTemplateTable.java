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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateTable {

	public static final String TABLE_NAME = "DDMTemplate";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"templateId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT},
		{"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"resourceClassNameId", Types.BIGINT},
		{"templateKey", Types.VARCHAR},
		{"version", Types.VARCHAR},
		{"name", Types.CLOB},
		{"description", Types.CLOB},
		{"type_", Types.VARCHAR},
		{"mode_", Types.VARCHAR},
		{"language", Types.VARCHAR},
		{"script", Types.CLOB},
		{"cacheable", Types.BOOLEAN},
		{"smallImage", Types.BOOLEAN},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("templateId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourceClassNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("templateKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.CLOB);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("mode_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("language", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("script", Types.CLOB);

TABLE_COLUMNS_MAP.put("cacheable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("smallImage", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("smallImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("smallImageURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table DDMTemplate (uuid_ VARCHAR(75) null,templateId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,resourceClassNameId LONG,templateKey VARCHAR(75) null,version VARCHAR(75) null,name TEXT null,description TEXT null,type_ VARCHAR(75) null,mode_ VARCHAR(75) null,language VARCHAR(75) null,script TEXT null,cacheable BOOLEAN,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DDMTemplate";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B6356F93 on DDMTemplate (classNameId, classPK, type_[$COLUMN_LENGTH:75$])",
		"create index IX_32F83D16 on DDMTemplate (classPK)",
		"create index IX_F0C3449 on DDMTemplate (groupId, classNameId, classPK, type_[$COLUMN_LENGTH:75$], mode_[$COLUMN_LENGTH:75$])",
		"create unique index IX_E6DFAB84 on DDMTemplate (groupId, classNameId, templateKey[$COLUMN_LENGTH:75$])",
		"create index IX_B1C33EA6 on DDMTemplate (groupId, classPK)",
		"create index IX_33BEF579 on DDMTemplate (language[$COLUMN_LENGTH:75$])",
		"create index IX_127A35B0 on DDMTemplate (smallImageId)",
		"create index IX_CAE41A28 on DDMTemplate (templateKey[$COLUMN_LENGTH:75$])",
		"create index IX_C4F283C8 on DDMTemplate (type_[$COLUMN_LENGTH:75$])",
		"create index IX_D4C2C221 on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_1AA75CE3 on DDMTemplate (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}