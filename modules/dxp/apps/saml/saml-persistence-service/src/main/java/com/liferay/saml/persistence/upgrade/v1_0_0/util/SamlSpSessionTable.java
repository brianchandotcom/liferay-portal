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

package com.liferay.saml.persistence.upgrade.v1_0_0.util;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class SamlSpSessionTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpSessionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"jSessionId", Types.VARCHAR}, {"nameIdFormat", Types.VARCHAR},
		{"nameIdValue", Types.VARCHAR}, {"terminated_", Types.BOOLEAN}
	};

	public static final String TABLE_NAME = "SamlSpSession";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_85F532ED on SamlSpSession (jSessionId)",
		"create index IX_1040A689 on SamlSpSession (nameIdValue)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlSpSession (samlSpSessionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,jSessionId VARCHAR(75) null,nameIdFormat VARCHAR(75) null,nameIdValue VARCHAR(75) null,terminated_ BOOLEAN)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpSession";

}