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

package com.liferay.saml.persistence.upgrade.v1_1_0.util;

import java.sql.Types;

/**
 * @author Mika Koivisto
 */
public class SamlSpSessionTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpSessionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"samlSpSessionKey", Types.VARCHAR}, {"assertionXml", Types.CLOB},
		{"jSessionId", Types.VARCHAR}, {"nameIdFormat", Types.VARCHAR},
		{"nameIdValue", Types.VARCHAR}, {"sessionIndex", Types.VARCHAR},
		{"terminated_", Types.BOOLEAN}
	};

	public static final String TABLE_NAME = "SamlSpSession";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_C66E4319 on SamlSpSession (samlSpSessionKey)",
		"create index IX_2001B382 on SamlSpSession (sessionIndex)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlSpSession (samlSpSessionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlSpSessionKey VARCHAR(75) null,assertionXml TEXT null,jSessionId VARCHAR(75) null,nameIdFormat VARCHAR(1024) null,nameIdValue VARCHAR(1024) null,sessionIndex VARCHAR(75) null,terminated_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpSession";

}