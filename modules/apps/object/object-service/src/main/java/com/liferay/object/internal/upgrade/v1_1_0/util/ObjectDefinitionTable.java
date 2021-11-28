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

package com.liferay.object.internal.upgrade.v1_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Marco Leo
 * @generated
 */
public class ObjectDefinitionTable {

	public static final String TABLE_NAME = "ObjectDefinition";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"uuid_", Types.VARCHAR},
		{"objectDefinitionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"descriptionObjectFieldId", Types.BIGINT},
		{"titleObjectFieldId", Types.BIGINT}, {"active_", Types.BOOLEAN},
		{"dbTableName", Types.VARCHAR}, {"label", Types.VARCHAR},
		{"className", Types.VARCHAR}, {"name", Types.VARCHAR},
		{"panelAppOrder", Types.VARCHAR}, {"panelCategoryKey", Types.VARCHAR},
		{"pkObjectFieldDBColumnName", Types.VARCHAR},
		{"pkObjectFieldName", Types.VARCHAR}, {"pluralLabel", Types.VARCHAR},
		{"portlet", Types.BOOLEAN}, {"scope", Types.VARCHAR},
		{"system_", Types.BOOLEAN}, {"version", Types.INTEGER},
		{"status", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("objectDefinitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("descriptionObjectFieldId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("titleObjectFieldId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("dbTableName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("label", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("panelAppOrder", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("panelCategoryKey", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("pkObjectFieldDBColumnName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("pkObjectFieldName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("pluralLabel", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("portlet", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("scope", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("version", Types.INTEGER);

TABLE_COLUMNS_MAP.put("status", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table ObjectDefinition (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,objectDefinitionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,descriptionObjectFieldId LONG,titleObjectFieldId LONG,active_ BOOLEAN,dbTableName VARCHAR(75) null,label STRING null,className VARCHAR(75) null,name VARCHAR(75) null,panelAppOrder VARCHAR(75) null,panelCategoryKey VARCHAR(75) null,pkObjectFieldDBColumnName VARCHAR(75) null,pkObjectFieldName VARCHAR(75) null,pluralLabel STRING null,portlet BOOLEAN,scope VARCHAR(75) null,system_ BOOLEAN,version INTEGER,status INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table ObjectDefinition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5C293E0D on ObjectDefinition (companyId, active_, system_, status)",
		"create index IX_2A008543 on ObjectDefinition (companyId, className[$COLUMN_LENGTH:75$])",
		"create index IX_3E56F38F on ObjectDefinition (companyId, name[$COLUMN_LENGTH:75$])",
		"create index IX_55C39BCE on ObjectDefinition (system_, status)",
		"create index IX_B929D94C on ObjectDefinition (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}