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
public class CompanyTable {

	public static final String TABLE_NAME = "Company";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"accountId", Types.BIGINT},
		{"webId", Types.VARCHAR},
		{"key_", Types.CLOB},
		{"mx", Types.VARCHAR},
		{"homeURL", Types.VARCHAR},
		{"logoId", Types.BIGINT},
		{"system", Types.BOOLEAN},
		{"maxUsers", Types.INTEGER},
		{"active_", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("accountId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("webId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("key_", Types.CLOB);

TABLE_COLUMNS_MAP.put("mx", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("homeURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("system", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("maxUsers", Types.INTEGER);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table Company (mvccVersion LONG default 0 not null,companyId LONG not null primary key,accountId LONG,webId VARCHAR(75) null,key_ TEXT null,mx VARCHAR(75) null,homeURL STRING null,logoId LONG,system BOOLEAN,maxUsers INTEGER,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table Company";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_38EFE3FD on Company (logoId)",
		"create index IX_12566EC2 on Company (mx[$COLUMN_LENGTH:75$])",
		"create index IX_35E3E7C6 on Company (system)",
		"create unique index IX_EC00543C on Company (webId[$COLUMN_LENGTH:75$])"
	};

}