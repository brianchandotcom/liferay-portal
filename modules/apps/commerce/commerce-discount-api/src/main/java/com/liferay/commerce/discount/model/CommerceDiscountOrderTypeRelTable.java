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
 * The table class for the &quot;CommerceDiscountOrderTypeRel&quot; database table.
 *
 * @author Marco Leo
 * @see CommerceDiscountOrderTypeRel
 * @generated
 */
public class CommerceDiscountOrderTypeRelTable
	extends BaseTable<CommerceDiscountOrderTypeRelTable> {

	public static final CommerceDiscountOrderTypeRelTable INSTANCE =
		new CommerceDiscountOrderTypeRelTable();

	public final Column<CommerceDiscountOrderTypeRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Long>
		commerceDiscountOrderTypeRelId = createColumn(
			"commerceDiscountOrderTypeRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceDiscountOrderTypeRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Long>
		commerceDiscountId = createColumn(
			"commerceDiscountId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Long>
		commerceOrderTypeId = createColumn(
			"commerceOrderTypeId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceDiscountOrderTypeRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CommerceDiscountOrderTypeRelTable() {
		super(
			"CommerceDiscountOrderTypeRel",
			CommerceDiscountOrderTypeRelTable::new);
	}

}