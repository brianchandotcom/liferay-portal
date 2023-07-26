/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.model;

import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.base.BaseTable;

import java.math.BigDecimal;

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
	public final Column<CommerceInventoryWarehouseItemTable, BigDecimal>
		quantity = createColumn(
			"quantity", BigDecimal.class, Types.DECIMAL, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, BigDecimal>
		reservedQuantity = createColumn(
			"reservedQuantity", BigDecimal.class, Types.DECIMAL,
			Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, String> sku =
		createColumn("sku", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
	public final Column<CommerceInventoryWarehouseItemTable, String>
		unitOfMeasureKey = createColumn(
			"unitOfMeasureKey", String.class, Types.VARCHAR,
			Column.FLAG_DEFAULT);

	private CommerceInventoryWarehouseItemTable() {
		super("CIWarehouseItem", CommerceInventoryWarehouseItemTable::new);
	}

}