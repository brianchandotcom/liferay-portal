/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;User_&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see User
 * @generated
 */
public class UserTable extends BaseTable<UserTable> {

	public static final UserTable INSTANCE = new UserTable();

	public final Column<UserTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<UserTable, Long> ctCollectionId = createColumn(
		"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> externalReferenceCode = createColumn(
		"externalReferenceCode", String.class, Types.VARCHAR,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<UserTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UserTable, Long> contactId = createColumn(
		"contactId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> password = createColumn(
		"password_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Boolean> passwordEncrypted = createColumn(
		"passwordEncrypted", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<UserTable, Boolean> passwordReset = createColumn(
		"passwordReset", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> passwordModifiedDate = createColumn(
		"passwordModifiedDate", Date.class, Types.TIMESTAMP,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, String> digest = createColumn(
		"digest", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> reminderQueryQuestion = createColumn(
		"reminderQueryQuestion", String.class, Types.VARCHAR,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, String> reminderQueryAnswer = createColumn(
		"reminderQueryAnswer", String.class, Types.VARCHAR,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, Integer> graceLoginCount = createColumn(
		"graceLoginCount", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> screenName = createColumn(
		"screenName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> emailAddress = createColumn(
		"emailAddress", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Long> facebookId = createColumn(
		"facebookId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> googleUserId = createColumn(
		"googleUserId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Long> ldapServerId = createColumn(
		"ldapServerId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> openId = createColumn(
		"openId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Long> portraitId = createColumn(
		"portraitId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> languageId = createColumn(
		"languageId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> timeZoneId = createColumn(
		"timeZoneId", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> greeting = createColumn(
		"greeting", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> comments = createColumn(
		"comments", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> firstName = createColumn(
		"firstName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> middleName = createColumn(
		"middleName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> lastName = createColumn(
		"lastName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> jobTitle = createColumn(
		"jobTitle", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> loginDate = createColumn(
		"loginDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> loginIP = createColumn(
		"loginIP", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> lastLoginDate = createColumn(
		"lastLoginDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UserTable, String> lastLoginIP = createColumn(
		"lastLoginIP", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> lastFailedLoginDate = createColumn(
		"lastFailedLoginDate", Date.class, Types.TIMESTAMP,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, Integer> failedLoginAttempts = createColumn(
		"failedLoginAttempts", Integer.class, Types.INTEGER,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, Boolean> lockout = createColumn(
		"lockout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<UserTable, Date> lockoutDate = createColumn(
		"lockoutDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<UserTable, Boolean> agreedToTermsOfUse = createColumn(
		"agreedToTermsOfUse", Boolean.class, Types.BOOLEAN,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, Boolean> emailAddressVerified = createColumn(
		"emailAddressVerified", Boolean.class, Types.BOOLEAN,
		Column.FLAG_DEFAULT);
	public final Column<UserTable, Integer> type = createColumn(
		"type_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<UserTable, Integer> status = createColumn(
		"status", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private UserTable() {
		super("User_", UserTable::new);
	}

}