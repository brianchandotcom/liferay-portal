/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

/**
 * The table class for the &quot;AccountEntryOrganizationRel&quot; database table.
 *
 * @author Brian Wing Shun Chan
 * @see AccountEntryOrganizationRel
 * @generated
 */
public class AccountEntryOrganizationRelTable
	extends BaseTable<AccountEntryOrganizationRelTable> {

	public static final AccountEntryOrganizationRelTable INSTANCE =
		new AccountEntryOrganizationRelTable();

	public final Column<AccountEntryOrganizationRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<AccountEntryOrganizationRelTable, Long>
		accountEntryOrganizationRelId = createColumn(
			"accountEntryOrganizationRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<AccountEntryOrganizationRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountEntryOrganizationRelTable, Long> accountEntryId =
		createColumn(
			"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<AccountEntryOrganizationRelTable, Long> organizationId =
		createColumn(
			"organizationId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);

	private AccountEntryOrganizationRelTable() {
		super(
			"AccountEntryOrganizationRel",
			AccountEntryOrganizationRelTable::new);
	}

}