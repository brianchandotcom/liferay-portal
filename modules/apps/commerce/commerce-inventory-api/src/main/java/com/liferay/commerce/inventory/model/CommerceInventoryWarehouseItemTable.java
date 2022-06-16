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
 * The table class for the &quot;CIWarehouseItem&quot; database table.
 *
 * @author Luca Pellizzon
 * @see CommerceInventoryWarehouseItem
 * @generated
 */
public class CommerceInventoryWarehouseItemTable
	extends BaseTable<CommerceInventoryWarehouseItemTable> {

	public static final CommerceInventoryWarehouseItemTable INSTANCE =
		new CommerceInventoryWarehouseItemTable();

	public final Column<CommerceInventoryWarehouseItemTable, Long> mvccVersion =
		createColumn(
			"mvccVersion", Long.class, Types.BIGINT, Column.FLAG_NULLITY);
	public final Column<CommerceInventoryWarehouseItemTable, String> uuid =
		createColumn("uuid_", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, String>
		externalReferenceCode = createColumn(
			"externalReferenceCode", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Long>
		commerceInventoryWarehouseItemId = createColumn(
			"CIWarehouseItemId", Long.class, Types.BIGINT, Column.FLAG_PRIMARY);
	public final Column<CommerceInventoryWarehouseItemTable, Long> companyId =
		createColumn(
			"companyId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Long> userId =
		createColumn("userId", Long.class, Types.BIGINT, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, String> userName =
		createColumn(
			"userName", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Date> createDate =
		createColumn(
			"createDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Date>
		modifiedDate = createColumn(
			"modifiedDate", Date.class, Types.TIMESTAMP, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Long>
		commerceInventoryWarehouseId = createColumn(
			"commerceInventoryWarehouseId", Long.class, Types.BIGINT,
			Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, String> sku =
		createColumn("sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Integer> quantity =
		createColumn(
			"quantity", Integer.class, Types.INTEGER, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, Integer>
		reservedQuantity = createColumn(
			"reservedQuantity", Integer.class, Types.INTEGER,
			Column.FLAG_DEFAULT);

	private CommerceInventoryWarehouseItemTable() {
		super("CIWarehouseItem", CommerceInventoryWarehouseItemTable::new);
	}

}