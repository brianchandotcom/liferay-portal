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

package com.liferay.portal.upgrade.v7_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class UserNotificationEventTable {

	public static final String TABLE_NAME = "UserNotificationEvent";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"userNotificationEventId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"type_", Types.VARCHAR},
		{"timestamp", Types.BIGINT},
		{"deliveryType", Types.INTEGER},
		{"deliverBy", Types.BIGINT},
		{"delivered", Types.BOOLEAN},
		{"payload", Types.CLOB},
		{"actionRequired", Types.BOOLEAN},
		{"archived", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("userNotificationEventId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("type_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("timestamp", Types.BIGINT);

TABLE_COLUMNS_MAP.put("deliveryType", Types.INTEGER);

TABLE_COLUMNS_MAP.put("deliverBy", Types.BIGINT);

TABLE_COLUMNS_MAP.put("delivered", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("payload", Types.CLOB);

TABLE_COLUMNS_MAP.put("actionRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("archived", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE = "create table UserNotificationEvent (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,userNotificationEventId LONG not null primary key,companyId LONG,userId LONG,type_ VARCHAR(75) null,timestamp LONG,deliveryType INTEGER,deliverBy LONG,delivered BOOLEAN,payload TEXT null,actionRequired BOOLEAN,archived BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table UserNotificationEvent";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_BF29100B on UserNotificationEvent (type_[$COLUMN_LENGTH:75$])",
		"create index IX_5CE95F03 on UserNotificationEvent (userId, actionRequired, archived)",
		"create index IX_3DBB361A on UserNotificationEvent (userId, archived)",
		"create index IX_E32CC19 on UserNotificationEvent (userId, delivered, actionRequired)",
		"create index IX_C4EFBD45 on UserNotificationEvent (userId, deliveryType, actionRequired, archived)",
		"create index IX_A87A585C on UserNotificationEvent (userId, deliveryType, archived)",
		"create index IX_A6F83617 on UserNotificationEvent (userId, deliveryType, delivered, actionRequired)",
		"create index IX_8FB65EC1 on UserNotificationEvent (userId, type_[$COLUMN_LENGTH:75$], deliveryType, delivered)",
		"create index IX_A6BAFDFE on UserNotificationEvent (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}