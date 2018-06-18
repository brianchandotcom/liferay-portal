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

package com.liferay.portal.workflow.kaleo.internal.upgrade.v1_4_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class KaleoConditionTable {

	public static final String TABLE_NAME = "KaleoCondition";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoConditionId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeId", Types.BIGINT},
		{"script", Types.CLOB},
		{"scriptLanguage", Types.VARCHAR},
		{"scriptRequiredContexts", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoConditionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("script", Types.CLOB);

TABLE_COLUMNS_MAP.put("scriptLanguage", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("scriptRequiredContexts", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoCondition (kaleoConditionId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoNodeId LONG,script TEXT null,scriptLanguage VARCHAR(75) null,scriptRequiredContexts STRING null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoCondition";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_FEE46067 on KaleoCondition (companyId)",
		"create index IX_353B7FB5 on KaleoCondition (kaleoDefinitionVersionId)",
		"create index IX_86CBD4C on KaleoCondition (kaleoNodeId)"
	};

}