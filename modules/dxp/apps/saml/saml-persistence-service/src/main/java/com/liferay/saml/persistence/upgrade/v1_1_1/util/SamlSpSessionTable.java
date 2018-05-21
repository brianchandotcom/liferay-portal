/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.upgrade.v1_1_1.util;

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
		{"nameIdNameQualifier", Types.VARCHAR},
		{"nameIdSPNameQualifier", Types.VARCHAR},
		{"nameIdValue", Types.VARCHAR}, {"sessionIndex", Types.VARCHAR},
		{"terminated_", Types.BOOLEAN}
	};

	public static final String TABLE_NAME = "SamlSpSession";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create unique index IX_C66E4319 on SamlSpSession (samlSpSessionKey)",
		"create index IX_2001B382 on SamlSpSession (sessionIndex)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlSpSession (samlSpSessionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlSpSessionKey VARCHAR(75) null,assertionXml TEXT null,jSessionId VARCHAR(200) null,nameIdFormat VARCHAR(1024) null,nameIdNameQualifier VARCHAR(1024) null,nameIdSPNameQualifier VARCHAR(1024) null,nameIdValue VARCHAR(1024) null,sessionIndex VARCHAR(75) null,terminated_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpSession";

}