/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.forms.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class KaleoProcessTable {

	public static final String TABLE_NAME = "KaleoProcess";

	public static final Object[][] TABLE_COLUMNS = {
		{"uuid_", Types.VARCHAR},
		{"kaleoProcessId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"DDLRecordSetId", Types.BIGINT},
		{"DDMTemplateId", Types.BIGINT},
		{"workflowDefinitionName", Types.VARCHAR},
		{"workflowDefinitionVersion", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kaleoProcessId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("DDLRecordSetId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("DDMTemplateId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("workflowDefinitionName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("workflowDefinitionVersion", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoProcess (uuid_ VARCHAR(75) null,kaleoProcessId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,DDLRecordSetId LONG,DDMTemplateId LONG,workflowDefinitionName VARCHAR(75) null,workflowDefinitionVersion INTEGER)";

	public static final String TABLE_SQL_DROP = "drop table KaleoProcess";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_65CA6CC9 on KaleoProcess (DDLRecordSetId)",
		"create index IX_A29A06D5 on KaleoProcess (groupId)",
		"create index IX_C1C03029 on KaleoProcess (uuid_[$COLUMN_LENGTH:75$], companyId)",
		"create unique index IX_C6B8ACEB on KaleoProcess (uuid_[$COLUMN_LENGTH:75$], groupId)"
	};

}