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

package com.liferay.portal.upgrade.v7_4_x.util;

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
		{"mvccVersion", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"webId", Types.VARCHAR}, {"mx", Types.VARCHAR},
		{"homeURL", Types.VARCHAR}, {"logoId", Types.BIGINT},
		{"system_", Types.BOOLEAN}, {"maxUsers", Types.INTEGER},
		{"active_", Types.BOOLEAN}, {"name", Types.VARCHAR},
		{"legalName", Types.VARCHAR}, {"legalId", Types.VARCHAR},
		{"legalType", Types.VARCHAR}, {"sicCode", Types.VARCHAR},
		{"tickerSymbol", Types.VARCHAR}, {"industry", Types.VARCHAR},
		{"type_", Types.VARCHAR}, {"size_", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("webId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("mx", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("homeURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("logoId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("maxUsers", Types.INTEGER);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("legalName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("legalId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("legalType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("sicCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("tickerSymbol", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("industry", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("size_", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table Company (mvccVersion LONG default 0 not null,companyId LONG not null primary key,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,webId VARCHAR(75) null,mx VARCHAR(200) null,homeURL STRING null,logoId LONG,system_ BOOLEAN,maxUsers INTEGER,active_ BOOLEAN,name VARCHAR(75) null,legalName VARCHAR(75) null,legalId VARCHAR(75) null,legalType VARCHAR(75) null,sicCode VARCHAR(75) null,tickerSymbol VARCHAR(75) null,industry VARCHAR(75) null,type_ VARCHAR(75) null,size_ VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table Company";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_38EFE3FD on Company (logoId)",
		"create index IX_12566EC2 on Company (mx[$COLUMN_LENGTH:200$])",
		"create index IX_8699D9BD on Company (system_)",
		"create unique index IX_EC00543C on Company (webId[$COLUMN_LENGTH:75$])"
	};

}