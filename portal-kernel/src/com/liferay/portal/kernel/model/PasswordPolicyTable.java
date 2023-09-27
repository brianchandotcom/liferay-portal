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
 * The table class for the &quot;PasswordPolicy&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see PasswordPolicy
 * @generated
 */
public class PasswordPolicyTable extends BaseTable<PasswordPolicyTable> {

	public static final PasswordPolicyTable INSTANCE =
		new PasswordPolicyTable();

	public final Column<PasswordPolicyTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<PasswordPolicyTable, String> uuid = createColumn(
		"uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> passwordPolicyId =
		createColumn(
			"passwordPolicyId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<PasswordPolicyTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> defaultPolicy =
		createColumn(
			"defaultPolicy", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, String> name = createColumn(
		"name", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, String> description = createColumn(
		"description", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> changeable = createColumn(
		"changeable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> changeRequired =
		createColumn(
			"changeRequired", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> minAge = createColumn(
		"minAge", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> checkSyntax =
		createColumn(
			"checkSyntax", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> allowDictionaryWords =
		createColumn(
			"allowDictionaryWords", Boolean.class, Types.BOOLEAN,
			Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> minAlphanumeric =
		createColumn(
			"minAlphanumeric", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> minLength = createColumn(
		"minLength", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> minLowerCase =
		createColumn(
			"minLowerCase", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> minNumbers = createColumn(
		"minNumbers", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> minSymbols = createColumn(
		"minSymbols", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> minUpperCase =
		createColumn(
			"minUpperCase", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, String> regex = createColumn(
		"regex", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> history = createColumn(
		"history", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> historyCount =
		createColumn(
			"historyCount", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> expireable = createColumn(
		"expireable", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> maxAge = createColumn(
		"maxAge", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> warningTime = createColumn(
		"warningTime", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> graceLimit = createColumn(
		"graceLimit", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> lockout = createColumn(
		"lockout", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Integer> maxFailure = createColumn(
		"maxFailure", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> lockoutDuration =
		createColumn(
			"lockoutDuration", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Boolean> requireUnlock =
		createColumn(
			"requireUnlock", Boolean.class, Types.BOOLEAN, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> resetFailureCount =
		createColumn(
			"resetFailureCount", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<PasswordPolicyTable, Long> resetTicketMaxAge =
		createColumn(
			"resetTicketMaxAge", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private PasswordPolicyTable() {
		super("PasswordPolicy", PasswordPolicyTable::new);
	}

}