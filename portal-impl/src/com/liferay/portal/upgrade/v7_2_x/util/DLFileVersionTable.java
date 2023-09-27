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
public class DLFileVersionTable {

	public static final String TABLE_NAME = "DLFileVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"fileVersionId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"repositoryId", Types.BIGINT}, {"folderId", Types.BIGINT},
		{"fileEntryId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"fileName", Types.VARCHAR}, {"extension", Types.VARCHAR},
		{"mimeType", Types.VARCHAR}, {"title", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"changeLog", Types.VARCHAR},
		{"extraSettings", Types.CLOB}, {"fileEntryTypeId", Types.BIGINT},
		{"version", Types.VARCHAR}, {"size_", Types.BIGINT},
		{"checksum", Types.VARCHAR}, {"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("extension", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("changeLog", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("extraSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("fileEntryTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("checksum", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DLFileVersion (uuid_ VARCHAR(75) null,fileVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,folderId LONG,fileEntryId LONG,treePath STRING null,fileName VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,changeLog VARCHAR(75) null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,checksum VARCHAR(75) null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A0A283F4 on DLFileVersion (companyId, status)",
		"create index IX_D47BB14D on DLFileVersion (fileEntryId, status)",
		"create unique index IX_E2815081 on DLFileVersion (fileEntryId, version[$COLUMN_LENGTH:75$])",
		"create index IX_DFD809D3 on DLFileVersion (groupId, folderId, status)",
		"create index IX_9BE769ED on DLFileVersion (groupId, folderId, title[$COLUMN_LENGTH:255$], version[$COLUMN_LENGTH:75$])",
		"create index IX_FFB3395C on DLFileVersion (mimeType[$COLUMN_LENGTH:75$])",
		"create index IX_95E9E44E on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_C99B2650 on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}