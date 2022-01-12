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

package com.liferay.commerce.price.list.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CommercePriceListDiscountRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListDiscountRel
 * @generated
 */
public class CommercePriceListDiscountRelTable
	extends BaseTable<CommercePriceListDiscountRelTable> {

	public static final CommercePriceListDiscountRelTable INSTANCE =
		new CommercePriceListDiscountRelTable();

	public final Column<CommercePriceListDiscountRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePriceListDiscountRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommercePriceListDiscountRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Long>
		commercePriceListDiscountRelId = createColumn(
			"commercePriceListDiscountRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePriceListDiscountRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Long>
		commerceDiscountId = createColumn(
			"commerceDiscountId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Long>
		commercePriceListId = createColumn(
			"commercePriceListId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Integer> order =
		createColumn(
			"order_", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListDiscountRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CommercePriceListDiscountRelTable() {
		super(
			"CommercePriceListDiscountRel",
			CommercePriceListDiscountRelTable::new);
	}

}