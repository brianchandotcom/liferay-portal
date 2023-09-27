/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.storage.internal.upgrade.v1_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class AuditEventTable {

	public static final String TABLE_NAME = "Audit_AuditEvent";

	public static final Object[][] TABLE_COLUMNS = {
		{"auditEventId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"eventType", Types.VARCHAR},
		{"className", Types.VARCHAR}, {"classPK", Types.VARCHAR},
		{"message", Types.VARCHAR}, {"clientHost", Types.VARCHAR},
		{"clientIP", Types.VARCHAR}, {"serverName", Types.VARCHAR},
		{"serverPort", Types.INTEGER}, {"sessionID", Types.VARCHAR},
		{"additionalInfo", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("auditEventId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("eventType", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("className", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("classPK", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("message", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("clientHost", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("clientIP", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("serverName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("serverPort", Types.INTEGER);

TABLE_COLUMNS_MAP.put("sessionID", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("additionalInfo", Types.CLOB);

}
	public static final String TABLE_SQL_CREATE =
"create table Audit_AuditEvent (auditEventId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(200) null,createDate DATE null,eventType VARCHAR(75) null,className VARCHAR(200) null,classPK VARCHAR(75) null,message STRING null,clientHost VARCHAR(255) null,clientIP VARCHAR(255) null,serverName VARCHAR(255) null,serverPort INTEGER,sessionID VARCHAR(255) null,additionalInfo TEXT null)";

	public static final String TABLE_SQL_DROP = "drop table Audit_AuditEvent";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}