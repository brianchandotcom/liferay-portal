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

package com.liferay.portal.upgrade.v7_1_x.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class PasswordPolicyTable {

	public static final String TABLE_NAME = "PasswordPolicy";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT},
		{"uuid_", Types.VARCHAR},
		{"passwordPolicyId", Types.BIGINT},
		{"companyId", Types.BIGINT},
		{"userId", Types.BIGINT},
		{"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP},
		{"defaultPolicy", Types.BOOLEAN},
		{"name", Types.VARCHAR},
		{"description", Types.VARCHAR},
		{"changeable", Types.BOOLEAN},
		{"changeRequired", Types.BOOLEAN},
		{"minAge", Types.BIGINT},
		{"checkSyntax", Types.BOOLEAN},
		{"allowDictionaryWords", Types.BOOLEAN},
		{"minAlphanumeric", Types.INTEGER},
		{"minLength", Types.INTEGER},
		{"minLowerCase", Types.INTEGER},
		{"minNumbers", Types.INTEGER},
		{"minSymbols", Types.INTEGER},
		{"minUpperCase", Types.INTEGER},
		{"regex", Types.VARCHAR},
		{"history", Types.BOOLEAN},
		{"historyCount", Types.INTEGER},
		{"expireable", Types.BOOLEAN},
		{"maxAge", Types.BIGINT},
		{"warningTime", Types.BIGINT},
		{"graceLimit", Types.INTEGER},
		{"lockout", Types.BOOLEAN},
		{"maxFailure", Types.INTEGER},
		{"lockoutDuration", Types.BIGINT},
		{"requireUnlock", Types.BOOLEAN},
		{"resetFailureCount", Types.BIGINT},
		{"resetTicketMaxAge", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("uuid_", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("passwordPolicyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("defaultPolicy", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("changeable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("changeRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("minAge", Types.BIGINT);

TABLE_COLUMNS_MAP.put("checkSyntax", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("allowDictionaryWords", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("minAlphanumeric", Types.INTEGER);

TABLE_COLUMNS_MAP.put("minLength", Types.INTEGER);

TABLE_COLUMNS_MAP.put("minLowerCase", Types.INTEGER);

TABLE_COLUMNS_MAP.put("minNumbers", Types.INTEGER);

TABLE_COLUMNS_MAP.put("minSymbols", Types.INTEGER);

TABLE_COLUMNS_MAP.put("minUpperCase", Types.INTEGER);

TABLE_COLUMNS_MAP.put("regex", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("history", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("historyCount", Types.INTEGER);

TABLE_COLUMNS_MAP.put("expireable", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("maxAge", Types.BIGINT);

TABLE_COLUMNS_MAP.put("warningTime", Types.BIGINT);

TABLE_COLUMNS_MAP.put("graceLimit", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lockout", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("maxFailure", Types.INTEGER);

TABLE_COLUMNS_MAP.put("lockoutDuration", Types.BIGINT);

TABLE_COLUMNS_MAP.put("requireUnlock", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("resetFailureCount", Types.BIGINT);

TABLE_COLUMNS_MAP.put("resetTicketMaxAge", Types.BIGINT);

}
	public static final String TABLE_SQL_CREATE = "create table PasswordPolicy (mvccVersion LONG default 0 not null,uuid_ VARCHAR(75) null,passwordPolicyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,defaultPolicy BOOLEAN,name VARCHAR(75) null,description STRING null,changeable BOOLEAN,changeRequired BOOLEAN,minAge LONG,checkSyntax BOOLEAN,allowDictionaryWords BOOLEAN,minAlphanumeric INTEGER,minLength INTEGER,minLowerCase INTEGER,minNumbers INTEGER,minSymbols INTEGER,minUpperCase INTEGER,regex STRING null,history BOOLEAN,historyCount INTEGER,expireable BOOLEAN,maxAge LONG,warningTime LONG,graceLimit INTEGER,lockout BOOLEAN,maxFailure INTEGER,lockoutDuration LONG,requireUnlock BOOLEAN,resetFailureCount LONG,resetTicketMaxAge LONG)";

	public static final String TABLE_SQL_DROP = "drop table PasswordPolicy";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_2C1142E on PasswordPolicy (companyId, defaultPolicy)",
		"create unique index IX_3FBFA9F4 on PasswordPolicy (companyId, name[$COLUMN_LENGTH:75$])",
		"create index IX_E4D7EF87 on PasswordPolicy (uuid_[$COLUMN_LENGTH:75$], companyId)"
	};

}