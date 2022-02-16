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

package com.liferay.oauth2.provider.internal.upgrade.v4_0_1.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class OAuth2ApplicationTable {

	public static final String TABLE_NAME = "OAuth2Application";

	public static final Object[][] TABLE_COLUMNS = {
		{"oAuth2ApplicationId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"oA2AScopeAliasesId", Types.BIGINT},
		{"allowedGrantTypes", Types.VARCHAR},
		{"clientCredentialUserId", Types.BIGINT},
		{"clientCredentialUserName", Types.VARCHAR},
		{"clientId", Types.VARCHAR}, {"clientProfile", Types.INTEGER},
		{"clientSecret", Types.VARCHAR}, {"description", Types.VARCHAR},
		{"features", Types.VARCHAR}, {"homePageURL", Types.VARCHAR},
		{"iconFileEntryId", Types.BIGINT}, {"name", Types.VARCHAR},
		{"privacyPolicyURL", Types.VARCHAR}, {"redirectURIs", Types.VARCHAR},
		{"rememberDevice", Types.BOOLEAN}, {"trustedApplication", Types.BOOLEAN}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("oAuth2ApplicationId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("oA2AScopeAliasesId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("allowedGrantTypes", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("clientCredentialUserId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("clientCredentialUserName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("clientId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("clientProfile", Types.INTEGER);

TABLE_COLUMNS_MAP.put("clientSecret", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("description", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("features", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("homePageURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("iconFileEntryId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("privacyPolicyURL", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("redirectURIs", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("rememberDevice", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("trustedApplication", Types.BOOLEAN);

}
	public static final String TABLE_SQL_CREATE =
"create table OAuth2Application (oAuth2ApplicationId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,oA2AScopeAliasesId LONG,allowedGrantTypes VARCHAR(128) null,clientCredentialUserId LONG,clientCredentialUserName VARCHAR(75) null,clientId VARCHAR(75) null,clientProfile INTEGER,clientSecret VARCHAR(75) null,description STRING null,features STRING null,homePageURL STRING null,iconFileEntryId LONG,name VARCHAR(75) null,privacyPolicyURL STRING null,redirectURIs STRING null,rememberDevice BOOLEAN,trustedApplication BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table OAuth2Application";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_523E5C67 on OAuth2Application (companyId, clientId[$COLUMN_LENGTH:75$])"
	};

}