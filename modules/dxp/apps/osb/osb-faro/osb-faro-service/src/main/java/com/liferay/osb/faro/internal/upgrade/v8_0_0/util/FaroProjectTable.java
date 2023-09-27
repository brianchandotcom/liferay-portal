/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.internal.upgrade.v8_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class FaroProjectTable {

	public static final String TABLE_NAME = "OSBFaro_FaroProject";

	public static final Object[][] TABLE_COLUMNS = {
		{"faroProjectId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createTime", Types.BIGINT}, {"modifiedTime", Types.BIGINT},
		{"name", Types.VARCHAR}, {"accountKey", Types.VARCHAR},
		{"accountName", Types.VARCHAR}, {"corpProjectName", Types.VARCHAR},
		{"corpProjectUuid", Types.VARCHAR}, {"ipAddresses", Types.VARCHAR},
		{"lastAccessTime", Types.BIGINT},
		{"recommendationsEnabled", Types.BOOLEAN},
		{"serverLocation", Types.VARCHAR}, {"services", Types.VARCHAR},
		{"state_", Types.VARCHAR}, {"subscription", Types.VARCHAR},
		{"weDeployKey", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("faroProjectId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("accountKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("accountName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("corpProjectName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("corpProjectUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("ipAddresses", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastAccessTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recommendationsEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("serverLocation", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("services", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("state_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("subscription", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("weDeployKey", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table OSBFaro_FaroProject (faroProjectId LONG not null primary key,groupId LONG,userId LONG,userName VARCHAR(75) null,createTime LONG,modifiedTime LONG,name VARCHAR(75) null,accountKey VARCHAR(75) null,accountName VARCHAR(75) null,corpProjectName VARCHAR(75) null,corpProjectUuid VARCHAR(75) null,ipAddresses STRING null,lastAccessTime LONG,recommendationsEnabled BOOLEAN,serverLocation VARCHAR(75) null,services STRING null,state_ VARCHAR(75) null,subscription STRING null,weDeployKey VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP =
"drop table OSBFaro_FaroProject";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}