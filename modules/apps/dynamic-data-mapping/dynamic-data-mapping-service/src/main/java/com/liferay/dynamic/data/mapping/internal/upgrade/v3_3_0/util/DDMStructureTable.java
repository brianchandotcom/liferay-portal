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

package com.liferay.dynamic.data.mapping.internal.upgrade.v3_3_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Preston Crary
 * @generated
 */
public class DDMStructureTable {

	public static final String TABLE_NAME = "DDMStructure";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"structureId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"versionUserId", Types.BIGINT}, {"versionUserName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"parentStructureId", Types.BIGINT}, {"classNameId", Types.BIGINT},
		{"structureKey", Types.VARCHAR}, {"version", Types.VARCHAR},
		{"name", Types.VARCHAR}, {"description", Types.CLOB},
		{"definition", Types.CLOB}, {"storageType", Types.VARCHAR},
		{"type_", Types.INTEGER}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("structureId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("versionUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("versionUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("parentStructureId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("structureKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("version", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.CLOB);

TABLE_COLUMNS_MAP.put("definition", Types.CLOB);

TABLE_COLUMNS_MAP.put("storageType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("type_", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table DDMStructure (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,structureId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,versionUserId LONG,versionUserName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,parentStructureId LONG,classNameId LONG,structureKey VARCHAR(75) null,version VARCHAR(75) null,name STRING null,description TEXT null,definition TEXT null,storageType VARCHAR(75) null,type_ INTEGER,lastPublishDate DATE null,primary key (structureId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table DDMStructure";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_79BF4CC0 on DDMStructure (classNameId, ctCollectionId)",
		"create index IX_988632F0 on DDMStructure (companyId, classNameId, ctCollectionId)",
		"create index IX_B44FCCCA on DDMStructure (ctCollectionId)",
		"create index IX_66F194AE on DDMStructure (groupId, classNameId, ctCollectionId)",
		"create unique index IX_4CFAC78E on DDMStructure (groupId, classNameId, structureKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_1AA13A1C on DDMStructure (groupId, ctCollectionId)",
		"create index IX_48373D74 on DDMStructure (groupId, parentStructureId, ctCollectionId)",
		"create index IX_800B2006 on DDMStructure (parentStructureId, ctCollectionId)",
		"create index IX_B1169EAA on DDMStructure (structureKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_85FA3BE on DDMStructure (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_49065026 on DDMStructure (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_D1F2BE40 on DDMStructure (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}