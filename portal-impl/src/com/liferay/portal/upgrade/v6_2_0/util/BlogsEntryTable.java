/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v6_2_0.util;

import java.sql.Types;

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
		{"urlTitle", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"content", Types.CLOB},
		{"displayDate", Types.TIMESTAMP},
		{"allowPingbacks", Types.BOOLEAN},
		{"allowTrackbacks", Types.BOOLEAN},
		{"trackbacks", Types.CLOB},
		{"smallImage", Types.BOOLEAN},
		{"smallImageId", Types.BIGINT},
		{"smallImageURL", Types.VARCHAR},
		{"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT},
		{"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final String TABLE_SQL_CREATE = "create table BlogsEntry (uuid_ VARCHAR(75) null,entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,title VARCHAR(150) null,urlTitle VARCHAR(150) null,description STRING null,content TEXT null,displayDate DATE null,allowPingbacks BOOLEAN,allowTrackbacks BOOLEAN,trackbacks TEXT null,smallImage BOOLEAN,smallImageId LONG,smallImageURL STRING null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table BlogsEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_72EF6041 on BlogsEntry (companyId)",
		"create index IX_430D791F on BlogsEntry (companyId, displayDate)",
		"create index IX_BB0C2905 on BlogsEntry (companyId, displayDate, status)",
		"create index IX_EB2DCE27 on BlogsEntry (companyId, status)",
		"create index IX_8CACE77B on BlogsEntry (companyId, userId)",
		"create index IX_A5F57B61 on BlogsEntry (companyId, userId, status)",
		"create index IX_2672F77F on BlogsEntry (displayDate, status)",
		"create index IX_81A50303 on BlogsEntry (groupId)",
		"create index IX_621E19D on BlogsEntry (groupId, displayDate)",
		"create index IX_F0E73383 on BlogsEntry (groupId, displayDate, status)",
		"create index IX_1EFD8EE9 on BlogsEntry (groupId, status)",
		"create unique index IX_DB780A20 on BlogsEntry (groupId, urlTitle)",
		"create index IX_FBDE0AA3 on BlogsEntry (groupId, userId, displayDate)",
		"create index IX_DA04F689 on BlogsEntry (groupId, userId, displayDate, status)",
		"create index IX_49E15A23 on BlogsEntry (groupId, userId, status)",
		"create index IX_69157A4D on BlogsEntry (uuid_)",
		"create index IX_5E8307BB on BlogsEntry (uuid_, companyId)",
		"create unique index IX_1B1040FD on BlogsEntry (uuid_, groupId)"
	};

}