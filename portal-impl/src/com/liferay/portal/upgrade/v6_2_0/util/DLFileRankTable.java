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
 * @author	  Mate Thurzo
 * @generated
 */
public class DLFileRankTable {

	public static final String TABLE_NAME = "DLFileRank";

	public static final Object[][] TABLE_COLUMNS = {
			{"uuid_", Types.VARCHAR},
			{"fileRankId", Types.BIGINT},
			{"groupId", Types.BIGINT},
			{"companyId", Types.BIGINT},
			{"userId", Types.BIGINT},
			{"userName", Types.VARCHAR},
			{"createDate", Types.TIMESTAMP},
			{"modifiedDate", Types.TIMESTAMP},
			{"fileEntryId", Types.BIGINT},
			{"active_", Types.BOOLEAN}
	};

	public static final String TABLE_SQL_CREATE = "create table DLFileRank (uuid_ VARCHAR(75) null,fileRankId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fileEntryId LONG,active_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table DLFileRank";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
			"create unique index IX_38F0315 on DLFileRank (companyId, userId, fileEntryId)",
			"create index IX_A65A1F8B on DLFileRank (fileEntryId)",
			"create index IX_BAFB116E on DLFileRank (groupId, userId)",
			"create index IX_4E96195B on DLFileRank (groupId, userId, active_)",
			"create index IX_EED06670 on DLFileRank (userId)",
			"create index IX_B8BB7CBE on DLFileRank (uuid_)",
			"create index IX_B3D1C4AA on DLFileRank (uuid_, companyId)",
			"create unique index IX_6D776DAC on DLFileRank (uuid_, groupId)"
	};

}