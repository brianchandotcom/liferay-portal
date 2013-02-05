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
public class DLSyncTable {

	public static final String TABLE_NAME = "DLSync";

	public static final Object[][] TABLE_COLUMNS = {
		{"syncId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.BIGINT},
		{"modifiedDate", Types.BIGINT},
		{"fileId", Types.BIGINT},
		{"fileUuid", Types.VARCHAR},
		{"repositoryId", Types.BIGINT},
		{"parentFolderId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"event", Types.VARCHAR},
		{"type_", Types.VARCHAR},
		{"version", Types.VARCHAR}
	};

	public static final String TABLE_SQL_CREATE = "create table DLSync (syncId LONG not null primary key,companyId LONG,createDate LONG,modifiedDate LONG,fileId LONG,fileUuid VARCHAR(75) null,repositoryId LONG,parentFolderId LONG,name VARCHAR(255) null,description STRING null,event VARCHAR(75) null,type_ VARCHAR(75) null,version VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table DLSync";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_B53EC783 on DLSync (companyId, modifiedDate, repositoryId)",
		"create index IX_3116DE70 on DLSync (companyId, repositoryId, updateId)",
		"create unique index IX_F9821AB4 on DLSync (fileId)"
	};

}