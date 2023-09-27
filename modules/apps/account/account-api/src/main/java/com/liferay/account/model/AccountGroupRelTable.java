/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;AccountGroupRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AccountGroupRel
 * @generated
 */
public class AccountGroupRelTable extends BaseTable<AccountGroupRelTable> {

	public static final AccountGroupRelTable INSTANCE =
		new AccountGroupRelTable();

	public final Column<AccountGroupRelTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountGroupRelTable, Long> accountGroupRelId =
		createColumn(
			"accountGroupRelId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AccountGroupRelTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Long> userId = createColumn(
		"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, String> userName = createColumn(
		"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Date> createDate = createColumn(
		"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Date> modifiedDate = createColumn(
		"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Long> accountGroupId =
		createColumn(
			"accountGroupId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Long> classNameId = createColumn(
		"classNameId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountGroupRelTable, Long> classPK = createColumn(
		"classPK", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AccountGroupRelTable() {
		super("AccountGroupRel", AccountGroupRelTable::new);
	}

}