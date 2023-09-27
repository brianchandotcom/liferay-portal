/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AssetLinkTable {

	public static final String TABLE_NAME = "AssetLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"linkId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"entryId1", Types.BIGINT},
		{"entryId2", Types.BIGINT}, {"type_", Types.INTEGER},
		{"weight", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("linkId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("entryId1", Types.BIGINT);

TABLE_COLUMNS_MAP.put("entryId2", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("weight", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table AssetLink (linkId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,entryId1 LONG,entryId2 LONG,type_ INTEGER,weight INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table AssetLink";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_8F542794 on AssetLink (entryId1, entryId2, type_)",
		"create index IX_14D5A20D on AssetLink (entryId1, type_)",
		"create index IX_91F132C on AssetLink (entryId2, type_)"
	};

}