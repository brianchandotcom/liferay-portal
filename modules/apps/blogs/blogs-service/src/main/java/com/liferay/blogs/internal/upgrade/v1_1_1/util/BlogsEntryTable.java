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

package com.liferay.blogs.internal.upgrade.v1_1_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class BlogsEntryTable {

	public static final String TABLE_NAME = "BlogsEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"entryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"title", Types.VARCHAR},
		{"subtitle", Types.VARCHAR},
		{"urlTitle", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"content", Types.CLOB},
		{"displayDate", Types.TIMESTAMP},
		{"allowPingbacks", Types.BOOLEAN},
		{"allowTrackbacks", Types.BOOLEAN},
		{"trackbacks", Types.CLOB},
		{"coverImageCaption", Types.VARCHAR},
		{"coverImageFileEntryId", Types.BIGINT},
		{"coverImageURL", Types.VARCHAR},
		{"smallImage", Types.BOOLEAN},
		{"smallImageFileEntryId", Types.BIGINT},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR},
		{"lastPublishDate", Types.TIMESTAMP},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("title", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("subtitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("urlTitle", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("content", Types.CLOB);

TABLE_COLUMNS_MAP.put("displayDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("allowPingbacks", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("allowTrackbacks", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("trackbacks", Types.CLOB);

TABLE_COLUMNS_MAP.put("coverImageCaption", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("coverImageFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("coverImageURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("smallImage", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("smallImageFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("smallImageId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("smallImageURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table BlogsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(150) null,subtitle STRING null,urlTitle VARCHAR(255) null,description STRING null,content TEXT null,displayDate DATE null,allowPingbacks BOOLEAN,allowTrackbacks BOOLEAN,trackbacks TEXT null,coverImageCaption STRING null,coverImageFileEntryId LONG,coverImageURL STRING null,smallImage BOOLEAN,smallImageFileEntryId LONG,smallImageId LONG,smallImageURL STRING null,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table BlogsEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_BB0C2905 on BlogsEntry (companyId, displayDate, status)",
		"create index IX_EB2DCE27 on BlogsEntry (companyId, status)",
		"create index IX_A5F57B61 on BlogsEntry (companyId, userId, status)",
		"create index IX_2672F77F on BlogsEntry (displayDate, status)",
		"create index IX_F0E73383 on BlogsEntry (groupId, displayDate, status)",
		"create index IX_1EFD8EE9 on BlogsEntry (groupId, status)",
		"create unique index IX_DB780A20 on BlogsEntry (groupId, urlTitle[$COLUMN_LENGTH:255$])",
		"create index IX_DA04F689 on BlogsEntry (groupId, userId, displayDate, status)",
		"create index IX_49E15A23 on BlogsEntry (groupId, userId, status)",
		"create index IX_5E8307BB on BlogsEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_1B1040FD on BlogsEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}