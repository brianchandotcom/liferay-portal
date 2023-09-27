/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class KaleoTaskAssignmentTable {

	public static final String TABLE_NAME = "KaleoTaskAssignment";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoTaskAssignmentId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR},
		{"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeId", Types.BIGINT},
		{"assigneeClassName", Types.VARCHAR},
		{"assigneeClassPK", Types.BIGINT},
		{"assigneeActionId", Types.VARCHAR},
		{"assigneeScript", Types.CLOB},
		{"assigneeScriptLanguage", Types.VARCHAR},
		{"assigneeScriptRequiredContexts", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoTaskAssignmentId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("assigneeClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assigneeClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("assigneeActionId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assigneeScript", Types.CLOB);

TABLE_COLUMNS_MAP.put("assigneeScriptLanguage", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assigneeScriptRequiredContexts", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoTaskAssignment (kaleoTaskAssignmentId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionVersionId LONG,kaleoNodeId LONG,assigneeClassName VARCHAR(200) null,assigneeClassPK LONG,assigneeActionId VARCHAR(75) null,assigneeScript TEXT null,assigneeScriptLanguage VARCHAR(75) null,assigneeScriptRequiredContexts STRING null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTaskAssignment";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_611732B0 on KaleoTaskAssignment (companyId)",
		"create index IX_1087068E on KaleoTaskAssignment (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, assigneeClassName[$COLUMN_LENGTH:200$])",
		"create index IX_E362B24C on KaleoTaskAssignment (kaleoDefinitionVersionId)"
	};

}