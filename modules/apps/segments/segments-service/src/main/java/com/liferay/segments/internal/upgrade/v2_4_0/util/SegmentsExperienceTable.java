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

package com.liferay.segments.internal.upgrade.v2_4_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SegmentsExperienceTable {

	public static final String TABLE_NAME = "SegmentsExperience";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"ctCollectionId", Types.BIGINT},
		{"uuid_", Types.VARCHAR}, {"segmentsExperienceId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"segmentsEntryId", Types.BIGINT},
		{"segmentsExperienceKey", Types.VARCHAR}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"name", Types.VARCHAR},
		{"priority", Types.INTEGER}, {"active_", Types.BOOLEAN},
		{"typeSettings", Types.VARCHAR}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("segmentsExperienceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("segmentsEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("segmentsExperienceKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("typeSettings", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table SegmentsExperience (mvccVersion LONG default 0 not null,ctCollectionId LONG default 0 not null,uuid_ VARCHAR(75) null,segmentsExperienceId LONG not null,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,segmentsEntryId LONG,segmentsExperienceKey VARCHAR(75) null,classNameId LONG,classPK LONG,name STRING null,priority INTEGER,active_ BOOLEAN,typeSettings VARCHAR(75) null,lastPublishDate DATE null,primary key (segmentsExperienceId, ctCollectionId))";

	public static final String TABLE_SQL_DROP = "drop table SegmentsExperience";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_99B31F58 on SegmentsExperience (groupId, classNameId, classPK, active_, ctCollectionId)",
		"create index IX_B42F674D on SegmentsExperience (groupId, classNameId, classPK, ctCollectionId)",
		"create unique index IX_8AD425 on SegmentsExperience (groupId, classNameId, classPK, priority, ctCollectionId)",
		"create index IX_874CAE78 on SegmentsExperience (groupId, ctCollectionId)",
		"create index IX_F48D81CF on SegmentsExperience (groupId, segmentsEntryId, classNameId, classPK, active_, ctCollectionId)",
		"create index IX_D78112F6 on SegmentsExperience (groupId, segmentsEntryId, classNameId, classPK, ctCollectionId)",
		"create unique index IX_789CF949 on SegmentsExperience (groupId, segmentsExperienceKey[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create index IX_BAA8E72B on SegmentsExperience (segmentsEntryId, ctCollectionId)",
		"create index IX_BDBB56E2 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], companyId, ctCollectionId)",
		"create index IX_7F7B2B82 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], ctCollectionId)",
		"create unique index IX_3C28EA64 on SegmentsExperience (uuid_[$COLUMN_LENGTH:75$], groupId, ctCollectionId)"
	};

}