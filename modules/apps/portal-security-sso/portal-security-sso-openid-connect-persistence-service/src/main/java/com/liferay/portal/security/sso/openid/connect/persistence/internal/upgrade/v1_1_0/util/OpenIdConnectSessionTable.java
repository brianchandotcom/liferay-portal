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

package com.liferay.portal.security.sso.openid.connect.persistence.internal.upgrade.v1_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OpenIdConnectSessionTable {

	public static final String TABLE_NAME = "OpenIdConnectSession";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"openIdConnectSessionId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"modifiedDate", Types.TIMESTAMP}, {"accessToken", Types.VARCHAR},
		{"configurationPid", Types.VARCHAR}, {"idToken", Types.VARCHAR},
		{"providerName", Types.VARCHAR}, {"refreshToken", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);

TABLE_COLUMNS_MAP.put("openIdConnectSessionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("accessToken", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("configurationPid", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("idToken", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("providerName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("refreshToken", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table OpenIdConnectSession (mvccVersion LONG default 0 not null,openIdConnectSessionId LONG not null primary key,companyId LONG,userId LONG,modifiedDate DATE null,accessToken VARCHAR(3000) null,configurationPid VARCHAR(256) null,idToken VARCHAR(3999) null,providerName VARCHAR(75) null,refreshToken VARCHAR(2000) null)";

	public static final String TABLE_SQL_DROP =
"drop table OpenIdConnectSession";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_C00E32C1 on OpenIdConnectSession (companyId, providerName[$COLUMN_LENGTH:75$])",
		"create unique index IX_A7FC5B3A on OpenIdConnectSession (configurationPid[$COLUMN_LENGTH:256$], userId)",
		"create unique index IX_A202B8E1 on OpenIdConnectSession (userId, providerName[$COLUMN_LENGTH:75$])"
	};

}