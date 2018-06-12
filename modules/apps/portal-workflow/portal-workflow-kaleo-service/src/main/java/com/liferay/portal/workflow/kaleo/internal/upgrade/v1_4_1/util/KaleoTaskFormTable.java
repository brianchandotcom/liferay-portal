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
public class KaleoTaskFormTable {

	public static final String TABLE_NAME = "KaleoTaskForm";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoTaskFormId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeId", Types.BIGINT},
		{"kaleoTaskId", Types.BIGINT},
		{"kaleoTaskName", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"formCompanyId", Types.BIGINT},
		{"formDefinition", Types.VARCHAR},
		{"formGroupId", Types.BIGINT},
		{"formId", Types.BIGINT},
		{"formUuid", Types.VARCHAR},
		{"metadata", Types.VARCHAR},
		{"priority", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoTaskFormId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("formCompanyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formDefinition", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("formGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("metadata", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("priority", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoTaskForm (kaleoTaskFormId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoNodeId LONG,kaleoTaskId LONG,kaleoTaskName VARCHAR(200) null,name VARCHAR(200) null,description STRING null,formCompanyId LONG,formDefinition STRING null,formGroupId LONG,formId LONG,formUuid VARCHAR(75) null,metadata STRING null,priority INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTaskForm";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_EFDA7E59 on KaleoTaskForm (companyId)",
		"create index IX_3B8B7F83 on KaleoTaskForm (kaleoDefinitionVersionId)",
		"create index IX_945326BE on KaleoTaskForm (kaleoNodeId)",
		"create index IX_E38A5954 on KaleoTaskForm (kaleoTaskId, formUuid[$COLUMN_LENGTH:75$])"
	};

}