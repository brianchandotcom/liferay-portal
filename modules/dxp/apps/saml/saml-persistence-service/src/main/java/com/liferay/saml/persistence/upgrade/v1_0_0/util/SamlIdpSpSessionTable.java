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
public class SamlIdpSpSessionTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"samlIdpSpSessionId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"userId", Types.BIGINT}, {"userName", Types.VARCHAR},
		{"createDate", Types.TIMESTAMP}, {"modifiedDate", Types.TIMESTAMP},
		{"samlIdpSsoSessionId", Types.BIGINT},
		{"samlSpEntityId", Types.VARCHAR}, {"nameIdFormat", Types.VARCHAR},
		{"nameIdValue", Types.VARCHAR}
	};

	public static final String TABLE_NAME = "SamlIdpSpSession";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_8EDF9D43 on SamlIdpSpSession (samlIdpSsoSessionId)",
		"create index IX_F2B40CDF on SamlIdpSpSession (samlIdpSsoSessionId, samlSpEntityId)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlIdpSpSession (samlIdpSpSessionId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,samlIdpSsoSessionId LONG,samlSpEntityId VARCHAR(75) null,nameIdFormat VARCHAR(75) null,nameIdValue VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table SamlIdpSpSession";

}