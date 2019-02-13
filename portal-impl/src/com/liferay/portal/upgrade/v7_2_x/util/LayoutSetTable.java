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
public class LayoutSetTable {

	public static final String TABLE_NAME = "LayoutSet";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"headId", Types.BIGINT},
		{"layoutSetId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"privateLayout", Types.BOOLEAN},
		{"logoId", Types.BIGINT},
		{"themeId", Types.VARCHAR},
		{"colorSchemeId", Types.VARCHAR},
		{"css", Types.CLOB},
		{"pageCount", Types.INTEGER},
		{"settings_", Types.CLOB},
		{"layoutSetPrototypeUuid", Types.VARCHAR},
		{"layoutSetPrototypeLinkEnabled", Types.BOOLEAN},
		{"head", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("headId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("layoutSetId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("privateLayout", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("themeId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("colorSchemeId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("pageCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("settings_", Types.CLOB);

TABLE_COLUMNS_MAP.put("layoutSetPrototypeUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutSetPrototypeLinkEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("head", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table LayoutSet (mvccVersion LONG default 0 not null,headId LONG,layoutSetId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,privateLayout BOOLEAN,logoId LONG,themeId VARCHAR(75) null,colorSchemeId VARCHAR(75) null,css TEXT null,pageCount INTEGER,settings_ TEXT null,layoutSetPrototypeUuid VARCHAR(75) null,layoutSetPrototypeLinkEnabled BOOLEAN,head BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table LayoutSet";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_AAF037A5 on LayoutSet (groupId, privateLayout, head)",
		"create unique index IX_28B7204D on LayoutSet (headId)",
		"create index IX_72BBA8B7 on LayoutSet (layoutSetPrototypeUuid[$COLUMN_LENGTH:75$])",
		"create index IX_1B698D9 on LayoutSet (privateLayout, logoId)"
	};

}