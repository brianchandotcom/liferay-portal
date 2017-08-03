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
public class SamlSpMessageTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"samlSpMessageId", Types.BIGINT}, {"companyId", Types.BIGINT},
		{"createDate", Types.TIMESTAMP}, {"samlIdpEntityId", Types.VARCHAR},
		{"samlIdpResponseKey", Types.VARCHAR},
		{"expirationDate", Types.TIMESTAMP}
	};

	public static final String TABLE_NAME = "SamlSpMessage";

	public static final String[] TABLE_SQL_ADD_INDEXES = {
		"create index IX_5615F9DD on SamlSpMessage (samlIdpEntityId, samlIdpResponseKey)"
	};

	public static final String TABLE_SQL_CREATE = "create table SamlSpMessage (samlSpMessageId LONG not null primary key,companyId LONG,createDate DATE null,samlIdpEntityId VARCHAR(75) null,samlIdpResponseKey VARCHAR(75) null,expirationDate DATE null)";

	public static final String TABLE_SQL_DROP = "drop table SamlSpMessage";

}