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
public class ResourceActionTable {

	public static final String TABLE_NAME = "ResourceAction";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"resourceActionId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"actionId", Types.VARCHAR},
		{"bitwiseValue", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resourceActionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("actionId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("bitwiseValue", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table ResourceAction (mvccVersion LONG default 0,resourceActionId LONG not null primary key,name VARCHAR(255) null,actionId VARCHAR(75) null,bitwiseValue LONG)";

	public static final String TABLE_SQL_DROP = "drop table ResourceAction";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_EDB9986E on ResourceAction (name, actionId)"
	};

}