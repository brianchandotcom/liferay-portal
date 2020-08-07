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

package com.liferay.commerce.discount.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommerceDiscountUsageEntry&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntry
 * @generated
 */
public class CommerceDiscountUsageEntryTable
	extends BaseTable<CommerceDiscountUsageEntryTable> {

	public static final CommerceDiscountUsageEntryTable INSTANCE =
		new CommerceDiscountUsageEntryTable();

	public final Column<CommerceDiscountUsageEntryTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceDiscountUsageEntryTable, Long>
		commerceDiscountUsageEntryId = createColumn(
			"commerceDiscountUsageEntryId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDiscountUsageEntryTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, Long>
		commerceAccountId = createColumn(
			"commerceAccountId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, Long> commerceOrderId =
		createColumn(
			"commerceOrderId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountUsageEntryTable, Long>
		commerceDiscountId = createColumn(
			"commerceDiscountId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);

	private CommerceDiscountUsageEntryTable() {
		super(
			"CommerceDiscountUsageEntry", CommerceDiscountUsageEntryTable::new);
	}

}