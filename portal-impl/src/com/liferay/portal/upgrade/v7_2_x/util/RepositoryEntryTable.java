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
public class RepositoryEntryTable {

	public static final String TABLE_NAME = "RepositoryEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"repositoryEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"repositoryId", Types.BIGINT},
		{"mappedId", Types.VARCHAR}, {"manualCheckInRequired", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("repositoryEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("mappedId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("manualCheckInRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table RepositoryEntry (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,repositoryEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,mappedId VARCHAR(255) null,manualCheckInRequired BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table RepositoryEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_9BDCF489 on RepositoryEntry (repositoryId, mappedId[$COLUMN_LENGTH:255$])",
		"create index IX_D3B9AF62 on RepositoryEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_354AA664 on RepositoryEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}