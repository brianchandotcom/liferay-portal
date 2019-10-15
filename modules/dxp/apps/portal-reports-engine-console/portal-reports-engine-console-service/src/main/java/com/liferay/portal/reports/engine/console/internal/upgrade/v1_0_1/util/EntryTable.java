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

package com.liferay.portal.reports.engine.console.internal.upgrade.v1_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class EntryTable {

	public static final String TABLE_NAME = "Reports_Entry";

	public static final Object[][] TABLE_COLUMNS = {
		{"entryId", Types.BIGINT},
		{"groupId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"definitionId", Types.BIGINT},
		{"format", Types.VARCHAR},
		{"scheduleRequest", Types.BOOLEAN},
		{"startDate", Types.TIMESTAMP},
		{"endDate", Types.TIMESTAMP},
		{"repeating", Types.BOOLEAN},
		{"recurrence", Types.VARCHAR},
		{"emailNotifications", Types.VARCHAR},
		{"emailDelivery", Types.VARCHAR},
		{"portletId", Types.VARCHAR},
		{"pageURL", Types.VARCHAR},
		{"reportParameters", Types.CLOB},
		{"status", Types.VARCHAR},
		{"errorMessage", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("entryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("definitionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("format", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("scheduleRequest", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("startDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("endDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("repeating", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("recurrence", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("emailNotifications", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("emailDelivery", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("portletId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("pageURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("reportParameters", Types.CLOB);

TABLE_COLUMNS_MAP.put("status", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("errorMessage", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE = "create table Reports_Entry (entryId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,definitionId LONG,format VARCHAR(75) null,scheduleRequest BOOLEAN,startDate DATE null,endDate DATE null,repeating BOOLEAN,recurrence VARCHAR(75) null,emailNotifications VARCHAR(200) null,emailDelivery VARCHAR(200) null,portletId VARCHAR(75) null,pageURL STRING null,reportParameters TEXT null,status VARCHAR(75) null,errorMessage STRING null)";

	public static final String TABLE_SQL_DROP = "drop table Reports_Entry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
	};

}