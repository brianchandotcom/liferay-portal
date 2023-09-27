/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AccountRole&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AccountRole
 * @generated
 */
public class AccountRoleTable extends BaseTable<AccountRoleTable> {

	public static final AccountRoleTable INSTANCE = new AccountRoleTable();

	public final Column<AccountRoleTable, Long> mvccVersion = createColumn(
		"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountRoleTable, Long> accountRoleId = createColumn(
		"accountRoleId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<AccountRoleTable, Long> companyId = createColumn(
		"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountRoleTable, Long> accountEntryId = createColumn(
		"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountRoleTable, Long> roleId = createColumn(
		"roleId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AccountRoleTable() {
		super("AccountRole", AccountRoleTable::new);
	}

}