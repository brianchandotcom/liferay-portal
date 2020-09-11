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

package com.liferay.commerce.product.type.virtual.internal.upgrade.v1_1_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class CPDefinitionVirtualSettingTable {

	public static final String TABLE_NAME = "CPDefinitionVirtualSetting";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"CPDefinitionVirtualSettingId", Types.BIGINT},
		{"groupId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"classNameId", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"fileEntryId", Types.BIGINT}, {"url", Types.VARCHAR},
		{"activationStatus", Types.INTEGER}, {"duration", Types.BIGINT},
		{"maxUsages", Types.INTEGER}, {"useSample", Types.BOOLEAN},
		{"sampleFileEntryId", Types.BIGINT}, {"sampleUrl", Types.VARCHAR},
		{"termsOfUseRequired", Types.BOOLEAN},
		{"termsOfUseContent", Types.VARCHAR},
		{"termsOfUseArticleResourcePK", Types.BIGINT},
		{"override", Types.BOOLEAN}, {"lastPublishDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("CPDefinitionVirtualSettingId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("url", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("activationStatus", Types.INTEGER);

TABLE_COLUMNS_MAP.put("duration", Types.BIGINT);

TABLE_COLUMNS_MAP.put("maxUsages", Types.INTEGER);

TABLE_COLUMNS_MAP.put("useSample", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("sampleFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("sampleUrl", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("termsOfUseRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("termsOfUseContent", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("termsOfUseArticleResourcePK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("override", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("lastPublishDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE =
"create table CPDefinitionVirtualSetting (uuid_ VARCHAR(75) null,CPDefinitionVirtualSettingId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,fileEntryId LONG,url VARCHAR(255) null,activationStatus INTEGER,duration LONG,maxUsages INTEGER,useSample BOOLEAN,sampleFileEntryId LONG,sampleUrl VARCHAR(255) null,termsOfUseRequired BOOLEAN,termsOfUseContent STRING null,termsOfUseArticleResourcePK LONG,override BOOLEAN,lastPublishDate DATE null)";

	public static final String TABLE_SQL_DROP =
"drop table CPDefinitionVirtualSetting";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_19B2FD20 on CPDefinitionVirtualSetting (classNameId, classPK)",
		"create index IX_F1182A3F on CPDefinitionVirtualSetting (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_8ED43481 on CPDefinitionVirtualSetting (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}