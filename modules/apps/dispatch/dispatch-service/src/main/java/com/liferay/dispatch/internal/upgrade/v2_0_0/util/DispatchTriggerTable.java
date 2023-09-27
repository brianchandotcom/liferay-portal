/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Igor Beslic
 * @generated
 */
public class DispatchTriggerTable {

	public static final String TABLE_NAME = "DispatchTrigger";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"dispatchTriggerId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"active_", Types.BOOLEAN},
		{"cronExpression", Types.VARCHAR}, {"endDate", Types.TIMESTAMP},
		{"name", Types.VARCHAR}, {"startDate", Types.TIMESTAMP},
		{"system_", Types.BOOLEAN}, {"taskSettings", Types.CLOB},
		{"taskType", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("dispatchTriggerId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("active_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("cronExpression", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("system_", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("taskSettings", Types.CLOB);

TABLE_COLUMNS_MAP.put("taskType", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table DispatchTrigger (mvccVersion LONG default 0 not null,dispatchTriggerId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,active_ BOOLEAN,cronExpression VARCHAR(75) null,endDate DATE null,name VARCHAR(75) null,startDate DATE null,system_ BOOLEAN,taskSettings TEXT null,taskType VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table DispatchTrigger";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_D86DCE63 on DispatchTrigger (companyId, name[$COLUMN_LENGTH:75$])",
		"create index IX_E9AD7A37 on DispatchTrigger (companyId, taskType[$COLUMN_LENGTH:75$])"
	};

}