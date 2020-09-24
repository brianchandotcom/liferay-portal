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

package com.liferay.depot.internal.upgrade.v1_2_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DepotEntryGroupRelTable {

	public static final String TABLE_NAME = "DepotEntryGroupRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"depotEntryGroupRelId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"ddmStructuresAvailable", Types.BOOLEAN},
		{"depotEntryId", Types.BIGINT}, {"searchable", Types.BOOLEAN},
		{"toGroupId", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("depotEntryGroupRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("ddmStructuresAvailable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("depotEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("searchable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("toGroupId", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE =
"create table DepotEntryGroupRel (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,depotEntryGroupRelId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,modifiedDate DATE null,ddmStructuresAvailable BOOLEAN,depotEntryId LONG,searchable BOOLEAN,toGroupId LONG)";

	public static final String TABLE_SQL_DROP = "drop table DepotEntryGroupRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_F329B161 on DepotEntryGroupRel (ddmStructuresAvailable, toGroupId)",
		"create unique index IX_65D34444 on DepotEntryGroupRel (depotEntryId, toGroupId)",
		"create index IX_C61C803B on DepotEntryGroupRel (searchable, toGroupId)",
		"create index IX_DB75E9F1 on DepotEntryGroupRel (toGroupId)",
		"create index IX_7ED7EAB2 on DepotEntryGroupRel (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_D25F75B4 on DepotEntryGroupRel (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}