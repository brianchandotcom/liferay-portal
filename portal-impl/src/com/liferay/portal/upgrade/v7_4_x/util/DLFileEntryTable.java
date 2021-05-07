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
public class DLFileEntryTable {

	public static final String TABLE_NAME = "DLFileEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"externalReferenceCode", Types.VARCHAR},
		{"fileEntryId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"repositoryId", Types.BIGINT},
		{"folderId", Types.BIGINT}, {"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"fileName", Types.VARCHAR},
		{"extension", Types.VARCHAR}, {"mimeType", Types.VARCHAR},
		{"title", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"extraSettings", Types.CLOB}, {"fileEntryTypeId", Types.BIGINT},
		{"version", Types.VARCHAR}, {"size_", Types.BIGINT},
		{"smallImageId", Types.BIGINT}, {"largeImageId", Types.BIGINT},
		{"custom1ImageId", Types.BIGINT}, {"custom2ImageId", Types.BIGINT},
		{"manualCheckInRequired", Types.BOOLEAN},
		{"expirationDate", Types.TIMESTAMP}, {"reviewDate", Types.TIMESTAMP},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("externalReferenceCode", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("repositoryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("folderId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("treePath", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("extension", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("extraSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("fileEntryTypeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);

TABLE_COLUMNS_MAP.put("smallImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("largeImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("custom1ImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("custom2ImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("manualCheckInRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("expirationDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("reviewDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DLFileEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,externalReferenceCode VARCHAR(75) null,fileEntryId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,repositoryId LONG,folderId LONG,treePath STRING null,name VARCHAR(255) null,fileName VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,smallImageId LONG,largeImageId LONG,custom1ImageId LONG,custom2ImageId LONG,manualCheckInRequired BOOLEAN,expirationDate DATE null,reviewDate DATE null,lastPublishDate DATE null,primary key (fileEntryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table DLFileEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_33E8A112 on DLFileEntry (companyId, ctCollectionId)",
		"create index IX_5444C427 on DLFileEntry (companyId, fileEntryTypeId)",
		"create index IX_9B56081C on DLFileEntry (custom1ImageId, ctCollectionId)",
		"create index IX_9D2F5B3B on DLFileEntry (custom2ImageId, ctCollectionId)",
		"create index IX_C0A6F645 on DLFileEntry (fileEntryTypeId, ctCollectionId)",
		"create index IX_F951AC2E on DLFileEntry (folderId, name[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create index IX_60830094 on DLFileEntry (groupId, ctCollectionId)",
		"create index IX_273362A5 on DLFileEntry (groupId, externalReferenceCode[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_BAF654E5 on DLFileEntry (groupId, fileEntryTypeId)",
		"create index IX_95A2D1F1 on DLFileEntry (groupId, folderId, ctCollectionId)",
		"create index IX_D8883586 on DLFileEntry (groupId, folderId, fileEntryTypeId, ctCollectionId)",
		"create unique index IX_A256938C on DLFileEntry (groupId, folderId, fileName[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create unique index IX_F7878970 on DLFileEntry (groupId, folderId, name[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create unique index IX_EAAB273 on DLFileEntry (groupId, folderId, title[$COLUMN_LENGTH:255$], ctCollectionId)",
		"create index IX_3B20ECE on DLFileEntry (groupId, userId, ctCollectionId)",
		"create index IX_87A8DFAB on DLFileEntry (groupId, userId, folderId, ctCollectionId)",
		"create index IX_863591A1 on DLFileEntry (largeImageId, ctCollectionId)",
		"create index IX_72175754 on DLFileEntry (mimeType[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_6EC7490B on DLFileEntry (repositoryId, ctCollectionId)",
		"create index IX_277C31A8 on DLFileEntry (repositoryId, folderId, ctCollectionId)",
		"create index IX_A8521555 on DLFileEntry (smallImageId, ctCollectionId)",
		"create index IX_854E0F17 on DLFileEntry (smallImageId, largeImageId, custom1ImageId, custom2ImageId, ctCollectionId)",
		"create index IX_1F89A446 on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_CF17549E on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_373340C8 on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}