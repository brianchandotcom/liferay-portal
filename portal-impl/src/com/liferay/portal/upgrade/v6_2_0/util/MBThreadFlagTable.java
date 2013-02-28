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
 * @author	  Daniel Kocsis
 * @generated
 */
public class MBThreadFlagTable {

	public static final String TABLE_NAME = "MBThreadFlag";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"threadFlagId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"threadId", Types.BIGINT}
	};

	public static final String TABLE_SQL_CREATE = "create table MBThreadFlag (uuid_ VARCHAR(75) null,threadFlagId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,threadId LONG)";

	public static final String TABLE_SQL_DROP = "drop table MBThreadFlag";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8CB0A24A on MBThreadFlag (threadId)",
		"create index IX_A28004B on MBThreadFlag (userId)",
		"create unique index IX_33781904 on MBThreadFlag (userId, threadId)",
		"create index IX_F36BBB83 on MBThreadFlag (uuid_)",
		"create index IX_DCE308C5 on MBThreadFlag (uuid_, companyId)",
		"create unique index IX_FEB0FC87 on MBThreadFlag (uuid_, groupId)"
	};

}