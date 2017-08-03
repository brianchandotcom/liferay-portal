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