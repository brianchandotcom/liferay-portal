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

package com.liferay.remote.app.internal.upgrade.v2_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Javier de Arcos
 * @generated
 */
public class RemoteAppEntryTable {

	public static final String TABLE_NAME = "RemoteAppEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"remoteAppEntryId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"customElementCSSURLs", Types.CLOB},
		{"customElementHTMLElementName", Types.VARCHAR},
		{"customElementURLs", Types.CLOB}, {"description", Types.CLOB},
		{"friendlyURLMapping", Types.VARCHAR}, {"iFrameURL", Types.VARCHAR},
		{"instanceable", Types.BOOLEAN}, {"name", Types.VARCHAR},
		{"portletCategoryName", Types.VARCHAR}, {"properties", Types.CLOB},
		{"sourceCodeURL", Types.VARCHAR}, {"type_", Types.VARCHAR},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("remoteAppEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("customElementCSSURLs", Types.CLOB);

TABLE_COLUMNS_MAP.put("customElementHTMLElementName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("customElementURLs", Types.CLOB);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("friendlyURLMapping", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("iFrameURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("instanceable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("portletCategoryName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("properties", Types.CLOB);

TABLE_COLUMNS_MAP.put("sourceCodeURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table RemoteAppEntry (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,remoteAppEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,customElementCSSURLs TEXT null,customElementHTMLElementName VARCHAR(255) null,customElementURLs TEXT null,description TEXT null,friendlyURLMapping VARCHAR(75) null,iFrameURL STRING null,instanceable BOOLEAN,name STRING null,portletCategoryName VARCHAR(75) null,properties TEXT null,sourceCodeURL STRING null,type_ VARCHAR(75) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table RemoteAppEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_7CDD4FB0 on RemoteAppEntry (companyId, iFrameURL[$COLUMN_LENGTH:4000$])",
		"create index IX_5F8F9C11 on RemoteAppEntry (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}