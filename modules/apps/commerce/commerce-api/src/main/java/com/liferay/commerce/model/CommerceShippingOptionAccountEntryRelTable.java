/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CSOptionAccountEntryRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingOptionAccountEntryRel
 * @generated
 */
public class CommerceShippingOptionAccountEntryRelTable
	extends BaseTable<CommerceShippingOptionAccountEntryRelTable> {

	public static final CommerceShippingOptionAccountEntryRelTable INSTANCE =
		new CommerceShippingOptionAccountEntryRelTable();

	public final Column<CommerceShippingOptionAccountEntryRelTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Long>
		CommerceShippingOptionAccountEntryRelId = createColumn(
			"CSOptionAccountEntryRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Long>
		userId = createColumn(
			"userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Long>
		accountEntryId = createColumn(
			"accountEntryId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, Long>
		commerceChannelId = createColumn(
			"commerceChannelId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, String>
		commerceShippingMethodKey = createColumn(
			"commerceShippingMethodKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceShippingOptionAccountEntryRelTable, String>
		commerceShippingOptionKey = createColumn(
			"commerceShippingOptionKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceShippingOptionAccountEntryRelTable() {
		super(
			"CSOptionAccountEntryRel",
			CommerceShippingOptionAccountEntryRelTable::new);
	}

}