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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class DLFileEntryTable {

	public static final String TABLE_NAME = "DLFileEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"fileEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT},
		{"repositoryId", Types.BIGINT},
		{"folderId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"fileName", Types.VARCHAR},
		{"extension", Types.VARCHAR},
		{"mimeType", Types.VARCHAR},
		{"title", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"extraSettings", Types.CLOB},
		{"fileEntryTypeId", Types.BIGINT},
		{"version", Types.VARCHAR},
		{"size_", Types.BIGINT},
		{"readCount", Types.INTEGER},
		{"smallImageId", Types.BIGINT},
		{"largeImageId", Types.BIGINT},
		{"custom1ImageId", Types.BIGINT},
		{"custom2ImageId", Types.BIGINT},
		{"manualCheckInRequired", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileEntry (uuid_ VARCHAR(75) null,fileEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,repositoryId LONG,folderId LONG,treePath STRING null,name VARCHAR(255) null,fileName VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,readCount INTEGER,smallImageId LONG,largeImageId LONG,custom1ImageId LONG,custom2ImageId LONG,manualCheckInRequired BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table DLFileEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_4CB1B2B4 on DLFileEntry (companyId)",
		"create index IX_772ECDE7 on DLFileEntry (fileEntryTypeId)",
		"create index IX_8F6C75D0 on DLFileEntry (folderId, name)",
		"create index IX_29D0AF28 on DLFileEntry (groupId, folderId, fileEntryTypeId)",
		"create unique index IX_DF37D92E on DLFileEntry (groupId, folderId, fileName)",
		"create unique index IX_5391712 on DLFileEntry (groupId, folderId, name)",
		"create unique index IX_ED5CA615 on DLFileEntry (groupId, folderId, title)",
		"create index IX_D20C434D on DLFileEntry (groupId, userId, folderId)",
		"create index IX_D9492CF6 on DLFileEntry (mimeType)",
		"create index IX_31079DE8 on DLFileEntry (uuid_, companyId)",
		"create unique index IX_BC2E7E6A on DLFileEntry (uuid_, groupId)"
	};

}