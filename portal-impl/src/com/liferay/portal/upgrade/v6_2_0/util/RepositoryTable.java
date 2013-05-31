/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
public class RepositoryTable {

	public static final String TABLE_NAME = "Repository";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"repositoryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"portletId", Types.VARCHAR},
		{"typeSettings", Types.CLOB},
		{"dlFolderId", Types.BIGINT}
	};

	public static final String TABLE_SQL_CREATE = "create table Repository (uuid_ VARCHAR(75) null,repositoryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,name VARCHAR(75) null,description STRING null,portletId VARCHAR(200) null,typeSettings TEXT null,dlFolderId LONG)";

	public static final String TABLE_SQL_DROP = "drop table Repository";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5253B1FA on Repository (groupId)",
		"create unique index IX_60C8634C on Repository (groupId, name, portletId)",
		"create index IX_74C17B04 on Repository (uuid_)",
		"create index IX_F543EA4 on Repository (uuid_, companyId)",
		"create unique index IX_11641E26 on Repository (uuid_, groupId)"
	};

}