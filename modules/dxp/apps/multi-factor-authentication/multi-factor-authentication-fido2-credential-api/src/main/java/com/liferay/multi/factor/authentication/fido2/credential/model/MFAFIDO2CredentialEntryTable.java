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

package com.liferay.multi.factor.authentication.fido2.credential.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MFAFIDO2CredentialEntry&quot; database table.
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntry
 * @generated
 */
public class MFAFIDO2CredentialEntryTable
	extends BaseTable<MFAFIDO2CredentialEntryTable> {

	public static final MFAFIDO2CredentialEntryTable INSTANCE =
		new MFAFIDO2CredentialEntryTable();

	public final Column<MFAFIDO2CredentialEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MFAFIDO2CredentialEntryTable, Long>
		mfaFIDO2CredentialEntryId = createColumn(
			"mfaFIDO2CredentialEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<MFAFIDO2CredentialEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, String> credentialKey =
		createColumn(
			"credentialKey", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, Integer> credentialType =
		createColumn(
			"credentialType", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, Integer> failedAttempts =
		createColumn(
			"failedAttempts", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, String> publicKeyCOSE =
		createColumn(
			"publicKeyCOSE", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MFAFIDO2CredentialEntryTable, Long> signatureCount =
		createColumn(
			"signatureCount", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private MFAFIDO2CredentialEntryTable() {
		super("MFAFIDO2CredentialEntry", MFAFIDO2CredentialEntryTable::new);
	}

}