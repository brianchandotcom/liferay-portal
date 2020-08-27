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
 * The table class for the &quot;CIReplenishmentItem&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryReplenishmentItem
 * @generated
 */
public class CommerceInventoryReplenishmentItemTable
	extends BaseTable<CommerceInventoryReplenishmentItemTable> {

	public static final CommerceInventoryReplenishmentItemTable INSTANCE =
		new CommerceInventoryReplenishmentItemTable();

	public final Column<CommerceInventoryReplenishmentItemTable, Long>
		mvccVersion = createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceInventoryReplenishmentItemTable, Long>
		commerceInventoryReplenishmentItemId = createColumn(
			"CIReplenishmentItemId", Long.class, Types.BIGINT,
			Column.FLAG_PRIMARY);
	public final Column<CommerceInventoryReplenishmentItemTable, Long>
		companyId = createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, String>
		userName = createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, Date>
		createDate = createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, Long>
		commerceInventoryWarehouseId = createColumn(
			"commerceInventoryWarehouseId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, String> sku =
		createColumn("sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, Date>
		availabilityDate = createColumn(
			"availabilityDate", Date.class, Types.TIMESTAMP,
			Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryReplenishmentItemTable, Integer>
		quantity = createColumn(
			"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);

	private CommerceInventoryReplenishmentItemTable() {
		super(
			"CIReplenishmentItem",
			CommerceInventoryReplenishmentItemTable::new);
	}

}