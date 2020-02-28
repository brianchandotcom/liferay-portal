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

package com.liferay.multi.factor.authentication.email.otp.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;MFAEmailOTPEntry&quot; database table.
 *
 * @author Arthur Chan
 * @see MFAEmailOTPEntry
 * @generated
 */
public class MFAEmailOTPEntryTable extends BaseTable<MFAEmailOTPEntryTable> {

	public static final MFAEmailOTPEntryTable INSTANCE =
		new MFAEmailOTPEntryTable();

	public final Column<MFAEmailOTPEntryTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<MFAEmailOTPEntryTable, Long> mfaEmailOTPEntryId =
		createColumn(
			"mfaEmailOTPEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<MFAEmailOTPEntryTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, Integer> failedAttempts =
		createColumn(
			"failedAttempts", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, Date> lastFailDate =
		createColumn(
			"lastFailDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, String> lastFailIP =
		createColumn(
			"lastFailIP", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, Date> lastSuccessDate =
		createColumn(
			"lastSuccessDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<MFAEmailOTPEntryTable, String> lastSuccessIP =
		createColumn(
			"lastSuccessIP", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private MFAEmailOTPEntryTable() {
		super("MFAEmailOTPEntry", MFAEmailOTPEntryTable::new);
	}

}