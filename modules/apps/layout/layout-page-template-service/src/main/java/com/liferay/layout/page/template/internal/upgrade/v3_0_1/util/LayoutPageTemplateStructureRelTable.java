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

package com.liferay.layout.page.template.internal.upgrade.v3_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class LayoutPageTemplateStructureRelTable {

	public static final String TABLE_NAME = "LayoutPageTemplateStructureRel";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR}, {"lPageTemplateStructureRelId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"layoutPageTemplateStructureId", Types.BIGINT},
		{"segmentsExperienceId", Types.BIGINT}, {"data_", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("lPageTemplateStructureRelId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("layoutPageTemplateStructureId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("segmentsExperienceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("data_", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE =
"create table LayoutPageTemplateStructureRel (uuid_ VARCHAR(75) null,lPageTemplateStructureRelId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,layoutPageTemplateStructureId LONG,segmentsExperienceId LONG,data_ TEXT null)";

	public static final String TABLE_SQL_DROP =
"drop table LayoutPageTemplateStructureRel";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_BB165B45 on LayoutPageTemplateStructureRel (layoutPageTemplateStructureId, segmentsExperienceId)",
		"create index IX_12808938 on LayoutPageTemplateStructureRel (segmentsExperienceId)",
		"create index IX_6F8B3413 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_2467A355 on LayoutPageTemplateStructureRel (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}