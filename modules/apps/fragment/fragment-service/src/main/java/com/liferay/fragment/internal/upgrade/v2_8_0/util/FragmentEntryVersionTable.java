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

package com.liferay.fragment.internal.upgrade.v2_8_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryVersionTable {

	public static final String TABLE_NAME = "FragmentEntryVersion";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"fragmentEntryVersionId", Types.BIGINT}, {"version", Types.INTEGER},
		{"uuid_", Types.VARCHAR}, {"fragmentEntryId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"fragmentCollectionId", Types.BIGINT},
		{"fragmentEntryKey", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"css", Types.CLOB}, {"html", Types.CLOB}, {"js", Types.CLOB},
		{"cacheable", Types.BOOLEAN}, {"configuration", Types.CLOB},
		{"icon", Types.VARCHAR}, {"previewFileEntryId", Types.BIGINT},
		{"readOnly", Types.BOOLEAN}, {"type_", Types.INTEGER},
		{"lastPublishDate", Types.TIMESTAMP}, {"status", Types.INTEGER},
		{"statusByUserId", Types.BIGINT}, {"statusByUserName", Types.VARCHAR},
		{"statusDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fragmentEntryVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("version", Types.INTEGER);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("fragmentEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("fragmentCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fragmentEntryKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("css", Types.CLOB);

TABLE_COLUMNS_MAP.put("html", Types.CLOB);

TABLE_COLUMNS_MAP.put("js", Types.CLOB);

TABLE_COLUMNS_MAP.put("cacheable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("configuration", Types.CLOB);

TABLE_COLUMNS_MAP.put("icon", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("previewFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("readOnly", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

TABLE_COLUMNS_MAP.put("statusByUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("statusByUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("statusDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table FragmentEntryVersion (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,fragmentEntryVersionId LONG not null,version INTEGER,uuid_ VARCHAR(75) null,fragmentEntryId LONG,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fragmentCollectionId LONG,fragmentEntryKey VARCHAR(75) null,name VARCHAR(75) null,css TEXT null,html TEXT null,js TEXT null,cacheable BOOLEAN,configuration TEXT null,icon VARCHAR(75) null,previewFileEntryId LONG,readOnly BOOLEAN,type_ INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (fragmentEntryVersionId, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
"drop table FragmentEntryVersion";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_646748B7 on FragmentEntryVersion (fragmentCollectionId, ctCollectionId)",
		"create index IX_DC8A5E2D on FragmentEntryVersion (fragmentCollectionId, version, ctCollectionId)",
		"create index IX_E487E5AF on FragmentEntryVersion (fragmentEntryId, ctCollectionId)",
		"create unique index IX_E87ED835 on FragmentEntryVersion (fragmentEntryId, version, ctCollectionId)",
		"create index IX_64C555AC on FragmentEntryVersion (groupId, ctCollectionId)",
		"create index IX_36EEEDA9 on FragmentEntryVersion (groupId, fragmentCollectionId, ctCollectionId)",
		"create index IX_BD8DFB28 on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_70C9440E on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status, ctCollectionId)",
		"create index IX_342834B6 on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status, version, ctCollectionId)",
		"create index IX_5336DADC on FragmentEntryVersion (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], version, ctCollectionId)",
		"create index IX_F194258F on FragmentEntryVersion (groupId, fragmentCollectionId, status, ctCollectionId)",
		"create index IX_9C0C7455 on FragmentEntryVersion (groupId, fragmentCollectionId, status, version, ctCollectionId)",
		"create index IX_17FF2488 on FragmentEntryVersion (groupId, fragmentCollectionId, type_, ctCollectionId)",
		"create index IX_59AB0D6E on FragmentEntryVersion (groupId, fragmentCollectionId, type_, status, ctCollectionId)",
		"create index IX_FDC2F756 on FragmentEntryVersion (groupId, fragmentCollectionId, type_, status, version, ctCollectionId)",
		"create index IX_868E3D7C on FragmentEntryVersion (groupId, fragmentCollectionId, type_, version, ctCollectionId)",
		"create index IX_EBC8297B on FragmentEntryVersion (groupId, fragmentCollectionId, version, ctCollectionId)",
		"create index IX_9FDA30DD on FragmentEntryVersion (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_32C340C7 on FragmentEntryVersion (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$], version, ctCollectionId)",
		"create index IX_A18314D8 on FragmentEntryVersion (groupId, version, ctCollectionId)",
		"create index IX_EF98A2E on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_2A14D296 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], companyId, version, ctCollectionId)",
		"create index IX_619C0FB6 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_C5B380B0 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)",
		"create unique index IX_937B0E54 on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], groupId, version, ctCollectionId)",
		"create index IX_9C7C060E on FragmentEntryVersion (uuid_[$COLUMN_LENGTH:75$], version, ctCollectionId)"
	};

}