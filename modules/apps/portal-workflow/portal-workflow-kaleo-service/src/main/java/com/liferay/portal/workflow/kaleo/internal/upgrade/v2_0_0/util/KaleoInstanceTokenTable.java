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
public class KaleoInstanceTokenTable {

	public static final String TABLE_NAME = "KaleoInstanceToken";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoInstanceTokenId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"parentKaleoInstanceTokenId", Types.BIGINT},
		{"currentKaleoNodeId", Types.BIGINT},
		{"currentKaleoNodeName", Types.VARCHAR},
		{"className", Types.VARCHAR},
		{"classPK", Types.BIGINT},
		{"completed", Types.BOOLEAN},
		{"completionDate", Types.TIMESTAMP}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("parentKaleoInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("currentKaleoNodeId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("currentKaleoNodeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoInstanceToken (kaleoInstanceTokenId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,parentKaleoInstanceTokenId LONG,currentKaleoNodeId LONG,currentKaleoNodeName VARCHAR(200) null,className VARCHAR(200) null,classPK LONG,completed BOOLEAN,completionDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoInstanceToken";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_360D34D9 on KaleoInstanceToken (companyId, parentKaleoInstanceTokenId, completionDate)",
		"create index IX_1181057E on KaleoInstanceToken (kaleoDefinitionVersionId)",
		"create index IX_F42AAFF6 on KaleoInstanceToken (kaleoInstanceId)"
	};

}