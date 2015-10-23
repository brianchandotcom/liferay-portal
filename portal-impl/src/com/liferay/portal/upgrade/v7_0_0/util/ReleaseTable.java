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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Manuel de la Peña
 * @generated
 */
public class ReleaseTable {

	public static final String TABLE_NAME = "Release_";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"releaseId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"servletContextName", Types.VARCHAR},
		{"schemaVersion", Types.VARCHAR},
		{"buildNumber", Types.INTEGER},
		{"buildDate", Types.TIMESTAMP},
		{"verified", Types.BOOLEAN},
		{"state_", Types.INTEGER},
		{"testString", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("releaseId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("servletContextName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("schemaVersion", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("buildNumber", Types.INTEGER);

TABLE_COLUMNS_MAP.put("buildDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("verified", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("state_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("testString", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table Release_ (mvccVersion LONG default 0,releaseId LONG not null primary key,createDate DATE null,modifiedDate DATE null,servletContextName VARCHAR(75) null,schemaVersion VARCHAR(75) null,buildNumber INTEGER,buildDate DATE null,verified BOOLEAN,state_ INTEGER,testString VARCHAR(1024) null)";

	public static final String TABLE_SQL_DROP = "drop table Release_";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_8BD6BCA7 on Release_ (servletContextName)"
	};

}