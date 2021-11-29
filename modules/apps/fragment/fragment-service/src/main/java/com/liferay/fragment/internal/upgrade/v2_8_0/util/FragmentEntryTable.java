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
public class FragmentEntryTable {

	public static final String TABLE_NAME = "FragmentEntry";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"headId", Types.BIGINT},
		{"head", Types.BOOLEAN}, {"fragmentEntryId", Types.BIGINT},
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

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("headId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("head", Types.BOOLEAN);

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
"create table FragmentEntry (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,headId LONG,head BOOLEAN,fragmentEntryId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,fragmentCollectionId LONG,fragmentEntryKey VARCHAR(75) null,name VARCHAR(75) null,css TEXT null,html TEXT null,js TEXT null,cacheable BOOLEAN,configuration TEXT null,icon VARCHAR(75) null,previewFileEntryId LONG,readOnly BOOLEAN,type_ INTEGER,lastPublishDate DATE null,status INTEGER,statusByUserId LONG,statusByUserName VARCHAR(75) null,statusDate DATE null,primary key (fragmentEntryId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table FragmentEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_18964A03 on FragmentEntry (fragmentCollectionId, ctCollectionId)",
		"create index IX_3E323417 on FragmentEntry (fragmentCollectionId, head, ctCollectionId)",
		"create index IX_C25228E0 on FragmentEntry (groupId, ctCollectionId)",
		"create index IX_96DEA1DD on FragmentEntry (groupId, fragmentCollectionId, ctCollectionId)",
		"create index IX_30ABC071 on FragmentEntry (groupId, fragmentCollectionId, head, ctCollectionId)",
		"create index IX_CEAAB85C on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_66C6BBB0 on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], head, ctCollectionId)",
		"create index IX_49BF8D42 on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status, ctCollectionId)",
		"create index IX_72C5CC16 on FragmentEntry (groupId, fragmentCollectionId, name[$COLUMN_LENGTH:75$], status, head, ctCollectionId)",
		"create index IX_2E7665C3 on FragmentEntry (groupId, fragmentCollectionId, status, ctCollectionId)",
		"create index IX_60FE7FD7 on FragmentEntry (groupId, fragmentCollectionId, status, head, ctCollectionId)",
		"create index IX_2A7A0DD4 on FragmentEntry (groupId, fragmentCollectionId, type_, ctCollectionId)",
		"create index IX_A8EE1728 on FragmentEntry (groupId, fragmentCollectionId, type_, head, ctCollectionId)",
		"create index IX_9F7DEABA on FragmentEntry (groupId, fragmentCollectionId, type_, status, ctCollectionId)",
		"create index IX_71762F8E on FragmentEntry (groupId, fragmentCollectionId, type_, status, head, ctCollectionId)",
		"create index IX_67941F11 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_F5386A5 on FragmentEntry (groupId, fragmentEntryKey[$COLUMN_LENGTH:75$], head, ctCollectionId)",
		"create index IX_C1D0B934 on FragmentEntry (groupId, head, ctCollectionId)",
		"create unique index IX_A7F77355 on FragmentEntry (headId, ctCollectionId)",
		"create index IX_5615117A on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_7445464E on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], companyId, head, ctCollectionId)",
		"create index IX_D2EC1FEA on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_4606AFC on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)",
		"create unique index IX_E5D3F650 on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], groupId, head, ctCollectionId)",
		"create index IX_6FD3C0BE on FragmentEntry (uuid_[$COLUMN_LENGTH:75$], head, ctCollectionId)"
	};

}