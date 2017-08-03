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

package com.liferay.saml.persistence.upgrade.v1_1_2.util;

import java.sql.Types;

/**
 * @author Mika Koivisto
 */
public class SamlSpIdpConnectionTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpIdpConnectionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"samlIdpEntityId", Types.VARCHAR},
		{"assertionSignatureRequired", Types.BOOLEAN},
		{"clockSkew", Types.BIGINT}, {"enabled", Types.BOOLEAN},
		{"forceAuthn", Types.BOOLEAN}, {"ldapImportEnabled", Types.BOOLEAN},
		{"metadataUrl", Types.VARCHAR}, {"metadataXml", Types.CLOB},
		{"metadataUpdatedDate", Types.TIMESTAMP}, {"name", Types.VARCHAR},
		{"nameIdFormat", Types.VARCHAR}, {"signAuthnRequest", Types.BOOLEAN},
		{"userAttributeMappings", Types.VARCHAR}
	};

	public static final String TABLE_NAME = "SamlSpIdpConnection";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_61204DD on SamlSpIdpConnection (companyId, samlIdpEntityId)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlSpIdpConnection (samlSpIdpConnectionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlIdpEntityId VARCHAR(1024) null,assertionSignatureRequired BOOLEAN,clockSkew LONG,enabled BOOLEAN,forceAuthn BOOLEAN,ldapImportEnabled BOOLEAN,metadataUrl VARCHAR(1024) null,metadataXml TEXT null,metadataUpdatedDate DATE null,name VARCHAR(75) null,nameIdFormat VARCHAR(1024) null,signAuthnRequest BOOLEAN,userAttributeMappings STRING null)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpIdpConnection";

}