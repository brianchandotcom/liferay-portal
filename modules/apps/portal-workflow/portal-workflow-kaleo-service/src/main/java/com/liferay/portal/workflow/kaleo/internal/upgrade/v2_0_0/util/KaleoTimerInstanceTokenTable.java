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
public class KaleoTimerInstanceTokenTable {

	public static final String TABLE_NAME = "KaleoTimerInstanceToken";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoTimerInstanceTokenId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR},
		{"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoInstanceTokenId", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT},
		{"kaleoTimerId", Types.BIGINT},
		{"kaleoTimerName", Types.VARCHAR},
		{"blocking", Types.BOOLEAN},
		{"completionUserId", Types.BIGINT},
		{"completed", Types.BOOLEAN},
		{"completionDate", Types.TIMESTAMP},
		{"workflowContext", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoTimerInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTimerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTimerName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("blocking", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("completionUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("completed", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("completionDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("workflowContext", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoTimerInstanceToken (kaleoTimerInstanceTokenId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,kaleoInstanceTokenId LONG,kaleoTaskInstanceTokenId LONG,kaleoTimerId LONG,kaleoTimerName VARCHAR(200) null,blocking BOOLEAN,completionUserId LONG,completed BOOLEAN,completionDate DATE null,workflowContext TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTimerInstanceToken";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_DB96C55B on KaleoTimerInstanceToken (kaleoInstanceId)",
		"create index IX_F904A89A on KaleoTimerInstanceToken (kaleoInstanceTokenId, blocking, completed)",
		"create index IX_DB279423 on KaleoTimerInstanceToken (kaleoInstanceTokenId, completed)",
		"create index IX_13A5BA2C on KaleoTimerInstanceToken (kaleoInstanceTokenId, kaleoTimerId)"
	};

}