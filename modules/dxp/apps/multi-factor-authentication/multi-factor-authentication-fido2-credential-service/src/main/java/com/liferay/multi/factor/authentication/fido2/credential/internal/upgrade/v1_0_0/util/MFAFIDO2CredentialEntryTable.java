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

package com.liferay.multi.factor.authentication.fido2.credential.internal.upgrade.v1_0_0.util;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Chan
 * @generated
 */
public class MFAFIDO2CredentialEntryTable {

	public static final Object[][] TABLE_COLUMNS = {
		{"mfaFIDO2CredentialEntryId", Types.BIGINT},
		{"companyId", Types.BIGINT}, {"userId", Types.BIGINT},
		{"userName", Types.VARCHAR}, {"createDate", Types.TIMESTAMP},
		{"modifiedDate", Types.TIMESTAMP}, {"credentialKey", Types.VARCHAR},
		{"credentialType", Types.INTEGER}, {"failedAttempts", Types.INTEGER},
		{"publicKeyCOSE", Types.VARCHAR}, {"signatureCount", Types.BIGINT}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>() {
			{
				put("mfaFIDO2CredentialEntryId", Types.BIGINT);

				put("companyId", Types.BIGINT);

				put("userId", Types.BIGINT);

				put("userName", Types.VARCHAR);

				put("createDate", Types.TIMESTAMP);

				put("modifiedDate", Types.TIMESTAMP);

				put("credentialKey", Types.VARCHAR);

				put("credentialType", Types.INTEGER);

				put("failedAttempts", Types.INTEGER);

				put("publicKeyCOSE", Types.VARCHAR);

				put("signatureCount", Types.BIGINT);
			}
		};

	public static final String TABLE_NAME =
		"MFAFIDO2CredentialEntry";

	public static final String[] TABLE_SQL_ADD_INDEXES = {};

	public static final String TABLE_SQL_CREATE =
		"create table MFAFIDO2CredentialEntry (mfaFIDO2CredentialEntryId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,credentialKey VARCHAR(128) null,credentialType INTEGER,failedAttempts INTEGER,publicKeyCOSE VARCHAR(128) null,signatureCount LONG)";

	public static final String TABLE_SQL_DROP =
		"drop table MFAFIDO2CredentialEntry";

}