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

package com.liferay.depot.internal.upgrade.v1_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Alejandro Tardín
 * @generated
 */
public class DepotEntryGroupRelTable {

	public static final String TABLE_NAME = "DepotEntryGroupRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"depotEntryGroupRelId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"ddmStructuresAvailable", Types.BOOLEAN},
		{"depotEntryId", Types.BIGINT}, {"searchable", Types.BOOLEAN},
		{"toGroupId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("depotEntryGroupRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ddmStructuresAvailable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("depotEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("searchable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("toGroupId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE =
"create table DepotEntryGroupRel (mvccVersion LONG default 0 not null,depotEntryGroupRelId LONG not null primary key,companyId LONG,ddmStructuresAvailable BOOLEAN,depotEntryId LONG,searchable BOOLEAN,toGroupId LONG)";

	public static final String TABLE_SQL_DROP = "drop table DepotEntryGroupRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_65D34444 on DepotEntryGroupRel (depotEntryId, toGroupId)",
		"create index IX_C61C803B on DepotEntryGroupRel (searchable, toGroupId)",
		"create index IX_DB75E9F1 on DepotEntryGroupRel (toGroupId)"
	};

}