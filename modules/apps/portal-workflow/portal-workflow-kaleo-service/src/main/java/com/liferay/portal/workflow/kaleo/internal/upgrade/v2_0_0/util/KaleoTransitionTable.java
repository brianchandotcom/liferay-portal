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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class KaleoTransitionTable {

	public static final String TABLE_NAME = "KaleoTransition";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoTransitionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeId", Types.BIGINT},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"sourceKaleoNodeId", Types.BIGINT},
		{"sourceKaleoNodeName", Types.VARCHAR},
		{"targetKaleoNodeId", Types.BIGINT},
		{"targetKaleoNodeName", Types.VARCHAR},
		{"defaultTransition", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoTransitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("sourceKaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("sourceKaleoNodeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("targetKaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("targetKaleoNodeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("defaultTransition", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoTransition (kaleoTransitionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoNodeId LONG,name VARCHAR(200) null,description STRING null,sourceKaleoNodeId LONG,sourceKaleoNodeName VARCHAR(200) null,targetKaleoNodeId LONG,targetKaleoNodeName VARCHAR(200) null,defaultTransition BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTransition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_41D6C6D on KaleoTransition (companyId)",
		"create index IX_16B426EF on KaleoTransition (kaleoDefinitionVersionId)",
		"create index IX_A38E2194 on KaleoTransition (kaleoNodeId, defaultTransition)",
		"create index IX_85268A11 on KaleoTransition (kaleoNodeId, name[$COLUMN_LENGTH:200$])"
	};

}