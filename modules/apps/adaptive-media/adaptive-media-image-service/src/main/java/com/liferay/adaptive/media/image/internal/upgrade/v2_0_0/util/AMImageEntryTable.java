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

package com.liferay.adaptive.media.image.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AMImageEntryTable {

	public static final String TABLE_NAME = "AMImageEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"amImageEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP},
		{"configurationUuid", Types.VARCHAR},
		{"fileVersionId", Types.BIGINT},
		{"mimeType", Types.VARCHAR},
		{"height", Types.INTEGER},
		{"width", Types.INTEGER},
		{"size_", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("amImageEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("configurationUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fileVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("mimeType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("height", Types.INTEGER);

TABLE_COLUMNS_MAP.put("width", Types.INTEGER);

TABLE_COLUMNS_MAP.put("size_", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table AMImageEntry (uuid_ VARCHAR(75) null,amImageEntryId LONG not null primary key,groupId LONG,companyId LONG,createDate DATE null,configurationUuid VARCHAR(75) null,fileVersionId LONG,mimeType VARCHAR(75) null,height INTEGER,width INTEGER,size_ LONG)";

	public static final String TABLE_SQL_DROP = "drop table AMImageEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_868E8C82 on AMImageEntry (companyId, configurationUuid[$COLUMN_LENGTH:75$])",
		"create unique index IX_C1EE916F on AMImageEntry (configurationUuid[$COLUMN_LENGTH:75$], fileVersionId)",
		"create index IX_E879919E on AMImageEntry (fileVersionId)",
		"create index IX_65AB1EA1 on AMImageEntry (groupId)",
		"create index IX_257F1DDD on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_A0FF779F on AMImageEntry (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}