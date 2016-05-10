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

package com.liferay.portal.upgrade.v6_0_0.util;

import java.sql.Types;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 * @deprecated As of 7.0.1, with no direct replacement
 */
@Deprecated
public class DLFileRankTable {

	public static final String TABLE_NAME = "DLFileRank";

	public static final Object[][] TABLE_COLUMNS = {
		{"fileRankId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"folderId", Types.BIGINT},
		{"name", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileRank (fileRankId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,createDate DATE null,folderId LONG,name VARCHAR(255) null)";

	public static final String TABLE_SQL_DROP = "drop table DLFileRank";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_CE705D48 on DLFileRank (companyId, userId, folderId, name)",
		"create index IX_40B56512 on DLFileRank (folderId, name)",
		"create index IX_BAFB116E on DLFileRank (groupId, userId)",
		"create index IX_EED06670 on DLFileRank (userId)"
	};

}