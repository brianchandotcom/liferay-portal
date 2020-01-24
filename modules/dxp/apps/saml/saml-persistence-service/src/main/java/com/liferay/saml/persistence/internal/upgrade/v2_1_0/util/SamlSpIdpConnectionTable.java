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

package com.liferay.saml.persistence.internal.upgrade.v2_1_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SamlSpIdpConnectionTable {

	public static final String TABLE_NAME = "SamlSpIdpConnection";

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpIdpConnectionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"assertionSignatureRequired", Types.BOOLEAN},
		{"clockSkew", Types.BIGINT}, {"enabled", Types.BOOLEAN},
		{"forceAuthn", Types.BOOLEAN}, {"ldapImportEnabled", Types.BOOLEAN},
		{"metadataUpdatedDate", Types.TIMESTAMP},
		{"metadataUrl", Types.VARCHAR}, {"metadataXml", Types.CLOB},
		{"name", Types.VARCHAR}, {"nameIdFormat", Types.VARCHAR},
		{"samlIdpEntityId", Types.VARCHAR}, {"signAuthnRequest", Types.BOOLEAN},
		{"unknownUsersAreStrangers", Types.BOOLEAN},
		{"userAttributeMappings", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("samlSpIdpConnectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("assertionSignatureRequired", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("clockSkew", Types.BIGINT);

TABLE_COLUMNS_MAP.put("enabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("forceAuthn", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("ldapImportEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("metadataUpdatedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("metadataUrl", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("metadataXml", Types.CLOB);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("nameIdFormat", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("samlIdpEntityId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("signAuthnRequest", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("unknownUsersAreStrangers", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("userAttributeMappings", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table SamlSpIdpConnection (samlSpIdpConnectionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,assertionSignatureRequired BOOLEAN,clockSkew LONG,enabled BOOLEAN,forceAuthn BOOLEAN,ldapImportEnabled BOOLEAN,metadataUpdatedDate DATE null,metadataUrl VARCHAR(1024) null,metadataXml TEXT null,name VARCHAR(75) null,nameIdFormat VARCHAR(1024) null,samlIdpEntityId VARCHAR(1024) null,signAuthnRequest BOOLEAN,unknownUsersAreStrangers BOOLEAN,userAttributeMappings STRING null)";

	public static final String TABLE_SQL_DROP =
"drop table SamlSpIdpConnection";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_61204DD on SamlSpIdpConnection (companyId, samlIdpEntityId[$COLUMN_LENGTH:1024$])"
	};

}