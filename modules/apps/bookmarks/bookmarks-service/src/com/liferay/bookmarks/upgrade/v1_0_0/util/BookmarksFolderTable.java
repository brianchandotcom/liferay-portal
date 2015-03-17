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

package com.liferay.bookmarks.upgrade.v1_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BookmarksFolderTable {

	public static final String TABLE_NAME = "BookmarksFolder";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"folderId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"lastPublishDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"resourceBlockId", Types.BIGINT},
		{"parentFolderId", Types.BIGINT},
		{"treePath", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table BookmarksFolder (uuid_ VARCHAR(75) null,folderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,lastPublishDate DATE null,modifiedDate DATE null,resourceBlockId LONG,parentFolderId LONG,treePath STRING null,name VARCHAR(75) null,description STRING null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table BookmarksFolder";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}