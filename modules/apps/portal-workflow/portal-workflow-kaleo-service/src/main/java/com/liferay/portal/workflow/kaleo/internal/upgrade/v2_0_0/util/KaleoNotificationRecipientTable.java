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
public class KaleoNotificationRecipientTable {

	public static final String TABLE_NAME = "KaleoNotificationRecipient";

	public static final Object[][] TABLE_COLUMNS = {
		{"kaleoNotificationRecipientId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"kaleoDefinitionVersionId", Types.BIGINT},
		{"kaleoNotificationId", Types.BIGINT},
		{"recipientClassName", Types.VARCHAR},
		{"recipientClassPK", Types.BIGINT},
		{"recipientRoleType", Types.INTEGER},
		{"recipientScript", Types.CLOB},
		{"recipientScriptLanguage", Types.VARCHAR},
		{"recipientScriptContexts", Types.VARCHAR},
		{"address", Types.VARCHAR},
		{"notificationReceptionType", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("kaleoNotificationRecipientId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("kaleoDefinitionVersionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("kaleoNotificationId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recipientClassName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("recipientClassPK", Types.BIGINT);

TABLE_COLUMNS_MAP.put("recipientRoleType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("recipientScript", Types.CLOB);

TABLE_COLUMNS_MAP.put("recipientScriptLanguage", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("recipientScriptContexts", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("address", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("notificationReceptionType", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table KaleoNotificationRecipient (kaleoNotificationRecipientId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,modifiedDate DATE null,kaleoDefinitionVersionId LONG,kaleoNotificationId LONG,recipientClassName VARCHAR(200) null,recipientClassPK LONG,recipientRoleType INTEGER,recipientScript TEXT null,recipientScriptLanguage VARCHAR(75) null,recipientScriptContexts STRING null,address VARCHAR(255) null,notificationReceptionType VARCHAR(3) null)";

	public static final String TABLE_SQL_DROP = "drop table KaleoNotificationRecipient";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_2C8C4AF4 on KaleoNotificationRecipient (companyId)",
		"create index IX_B6D98988 on KaleoNotificationRecipient (kaleoDefinitionVersionId)",
		"create index IX_7F4FED02 on KaleoNotificationRecipient (kaleoNotificationId)"
	};

}