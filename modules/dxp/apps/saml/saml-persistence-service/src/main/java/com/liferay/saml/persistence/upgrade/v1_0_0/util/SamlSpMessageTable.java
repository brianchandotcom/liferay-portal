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