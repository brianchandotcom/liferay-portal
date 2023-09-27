/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AccountEntryUserRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryUserRel
 * @generated
 */
public class AccountEntryUserRelTable
	extends BaseTable<AccountEntryUserRelTable> {

	public static final AccountEntryUserRelTable INSTANCE =
		new AccountEntryUserRelTable();

	public final Column<AccountEntryUserRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountEntryUserRelTable, Long> accountEntryUserRelId =
		createColumn(
			"accountEntryUserRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AccountEntryUserRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountEntryUserRelTable, Long> accountEntryId =
		createColumn(
			"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountEntryUserRelTable, Long> accountUserId =
		createColumn(
			"accountUserId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AccountEntryUserRelTable() {
		super("AccountEntryUserRel", AccountEntryUserRelTable::new);
	}

}