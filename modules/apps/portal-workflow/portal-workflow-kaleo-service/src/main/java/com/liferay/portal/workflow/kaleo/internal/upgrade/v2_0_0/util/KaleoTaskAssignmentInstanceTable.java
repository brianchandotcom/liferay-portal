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
public class KaleoTaskAssignmentInstanceTable {

	public static final String TABLE_NAME = "KaleoTaskAssignmentInstance";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoTaskAssignmentInstanceId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoInstanceTokenId", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT},
		{"kaleoTaskId", Types.BIGINT},
		{"kaleoTaskName", Types.VARCHAR},
		{"assigneeClassName", Types.VARCHAR},
		{"assigneeClassPK", Types.BIGINT},
		{"completed", Types.BOOLEAN},
		{"completionDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoTaskAssignmentInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assigneeClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assigneeClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoTaskAssignmentInstance (kaleoTaskAssignmentInstanceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskInstanceTokenId LONG,kaleoTaskId LONG,kaleoTaskName VARCHAR(200) null,assigneeClassName VARCHAR(200) null,assigneeClassPK LONG,completed BOOLEAN,completionDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTaskAssignmentInstance";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_3BD436FD on KaleoTaskAssignmentInstance (assigneeClassName[$COLUMN_LENGTH:200$], assigneeClassPK)",
		"create index IX_6E3CDA1B on KaleoTaskAssignmentInstance (companyId)",
		"create index IX_38A47B17 on KaleoTaskAssignmentInstance (groupId, assigneeClassPK)",
		"create index IX_B751E781 on KaleoTaskAssignmentInstance (kaleoDefinitionVersionId)",
		"create index IX_67A9EE93 on KaleoTaskAssignmentInstance (kaleoInstanceId)",
		"create index IX_D4C2235B on KaleoTaskAssignmentInstance (kaleoTaskInstanceTokenId)"
	};

}