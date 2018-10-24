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
public class KaleoNotificationTable {

	public static final String TABLE_NAME = "KaleoNotification";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoNotificationId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoClassName", Types.VARCHAR},
		{"kaleoClassPK", Types.BIGINT},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNodeName", Types.VARCHAR},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"executionType", Types.VARCHAR},
		{"template", Types.CLOB},
		{"templateLanguage", Types.VARCHAR},
		{"notificationTypes", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoNotificationId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("kaleoClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNodeName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("executionType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("template", Types.CLOB);

TABLE_COLUMNS_MAP.put("templateLanguage", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("notificationTypes", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoNotification (kaleoNotificationId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoClassName VARCHAR(200) null,kaleoClassPK LONG,kaleoDefinitionVersionId LONG,kaleoNodeName VARCHAR(200) null,name VARCHAR(200) null,description STRING null,executionType VARCHAR(20) null,template TEXT null,templateLanguage VARCHAR(75) null,notificationTypes VARCHAR(25) null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoNotification";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_38829497 on KaleoNotification (companyId)",
		"create index IX_F3362E93 on KaleoNotification (kaleoClassName[$COLUMN_LENGTH:200$], kaleoClassPK, executionType[$COLUMN_LENGTH:20$])",
		"create index IX_B8486585 on KaleoNotification (kaleoDefinitionVersionId)"
	};

}