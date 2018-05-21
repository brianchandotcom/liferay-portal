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