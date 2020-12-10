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

package com.liferay.account.internal.upgrade.v1_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AccountGroupTable {

	public static final String TABLE_NAME = "AccountGroup";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"externalReferenceCode", Types.VARCHAR},
		{"accountGroupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"defaultAccountGroup", Types.BOOLEAN}, {"description", Types.VARCHAR},
		{"name", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("accountGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("defaultAccountGroup", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table AccountGroup (mvccVersion LONG default 0 not null,externalReferenceCode VARCHAR(75) null,accountGroupId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,defaultAccountGroup BOOLEAN,description VARCHAR(75) null,name VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table AccountGroup";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F7BFA1CD on AccountGroup (companyId, externalReferenceCode[$COLUMN_LENGTH:75$])"
	};

}