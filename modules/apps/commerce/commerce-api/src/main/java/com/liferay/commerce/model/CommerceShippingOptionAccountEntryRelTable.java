/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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