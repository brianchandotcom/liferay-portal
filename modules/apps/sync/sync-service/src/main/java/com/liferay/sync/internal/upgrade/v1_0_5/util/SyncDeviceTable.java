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

package null;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SyncDeviceTable {

	public static final String TABLE_NAME = "SyncDevice";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"syncDeviceId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"type_", Types.VARCHAR},
		{"buildNumber", Types.BIGINT},
		{"featureSet", Types.INTEGER},
		{"hostname", Types.VARCHAR},
		{"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("syncDeviceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("buildNumber", Types.BIGINT);

TABLE_COLUMNS_MAP.put("featureSet", Types.INTEGER);

TABLE_COLUMNS_MAP.put("hostname", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table SyncDevice (uuid_ VARCHAR(75) null,syncDeviceId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,type_ VARCHAR(75) null,buildNumber LONG,featureSet INTEGER,hostname VARCHAR(200) null,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table SyncDevice";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_176DF87B on SyncDevice (companyId, userName[$COLUMN_LENGTH:75$])",
		"create index IX_A18EDDB1 on SyncDevice (userId)",
		"create index IX_AE38DEAB on SyncDevice (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}