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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class RepositoryEntryTable {

	public static final String TABLE_NAME = "RepositoryEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"repositoryEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"repositoryId", Types.BIGINT},
		{"mappedId", Types.VARCHAR},
		{"manualCheckInRequired", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table RepositoryEntry (mvccVersion LONG default 0,uuid_ VARCHAR(75) null,repositoryEntryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,repositoryId LONG,mappedId VARCHAR(200) null,manualCheckInRequired BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table RepositoryEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_9BDCF489 on RepositoryEntry (repositoryId, mappedId)",
		"create index IX_D3B9AF62 on RepositoryEntry (uuid_, companyId)",
		"create unique index IX_354AA664 on RepositoryEntry (uuid_, groupId)"
	};

}