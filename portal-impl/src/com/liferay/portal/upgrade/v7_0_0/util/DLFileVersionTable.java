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
public class DLFileVersionTable {

	public static final String TABLE_NAME = "DLFileVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"fileVersionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"repositoryId", Types.BIGINT},
		{"folderId", Types.BIGINT},
		{"fileEntryId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"fileName", Types.VARCHAR},
		{"extension", Types.VARCHAR},
		{"mimeType", Types.VARCHAR},
		{"title", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"changeLog", Types.VARCHAR},
		{"extraSettings", Types.CLOB},
		{"fileEntryTypeId", Types.BIGINT},
		{"version", Types.VARCHAR},
		{"size_", Types.BIGINT},
		{"checksum", Types.VARCHAR},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileVersion (uuid_ VARCHAR(75) null,fileVersionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,folderId LONG,fileEntryId LONG,treePath STRING null,fileName VARCHAR(255) null,extension VARCHAR(75) null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,changeLog VARCHAR(75) null,extraSettings TEXT null,fileEntryTypeId LONG,version VARCHAR(75) null,size_ LONG,checksum VARCHAR(75) null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A0A283F4 on DLFileVersion (companyId, status)",
		"create index IX_D47BB14D on DLFileVersion (fileEntryId, status)",
		"create unique index IX_E2815081 on DLFileVersion (fileEntryId, version)",
		"create index IX_DFD809D3 on DLFileVersion (groupId, folderId, status)",
		"create index IX_9BE769ED on DLFileVersion (groupId, folderId, title, version)",
		"create index IX_FFB3395C on DLFileVersion (mimeType)",
		"create index IX_95E9E44E on DLFileVersion (uuid_, companyId)",
		"create unique index IX_C99B2650 on DLFileVersion (uuid_, groupId)"
	};

}