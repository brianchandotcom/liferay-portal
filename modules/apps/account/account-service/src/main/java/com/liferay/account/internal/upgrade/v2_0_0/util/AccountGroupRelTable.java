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

package com.liferay.account.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AccountGroupRelTable {

	public static final String TABLE_NAME = "AccountGroupRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"AccountGroupRelId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"accountGroupId", Types.BIGINT},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("AccountGroupRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("accountGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE =
"create table AccountGroupRel (mvccVersion LONG default 0 not null,AccountGroupRelId LONG not null primary key,companyId LONG,accountGroupId LONG,classNameId LONG,classPK LONG)";

	public static final String TABLE_SQL_DROP = "drop table AccountGroupRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_448835E3 on AccountGroupRel (accountGroupId, classNameId, classPK)",
		"create index IX_E31F0762 on AccountGroupRel (classNameId, classPK)"
	};

}