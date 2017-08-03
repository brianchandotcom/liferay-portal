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
public class SamlSpAuthRequestTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpAuthnRequestId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"samlIdpEntityId", Types.VARCHAR},
		{"samlSpAuthRequestKey", Types.VARCHAR}
	};

	public static final String TABLE_NAME = "SamlSpAuthRequest";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_10D77E09 on SamlSpAuthRequest (samlIdpEntityId, samlSpAuthRequestKey)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlSpAuthRequest (samlSpAuthnRequestId LONG not null primary key,companyId LONG,createDate DATE null,samlIdpEntityId VARCHAR(75) null,samlSpAuthRequestKey VARCHAR(75) null)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpAuthRequest";

}