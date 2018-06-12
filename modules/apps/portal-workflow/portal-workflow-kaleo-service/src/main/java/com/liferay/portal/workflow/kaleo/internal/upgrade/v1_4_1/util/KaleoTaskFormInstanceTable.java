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
public class KaleoTaskFormInstanceTable {

	public static final String TABLE_NAME = "KaleoTaskFormInstance";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoTaskFormInstanceId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoInstanceId", Types.BIGINT},
		{"kaleoTaskId", Types.BIGINT},
		{"kaleoTaskInstanceTokenId", Types.BIGINT},
		{"kaleoTaskFormId", Types.BIGINT},
		{"formValues", Types.VARCHAR},
		{"formValueEntryGroupId", Types.BIGINT},
		{"formValueEntryId", Types.BIGINT},
		{"formValueEntryUuid", Types.VARCHAR},
		{"metadata", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoTaskFormInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoInstanceId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskInstanceTokenId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoTaskFormId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formValues", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("formValueEntryGroupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formValueEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("formValueEntryUuid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("metadata", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoTaskFormInstance (kaleoTaskFormInstanceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoInstanceId LONG,kaleoTaskId LONG,kaleoTaskInstanceTokenId LONG,kaleoTaskFormId LONG,formValues STRING null,formValueEntryGroupId LONG,formValueEntryId LONG,formValueEntryUuid VARCHAR(75) null,metadata STRING null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoTaskFormInstance";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_77B26CC4 on KaleoTaskFormInstance (companyId)",
		"create index IX_F118DB8 on KaleoTaskFormInstance (kaleoDefinitionVersionId)",
		"create index IX_FF271E7C on KaleoTaskFormInstance (kaleoInstanceId)",
		"create index IX_E7F42BD0 on KaleoTaskFormInstance (kaleoTaskFormId)",
		"create index IX_2A86346C on KaleoTaskFormInstance (kaleoTaskId)",
		"create index IX_2C81C992 on KaleoTaskFormInstance (kaleoTaskInstanceTokenId)"
	};

}