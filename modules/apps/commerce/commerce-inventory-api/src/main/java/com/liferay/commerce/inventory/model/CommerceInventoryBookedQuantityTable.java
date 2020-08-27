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

package com.liferay.commerce.inventory.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.sql.Types;

import java.util.Date;

/**
 * The table class for the &quot;CIBookedQuantity&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryBookedQuantity
 * @generated
 */
public class CommerceInventoryBookedQuantityTable
	extends BaseTable<CommerceInventoryBookedQuantityTable> {

	public static final CommerceInventoryBookedQuantityTable INSTANCE =
		new CommerceInventoryBookedQuantityTable();

	public final Column<CommerceInventoryBookedQuantityTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceInventoryBookedQuantityTable, Long>
		commerceInventoryBookedQuantityId = createColumn(
			"CIBookedQuantityId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceInventoryBookedQuantityTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String> sku =
		createColumn("sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Integer>
		quantity = createColumn(
			"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, Date>
		expirationDate = createColumn(
			"expirationDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryBookedQuantityTable, String>
		bookedNote = createColumn(
			"bookedNote", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);

	private CommerceInventoryBookedQuantityTable() {
		super("CIBookedQuantity", CommerceInventoryBookedQuantityTable::new);
	}

}