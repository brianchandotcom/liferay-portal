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
public class DLFileEntryTable {

	public static final String TABLE_NAME = "DLFileEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"fileEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"repositoryId", Types.BIGINT}, {"folderId", Types.BIGINT},
		{"treePath", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"fileName", Types.VARCHAR}, {"extension", Types.VARCHAR},
		{"mimeType", Types.VARCHAR}, {"title", Types.VARCHAR},
		{"description", Types.VARCHAR}, {"extraSettings", Types.CLOB},
		{"fileEntryTypeId", Types.BIGINT}, {"version", Types.VARCHAR},
		{"size_", Types.BIGINT}, {"readCount", Types.INTEGER},
		{"smallImageId", Types.BIGINT}, {"largeImageId", Types.BIGINT},
		{"custom1ImageId", Types.BIGINT}, {"custom2ImageId", Types.BIGINT},
		{"manualCheckInRequired", Types.BOOLEAN},
		{"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

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

TABLE_COLUMNS_MAP.put("readCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("smallImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("largeImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("custom1ImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("custom2ImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("manualCheckInRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DLFileEntry (uuid_ VARCHAR(75) null,fileEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,repositoryId LONG,folderId LONG,treePath STRING null,name VARCHAR(255) null,fileName VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,readCount INTEGER,smallImageId LONG,largeImageId LONG,custom1ImageId LONG,custom2ImageId LONG,manualCheckInRequired BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5444C427 on DLFileEntry (companyId, fileEntryTypeId)",
		"create index IX_B8526DBE on DLFileEntry (custom1ImageId)",
		"create index IX_AC9BDEDD on DLFileEntry (custom2ImageId)",
		"create index IX_772ECDE7 on DLFileEntry (fileEntryTypeId)",
		"create index IX_8F6C75D0 on DLFileEntry (folderId, name[$COLUMN_LENGTH:255$])",
		"create index IX_BAF654E5 on DLFileEntry (groupId, fileEntryTypeId)",
		"create index IX_29D0AF28 on DLFileEntry (groupId, folderId, fileEntryTypeId)",
		"create unique index IX_DF37D92E on DLFileEntry (groupId, folderId, fileName[$COLUMN_LENGTH:255$])",
		"create unique index IX_5391712 on DLFileEntry (groupId, folderId, name[$COLUMN_LENGTH:255$])",
		"create unique index IX_ED5CA615 on DLFileEntry (groupId, folderId, title[$COLUMN_LENGTH:255$])",
		"create index IX_D20C434D on DLFileEntry (groupId, userId, folderId)",
		"create index IX_4DB7A143 on DLFileEntry (largeImageId)",
		"create index IX_D9492CF6 on DLFileEntry (mimeType[$COLUMN_LENGTH:75$])",
		"create index IX_1B352F4A on DLFileEntry (repositoryId, folderId)",
		"create index IX_25F5CAB9 on DLFileEntry (smallImageId, largeImageId, custom1ImageId, custom2ImageId)",
		"create index IX_31079DE8 on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_BC2E7E6A on DLFileEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}