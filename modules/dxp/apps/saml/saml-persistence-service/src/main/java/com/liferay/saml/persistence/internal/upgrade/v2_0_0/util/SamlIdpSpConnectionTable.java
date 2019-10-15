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

package com.liferay.saml.persistence.internal.upgrade.v2_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	  Brian Wing Shun Chan
 * @generated
 */
public class SamlIdpSpConnectionTable {

	public static final String TABLE_NAME = "SamlIdpSpConnection";

	public static final Object[][] TABLE_COLUMNS = {
		{"samlIdpSpConnectionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"samlSpEntityId", Types.VARCHAR}, {"assertionLifetime", Types.INTEGER},
		{"attributeNames", Types.VARCHAR}, {"attributesEnabled", Types.BOOLEAN},
		{"attributesNamespaceEnabled", Types.BOOLEAN},
		{"enabled", Types.BOOLEAN}, {"encryptionForced", Types.BOOLEAN},
		{"metadataUrl", Types.VARCHAR}, {"metadataXml", Types.CLOB},
		{"metadataUpdatedDate", Types.TIMESTAMP}, {"name", Types.VARCHAR},
		{"nameIdAttribute", Types.VARCHAR}, {"nameIdFormat", Types.VARCHAR}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
new HashMap<String, Integer>();

static {
TABLE_COLUMNS_MAP.put("samlIdpSpConnectionId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);

TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("samlSpEntityId", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("assertionLifetime", Types.INTEGER);

TABLE_COLUMNS_MAP.put("attributeNames", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("attributesEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("attributesNamespaceEnabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("enabled", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("encryptionForced", Types.BOOLEAN);

TABLE_COLUMNS_MAP.put("metadataUrl", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("metadataXml", Types.CLOB);

TABLE_COLUMNS_MAP.put("metadataUpdatedDate", Types.TIMESTAMP);

TABLE_COLUMNS_MAP.put("name", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("nameIdAttribute", Types.VARCHAR);

TABLE_COLUMNS_MAP.put("nameIdFormat", Types.VARCHAR);

}
	public static final String TABLE_SQL_CREATE =
"create table SamlIdpSpConnection (samlIdpSpConnectionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlSpEntityId VARCHAR(1024) null,assertionLifetime INTEGER,attributeNames STRING null,attributesEnabled BOOLEAN,attributesNamespaceEnabled BOOLEAN,enabled BOOLEAN,encryptionForced BOOLEAN,metadataUrl VARCHAR(1024) null,metadataXml TEXT null,metadataUpdatedDate DATE null,name VARCHAR(75) null,nameIdAttribute VARCHAR(1024) null,nameIdFormat VARCHAR(1024) null)";

	public static final String TABLE_SQL_DROP =
"drop table SamlIdpSpConnection";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_87463CFB on SamlIdpSpConnection (companyId, samlSpEntityId[$COLUMN_LENGTH:1024$])"
	};

}