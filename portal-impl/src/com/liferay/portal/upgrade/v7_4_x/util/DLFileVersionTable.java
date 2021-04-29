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
public class DLFileVersionTable {

	public static final String TABLE_NAME = "DLFileVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
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
		{"checksum", Types.VARCHAR}, {"expirationDate", Types.TIMESTAMP},
		{"reviewDate", Types.TIMESTAMP}, {"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER}, {"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR}, {"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

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

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("reviewDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DLFileVersion (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,fileVersionId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,folderId LONG,fileEntryId LONG,treePath STRING null,fileName VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,changeLog VARCHAR(75) null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,checksum VARCHAR(75) null,expirationDate DATE null,reviewDate DATE null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (fileVersionId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table DLFileVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_97782D6C on DLFileVersion (companyId, ctCollectionId)",
		"create index IX_808EF252 on DLFileVersion (companyId, status, ctCollectionId)",
		"create index IX_759EF1C5 on DLFileVersion (fileEntryId, ctCollectionId)",
		"create index IX_C97C4DAB on DLFileVersion (fileEntryId, status, ctCollectionId)",
		"create unique index IX_10E504DF on DLFileVersion (fileEntryId, version[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_3A12DA31 on DLFileVersion (groupId, folderId, status, ctCollectionId)",
		"create index IX_DCA2C64B on DLFileVersion (groupId, folderId, title[$COLUMN_LENGTH:255$], version[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_9E97D7BA on DLFileVersion (mimeType[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_16CE5EAC on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_48BF1DF8 on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_350F5CAE on DLFileVersion (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}