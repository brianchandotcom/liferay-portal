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
 * The table class for the &quot;CommercePriceListOrderTypeRel&quot; database table.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRel
 * @generated
 */
public class CommercePriceListOrderTypeRelTable
	extends BaseTable<CommercePriceListOrderTypeRelTable> {

	public static final CommercePriceListOrderTypeRelTable INSTANCE =
		new CommercePriceListOrderTypeRelTable();

	public final Column<CommercePriceListOrderTypeRelTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommercePriceListOrderTypeRelTable, Long>
		ctCollectionId = createColumn(
			"ctCollectionId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommercePriceListOrderTypeRelTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Long>
		commercePriceListOrderTypeRelId = createColumn(
			"CPriceListOrderTypeRelId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommercePriceListOrderTypeRelTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Date> modifiedDate =
		createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Long>
		commercePriceListId = createColumn(
			"commercePriceListId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Long>
		commerceOrderTypeId = createColumn(
			"commerceOrderTypeId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Integer> priority =
		createColumn(
			"priority", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommercePriceListOrderTypeRelTable, Date>
		lastPublishDate = createColumn(
			"lastPublishDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);

	private CommercePriceListOrderTypeRelTable() {
		super(
			"CommercePriceListOrderTypeRel",
			CommercePriceListOrderTypeRelTable::new);
	}

}