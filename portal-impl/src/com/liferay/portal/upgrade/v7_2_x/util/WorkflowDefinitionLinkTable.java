/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade.v7_2_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class WorkflowDefinitionLinkTable {

	public static final String TABLE_NAME = "WorkflowDefinitionLink";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"workflowDefinitionLinkId", Types.BIGINT}, {"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"classNameId", Types.BIGINT},
		{"classPK", Types.BIGINT}, {"typePK", Types.BIGINT},
		{"workflowDefinitionName", Types.VARCHAR},
		{"workflowDefinitionVersion", Types.INTEGER}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("workflowDefinitionLinkId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("classNameId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("typePK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("workflowDefinitionName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("workflowDefinitionVersion", Types.INTEGER);

}
	public static final String TABLE_SQL_CREATE =
"create table WorkflowDefinitionLink (mvccVersion LONG default 0 not null,workflowDefinitionLinkId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,typePK LONG,workflowDefinitionName VARCHAR(75) null,workflowDefinitionVersion INTEGER)";

	public static final String TABLE_SQL_DROP =
"drop table WorkflowDefinitionLink";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_A4DB1F0F on WorkflowDefinitionLink (companyId, workflowDefinitionName[$COLUMN_LENGTH:75$], workflowDefinitionVersion)",
		"create index IX_705B40EE on WorkflowDefinitionLink (groupId, companyId, classNameId, classPK, typePK)"
	};

}