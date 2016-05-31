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
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutBranchTable {

	public static final String TABLE_NAME = "LayoutBranch";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"layoutBranchId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"layoutSetBranchId", Types.BIGINT},
		{"plid", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"master", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("layoutBranchId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("layoutSetBranchId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("plid", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("master", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table LayoutBranch (mvccVersion LONG default 0 not null,layoutBranchId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,layoutSetBranchId LONG,plid LONG,name VARCHAR(75) null,description STRING null,master BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table LayoutBranch";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A705FF94 on LayoutBranch (layoutSetBranchId, plid, master)",
		"create unique index IX_FD57097D on LayoutBranch (layoutSetBranchId, plid, name[$COLUMN_LENGTH:75$])"
	};

}