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
public class DLFileEntryTypeTable {

	public static final String TABLE_NAME = "DLFileEntryType";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"fileEntryTypeId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"fileEntryTypeKey", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileEntryType (uuid_ VARCHAR(75) null,fileEntryTypeId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fileEntryTypeKey VARCHAR(75) null,name STRING null,description STRING null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileEntryType";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_4501FD9C on DLFileEntryType (groupId)",
		"create unique index IX_5B6BEF5F on DLFileEntryType (groupId, fileEntryTypeKey)",
		"create unique index IX_E9B6A85B on DLFileEntryType (groupId, name)",
		"create index IX_90724726 on DLFileEntryType (uuid_)",
		"create index IX_5B03E942 on DLFileEntryType (uuid_, companyId)",
		"create unique index IX_1399D844 on DLFileEntryType (uuid_, groupId)"
	};

}